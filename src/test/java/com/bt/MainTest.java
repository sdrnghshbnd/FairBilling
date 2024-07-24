package com.bt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void testMainMethod(@TempDir Path tempDir) throws Exception {
        Path logFile = tempDir.resolve("test.log");
        Files.write(logFile, (
                "14:02:03 ALICE99 Start\n" +
                        "14:02:05 CHARLIE End\n" +
                        "14:02:34 ALICE99 End\n" +
                        "14:02:58 ALICE99 Start\n" +
                        "14:03:02 CHARLIE Start\n" +
                        "14:03:33 ALICE99 Start\n" +
                        "14:03:35 ALICE99 End\n" +
                        "14:03:37 CHARLIE End\n" +
                        "14:04:05 ALICE99 End\n" +
                        "14:04:23 ALICE99 End\n" +
                        "14:04:41 CHARLIE Start"
        ).getBytes());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Main.main(new String[]{logFile.toString()});

        String expectedOutput = "ALICE99 4 240\r\nCHARLIE 3 37\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
