package ucu.edu.ua;

import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class SmartDocumentTest {

    @Test
    void testParseWithOCR() {
        SmartDocument smartDocument = new SmartDocument();

        File testImage = new File("src\\main\\resources\\patterns.png");
        assertTrue(testImage.exists(), "Test image must exist for OCR test.");

        String result = smartDocument.parse(testImage.getPath());
        assertNotNull(result);
    }

    @Test
    void testParseWithOCRSec() {
        SmartDocument smartDocument = new SmartDocument();

        File testImage = new File("src\\main\\resources\\decorator.png");
        assertTrue(testImage.exists(), "Test image must exist for OCR test.");

        String result = smartDocument.parse(testImage.getPath());
        assertEquals(result, "Pattern: Decorator\n" + //
            "\n" + //
            "Name: Decorator (a.k.a Wrapper)\n" + //
            "\n" + //
            "Problem: Attach additional responsibilities to an object dynamically. Decorators\n" + //
            "provide a flexible alternative to sub classing to extend flexibility\n" + //
            "\n" + //
            "Solution: Create a Wrapper that would wrap the object and change it behaviour\n"
        );
    }
}
