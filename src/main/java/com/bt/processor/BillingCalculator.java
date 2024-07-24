package com.bt.processor;

import com.bt.model.SessionDetail;

import java.util.*;

public class BillingCalculator {
    public void calculateBilling(Map<String, SessionDetail>  usersSessionsDetail) {
        for (Map.Entry<String, SessionDetail> user : usersSessionsDetail.entrySet()) {
            System.out.printf("%s %d %d%n", user.getKey(), user.getValue().getSessionCounter(), user.getValue().getTotalSessionDuration());
        }
    }
}