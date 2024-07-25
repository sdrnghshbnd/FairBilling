package com.bt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents the session details for a user.
 */
@Data
@AllArgsConstructor
public class SessionDetail {
    private int sessionCounter;
    private long totalSessionDuration;

    /**
     * Updates the total session duration and increments the session counter.
     *
     * @param duration The current session duration to add to the total.
     * @return The updated SessionDetail object.
     */
    public SessionDetail updateTotalSessionDuration(long duration) {
        this.totalSessionDuration += duration;
        this.sessionCounter++;
        return this;
    }
}