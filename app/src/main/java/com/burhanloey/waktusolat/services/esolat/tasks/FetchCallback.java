package com.burhanloey.waktusolat.services.esolat.tasks;

/**
 * Callbacks during ESolat API data fetching. The callbacks will run on UI thread.
 */
public interface FetchCallback {
    void onCompleted();
    void onFailure(String message);
}
