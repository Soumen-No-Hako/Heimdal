package heimdal;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        PDFTextStripper stripper = new PDFTextStripper();
        try {
            PDDocument doc = Loader.loadPDF(new File("/home/soumen/Downloads/Projects-2026/Heimdal/src/main/resources/LLM Data Pipeline Explained_.pdf"));
            int numberOfPages = doc.getNumberOfPages();
            System.out.println("num of pages : "+ numberOfPages);
            for (int i = 0; i < numberOfPages; i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                System.out.println("Processing page "+(i+1)+"...");
                String pageText = stripper.getText(doc);
                System.out.println(pageText.replaceAll("[^A-Za-z0-9\\s]", " "));
            }
        } catch (IOException e) {
            throw new RuntimeException(e+": File not found");
        }
    }
    public static boolean isArticle(String word)
    {
        var articles = List.of("a","an","the");
        for (int i = 0; i < articles.size(); i++) {
            if (word.equalsIgnoreCase(articles.get(i)))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean filterSmallPrepositions(String word) {
        var prepositions = List.of("At, in, on, before, during, after, since, until, in, on, at, under, over, above, below, beside, between, behind, to, into, towards, through, across, up, down, around, onto, Of, for, by, with, about, against, despite, except, instead".split(", "));
        for (int i = 0; i < prepositions.size(); i++) {
            if (word.equalsIgnoreCase(prepositions.get(i)))
            {
                return true;
            }
        }
        return false;
    }
}
