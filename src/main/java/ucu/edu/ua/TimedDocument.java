package ucu.edu.ua;

public class TimedDocument extends DocumentDecorator{
    public TimedDocument(Document document) {
        super(document);
    }

    public String parse(String path) {
        long startTime = System.nanoTime();

        String result =  super.parse(path);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time in nanoseconds: " + elapsedTime/1_000_000_000.0);

        return result;
    }
}
