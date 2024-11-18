package ucu.edu.ua;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TimedDocumentTest {

    static final int EXPECTED_TIME_MS = 5000000;
    static final int TOLERANCE_MS = 5000000;

    @Test
    void testParseTiming() {
        Document stubDocument = path -> "Stub Result";

        TimedDocument timedDocument = new TimedDocument(stubDocument);

        long startTime = System.nanoTime();
        String result = timedDocument.parse("test-path");
        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;

        // assertEquals(elapsedTime, 10);
        assertTrue(elapsedTime >= EXPECTED_TIME_MS - TOLERANCE_MS 
        && elapsedTime <= EXPECTED_TIME_MS + TOLERANCE_MS);
        assertEquals("Stub Result", result);

    }
}
