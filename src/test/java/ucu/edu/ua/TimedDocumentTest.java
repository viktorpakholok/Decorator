package ucu.edu.ua;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TimedDocumentTest {

    @Test
    void testParseTiming() {
        Document stubDocument = path -> "Stub Result";

        TimedDocument timedDocument = new TimedDocument(stubDocument);

        long startTime = System.nanoTime();
        String result = timedDocument.parse("test-path");
        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;

        int expectedTimeMs = 5000000;
        int toleranceMs = 5000000;

        // assertEquals(elapsedTime, 10);
        assertTrue(elapsedTime >= expectedTimeMs - toleranceMs && elapsedTime <= expectedTimeMs + toleranceMs);
        assertEquals("Stub Result", result);

        // Test execution timing (verify it runs correctly, not the actual time).
        // Optionally measure elapsed time here.
    }
}
