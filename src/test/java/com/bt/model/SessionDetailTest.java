package com.bt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionDetailTest {

    @Test
    void testConstructor() {
        SessionDetail sessionDetail = new SessionDetail(5, 300L);
        assertEquals(5, sessionDetail.getSessionCounter());
        assertEquals(300L, sessionDetail.getTotalSessionDuration());
    }

    @Test
    void testGetSessionCounter() {
        SessionDetail sessionDetail = new SessionDetail(3, 180L);
        assertEquals(3, sessionDetail.getSessionCounter());
    }

    @Test
    void testGetTotalSessionDuration() {
        SessionDetail sessionDetail = new SessionDetail(2, 120L);
        assertEquals(120L, sessionDetail.getTotalSessionDuration());
    }

    @Test
    void testUpdateTotalSessionDuration() {
        SessionDetail sessionDetail = new SessionDetail(1, 60L);
        SessionDetail updatedSessionDetail = sessionDetail.updateTotalSessionDuration(30L);

        // Check that the method returns the same object
        assertSame(sessionDetail, updatedSessionDetail);

        // Check that the values are updated correctly
        assertEquals(2, updatedSessionDetail.getSessionCounter());
        assertEquals(90L, updatedSessionDetail.getTotalSessionDuration());
    }

    @Test
    void testMultipleUpdates() {
        SessionDetail sessionDetail = new SessionDetail(0, 0L);

        sessionDetail.updateTotalSessionDuration(50L);
        assertEquals(1, sessionDetail.getSessionCounter());
        assertEquals(50L, sessionDetail.getTotalSessionDuration());

        sessionDetail.updateTotalSessionDuration(30L);
        assertEquals(2, sessionDetail.getSessionCounter());
        assertEquals(80L, sessionDetail.getTotalSessionDuration());

        sessionDetail.updateTotalSessionDuration(20L);
        assertEquals(3, sessionDetail.getSessionCounter());
        assertEquals(100L, sessionDetail.getTotalSessionDuration());
    }
}