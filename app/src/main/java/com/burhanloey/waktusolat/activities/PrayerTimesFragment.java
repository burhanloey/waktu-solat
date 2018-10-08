package com.burhanloey.waktusolat.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.services.esolat.ESolat;
import com.burhanloey.waktusolat.services.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerFragment;

public class PrayerTimesFragment extends DaggerFragment {
    @Inject
    PrayerTimeDao prayerTimeDao;

    @Inject
    ExecutorService executorService;

    @Inject
    @Named("date")
    DateFormat dateFormat;

    @Inject
    @Named("time-from")
    DateFormat fromDateFormat;

    @Inject
    @Named("time-to")
    DateFormat toDateFormat;

    private TextView subuhTimeTextView;
    private TextView zuhurTimeTextView;
    private TextView asarTimeTextView;
    private TextView maghribTimeTextView;
    private TextView ishaTimeTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prayertimes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (subuhTimeTextView == null) {
            subuhTimeTextView = view.findViewById(R.id.subuhtime_textview);
        }
        if (zuhurTimeTextView == null) {
            zuhurTimeTextView = view.findViewById(R.id.zuhurtime_textview);
        }
        if (asarTimeTextView == null) {
            asarTimeTextView = view.findViewById(R.id.asartime_textview);
        }
        if (maghribTimeTextView == null) {
            maghribTimeTextView = view.findViewById(R.id.maghribtime_textview);
        }
        if (ishaTimeTextView == null) {
            ishaTimeTextView = view.findViewById(R.id.ishatime_textview);
        }
    }

    private String formatTime(String from) {
        try {
            Date date = fromDateFormat.parse(from);
            return toDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return from;
    }

    private void update(PrayerTime prayerTime) {
        String fajrTime = formatTime(prayerTime.getFajr());
        subuhTimeTextView.setText(fajrTime);

        String dhuhrTime = formatTime(prayerTime.getDhuhr());
        zuhurTimeTextView.setText(dhuhrTime);

        String asrTime = formatTime(prayerTime.getAsr());
        asarTimeTextView.setText(asrTime);

        String maghribTime = formatTime(prayerTime.getMaghrib());
        maghribTimeTextView.setText(maghribTime);

        String ishaTime = formatTime(prayerTime.getIsha());
        ishaTimeTextView.setText(ishaTime);
    }

    private void clear() {
        subuhTimeTextView.setText(R.string.not_available);
        zuhurTimeTextView.setText(R.string.not_available);
        asarTimeTextView.setText(R.string.not_available);
        maghribTimeTextView.setText(R.string.not_available);
        ishaTimeTextView.setText(R.string.not_available);
    }

    public void loadPrayerTime(final String districtCode) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String today = dateFormat.format(new Date());
                PrayerTime prayerTime = prayerTimeDao.find(today, districtCode);

                if (prayerTime == null) {
                    clear();
                } else {
                    update(prayerTime);
                }
            }
        });
    }
}
