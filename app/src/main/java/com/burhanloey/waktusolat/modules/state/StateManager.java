package com.burhanloey.waktusolat.modules.state;

import com.burhanloey.waktusolat.modules.storage.LocalStorage;

/**
 * A service to store/retrieve application state.
 */
public class StateManager {
    private final LocalStorage localStorage;

    public StateManager(LocalStorage localStorage) {
        this.localStorage = localStorage;
    }

    /**
     * Get previous position of the state spinner. Default to 0 (first item).
     *
     * @return State spinner position
     */
    public int getStatePosition() {
        return localStorage.getInt("state_position", 0);
    }

    /**
     * Save current position of the state spinner.
     *
     * @param position State spinner position
     */
    public void saveStatePosition(int position) {
        localStorage.putInt("state_position", position);
    }

    /**
     * Get previous position of the district spinner. Default to 0 (first item).
     *
     * @return District spinner position
     */
    public int getDistrictPosition() {
        return localStorage.getInt("district_position", 0);
    }

    /**
     * Save current position of the district spinner.
     *
     * @param position District spinner position
     */
    public void saveDistrictPosition(int position) {
        localStorage.putInt("district_position", position);
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
    public void saveNotificationsEnabled(boolean value) {
        localStorage.putBoolean("notifications_enabled", value);
    }
}
