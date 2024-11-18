package ucu.edu.ua;

public class Main {
    public static void main(String[] args) {
        // Document document = new SmartDocumentLesson();
        // document = new CachedDocument(new TimedDocument(document));
        // System.out.println(document.parse("hello"));

        Document documentNew = new SmartDocument();
        documentNew = new CachedDocument(new TimedDocument(documentNew));
        System.out.println(documentNew.parse("src\\main\\resources\\patterns.png"));

    }
}