package com.burhanloey.waktusolat.services.state;

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
