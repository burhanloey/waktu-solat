package com.burhanloey.waktusolat.modules.esolat;

import com.burhanloey.waktusolat.modules.esolat.tasks.TaskManager;
import com.burhanloey.waktusolat.modules.storage.AppDatabase;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;
import com.burhanloey.waktusolat.tls.TLSSocketFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ESolatModule {
    @Provides
    TLSSocketFactory provideTlsSocketFactory() {
        try {
            return new TLSSocketFactory();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    OkHttpClient provideOkHttpClient(TLSSocketFactory tlsSocketFactory) {
        return new OkHttpClient.Builder()
                .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                .build();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(ESolat.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    ESolatApi provideESolatApi(Retrofit retrofit) {
        return retrofit.create(ESolatApi.class);
    }

    @Provides
    PrayerTimeDao providePrayerTimeDao(AppDatabase appDatabase) {
        return appDatabase.prayerTimeDao();
    }

    @Provides
    TaskManager provideTaskManager(PrayerTimeDao prayerTimeDao, TimeFormatter timeFormatter) {
        return new TaskManager(prayerTimeDao, timeFormatter);
    }

    @Provides
    ESolatManager provideESolatManager(ESolatApi eSolatApi, TaskManager taskManager,
                                       TimeFormatter timeFormatter) {
        return new ESolatManager(eSolatApi, taskManager, timeFormatter);
    }
}
