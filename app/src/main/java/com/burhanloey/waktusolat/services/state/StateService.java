package com.burhanloey.waktusolat.services.state;

import com.burhanloey.waktusolat.services.storage.LocalStorage;

public class StateService {
    private final LocalStorage localStorage;

    public StateService(LocalStorage localStorage) {
        this.localStorage = localStorage;
    }

    public int getPosition() {
        return localStorage.getInt("position", 0);
    }

    public void setPosition(int position) {
        localStorage.putInt("position", position);
    }
}
