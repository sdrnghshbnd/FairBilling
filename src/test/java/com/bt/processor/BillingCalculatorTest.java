//package com.bt.processor;
//
//import com.bt.model.UserSessionStart;
//import org.junit.jupiter.api.Test;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class BillingCalculatorTest {
//
//    @Test
//    void testCalculateBilling() {
//        Map<String, Long> usersDuration = new HashMap<>();
//        UserSessionStart alice = new UserSessionStart("ALICE99");
//        usersDuration.put("ALICE99", 0L);
//
//        UserSessionStart charlie = new UserSessionStart("CHARLIE");
//        usersDuration.put("CHARLIE", 0L);
//
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        BillingCalculator calculator = new BillingCalculator();
//        calculator.calculateBilling(usersDuration);
//
//        String expectedOutput = "ALICE99 0\nCHARLIE 0\n";
//        assertEquals(expectedOutput, outContent.toString());
//    }
//}