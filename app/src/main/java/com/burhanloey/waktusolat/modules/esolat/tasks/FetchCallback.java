package com.burhanloey.waktusolat.modules.esolat.tasks;

/**
 * Callbacks during ESolat API data fetching. The callbacks will run on UI thread.
 */
public interface FetchCallback {
    void onCompleted();
    void onSavingData();
    void onFailure(String message);
}
