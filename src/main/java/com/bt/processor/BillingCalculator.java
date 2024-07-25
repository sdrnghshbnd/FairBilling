package com.bt.processor;

import com.bt.model.SessionDetail;

import java.util.Map;

/**
 * Calculates and prints billing information for user sessions.
 */
public class BillingCalculator {

    /**
     * Calculates and prints the billing information for each user.
     *
     * @param usersSessionsDetail A map of usernames to their session details.
     */
    public void calculateBilling(Map<String, SessionDetail> usersSessionsDetail) {
        usersSessionsDetail.forEach((username, sessionDetail) ->
                System.out.printf("%s %d %d%n", username, sessionDetail.getSessionCounter(), sessionDetail.getTotalSessionDuration())
        );
    }
}