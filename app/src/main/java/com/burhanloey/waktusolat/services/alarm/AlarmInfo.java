package com.burhanloey.waktusolat.services.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmInfo {
    private String title;
    private String text;
    private long time;
}
