package ucu.edu.ua;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class SmartDocument implements Document {
    private Tesseract tesseract;

    public SmartDocument() {
        this.tesseract = new Tesseract();
        this.tesseract.setDatapath("src\\main\\resources\\");
    }

    public String parse(String path) {
        try {
            return tesseract.doOCR(new java.io.File(path));
        } catch (TesseractException e) {
            e.printStackTrace();
            return "";
        }
    }
}
