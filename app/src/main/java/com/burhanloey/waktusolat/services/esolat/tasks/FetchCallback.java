package com.burhanloey.waktusolat.services.esolat.tasks;

/**
 * Callbacks during ESolat API data fetching.
 */
public interface FetchCallback {
    void onSuccess();
    void onFailure(String message);
}
