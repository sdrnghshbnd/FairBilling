package com.bt;

import com.bt.processor.BillingCalculator;
import com.bt.processor.LogParser;

import java.nio.file.Paths;

/**
 * Main class to run the Fair Billing application.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide the path to the log file as an argument.");
            return;
        }

        String filePath = args[0];
        LogParser parser = new LogParser();
        BillingCalculator calculator = new BillingCalculator();

        try {
            calculator.calculateBilling(parser.parseLogFile(Paths.get(filePath)));
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}