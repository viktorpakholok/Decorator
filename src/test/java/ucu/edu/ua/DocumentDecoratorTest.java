package ucu.edu.ua;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DocumentDecoratorTest {

    @Test
    void testParseDelegatesToWrappedDocument() {
        Document stubDocument = path -> "Decorated Result";

        DocumentDecorator decorator = new DocumentDecorator(stubDocument);

        String result = decorator.parse("test-path");
        assertEquals("Decorated Result", result);
    }
}
