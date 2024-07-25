package com.bt;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainIntegrationTest {

    @Test
    void testMainWithSampleLogFile() throws Exception {
        // Get the path to the log file in resources
        String logFilePath = getResourceFilePath();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Run the main method
            Main.main(new String[]{logFilePath});

            // expected output
            String expectedOutput = "SADRA 1 30" + System.lineSeparator() +
                    "DAVID 1 140" + System.lineSeparator() +
                    "ALICE99 4 270" + System.lineSeparator() +
                    "CHARLIE 7 104"+ System.lineSeparator();

            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    private String getResourceFilePath() throws URISyntaxException {
        return Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("log.txt")).toURI()).toString();
    }
}