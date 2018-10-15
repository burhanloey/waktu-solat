package com.burhanloey.waktusolat.services.state;

import com.burhanloey.waktusolat.services.storage.LocalStorage;

/**
 * A service to store/retrieve application state.
 */
public class StateManager {
    private final LocalStorage localStorage;

    public StateManager(LocalStorage localStorage) {
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

    /**
     * Get previous state of notifications switch. Default to false.
     *
     * @return Notifications switch state
     */
    public boolean getNotificationsEnabled() {
        return localStorage.getBoolean("notifications_enabled", false);
    }

    /**
     * Save current state of notifications switch.
     *
     * @param value Boolean value
     */
    public void setNotificationsEnabled(boolean value) {
        localStorage.putBoolean("notifications_enabled", value);
    }
}
