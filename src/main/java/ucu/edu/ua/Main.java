package ucu.edu.ua;

public class Main {
    public static void main(String[] args) {
        Document document = new SmartDocument();
        document = new CachedDocument(new TimedDocument(document));
        System.out.println(document.parse(
            "src\\main\\resources\\decorator.png"
        ));

        Document documentNew = new SmartDocument();
        documentNew = new CachedDocument(new TimedDocument(documentNew));
        System.out.println(documentNew.parse(
            "src\\main\\resources\\patterns.png"
        ));

    }
}