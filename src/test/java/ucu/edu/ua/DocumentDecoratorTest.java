package ucu.edu.ua;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DocumentDecoratorTest {

    @Test
    void testParseDelegatesToWrappedDocument() {
        Document stubDocument = path -> "Decorated Result";

        DocumentDecorator decorator = new DocumentDecorator(stubDocument);

        String result = decorator.parse("test-path");
        assertEquals("Decorated Result", result);
    }
}
