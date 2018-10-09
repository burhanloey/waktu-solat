package com.burhanloey.waktusolat.services.state;

import com.burhanloey.waktusolat.services.storage.LocalStorage;

/**
 * A service to store/retrieve application state.
 */
public class StateService {
    private final LocalStorage localStorage;

    public StateService(LocalStorage localStorage) {
        this.localStorage = localStorage;
    }

    /**
     * Get previous position of the spinner. Default to 0 (first item).
     *
     * @return Spinner position
     */
    public int getPosition() {
        return localStorage.getInt("position", 0);
    }

    /**
     * Save current position of the spinner.
     *
     * @param position Spinner position
     */
    public void setPosition(int position) {
        localStorage.putInt("position", position);
    }
}
