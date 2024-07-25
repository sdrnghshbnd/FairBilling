package com.bt.processor;

import com.bt.model.SessionDetail;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillingCalculatorTest {

    @Test
    void testCalculateBilling() {
        Map<String, SessionDetail> usersSessionsDetail = new HashMap<>();
        usersSessionsDetail.put("ALICE99", new SessionDetail(4, 240));
        usersSessionsDetail.put("CHARLIE", new SessionDetail(3, 37));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BillingCalculator calculator = new BillingCalculator();
        calculator.calculateBilling(usersSessionsDetail);

        String expectedOutput = "ALICE99 4 240" + System.lineSeparator() + "CHARLIE 3 37" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}
