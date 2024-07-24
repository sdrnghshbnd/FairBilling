package com.bt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionDetail {
    private int sessionCounter;
    private long totalSessionDuration;

    public SessionDetail updateTotalSessionDuration(long duration) {
        this.totalSessionDuration = totalSessionDuration + duration;
        this.sessionCounter = sessionCounter + 1;
        return this;
    }
}
