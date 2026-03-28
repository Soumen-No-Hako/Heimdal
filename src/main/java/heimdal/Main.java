package heimdal;

import heimdal.model.FileDetails;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static String pathname = "/home/soumen/Downloads/Projects-2026/Heimdal/src/main/resources/LLM Data Pipeline Explained_.pdf";
    public static String filename = pathname.substring(pathname.indexOf("/resources/") + "/resources/".length());
    static void main() {
        PDFTextStripper stripper = new PDFTextStripper();
        HashMap<String, ArrayList<FileDetails>> wordmap = new HashMap<>();
        try {

            PDDocument doc = Loader.loadPDF(new File(pathname));
            int numberOfPages = doc.getNumberOfPages();
            System.out.println("num of pages : "+ numberOfPages);
            for (int i = 0; i < numberOfPages; i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                System.out.println("Processing page "+(i+1)+" of "+ filename +" ...");
                String pagetxt = stripper.getText(doc);
                int steady=0, runny = 1;
                String word = "";
                if(pagetxt.length()!=0) word = pagetxt.substring(0,1);
                while(runny<pagetxt.length())
                {
                    if(String.valueOf(pagetxt.charAt(runny)).matches("[^A-Za-z0-9]") || (runny+1)>=pagetxt.length())
                    {
                        //add word to map
                        //change pointers
                        appendWordmap(word.toLowerCase().trim(), wordmap, i);
                        if(runny+1 != pagetxt.length()) word = String.valueOf(pagetxt.charAt(runny + 1));
                        runny+=2;
                    }
                    else word += pagetxt.charAt(runny++);
                }
            }
            File outputFile = new File("/home/soumen/Downloads/Projects-2026/Heimdal/src/main/resources/heimdal-output/output-heimdal-" + Instant.now().toString() + ".txt");
            outputFile.createNewFile();
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write(wordmap.toString().replaceAll("}], ","}],\n"));
            fileWriter.close();
//            System.out.println(wordmap);
        } catch (IOException e) {
            throw new RuntimeException(e+": File not found");
        }
    }

    private static void appendWordmap(String word, HashMap<String, ArrayList<FileDetails>> wordmap, int pgnum) {

        if(!(isArticle(word) || filterSmallPrepositions(word) || word.length()<2)){
            if (wordmap.containsKey(word)) {
                FileDetails fd = wordmap.get(word).get(0);
                long freq = fd.getFrequency() + 1;
                ArrayList<Integer> pagenums = fd.getPages();
                pagenums.add(pgnum);
                fd.setFrequency(freq);
                fd.setPages(pagenums);
                wordmap.get(word).set(0, fd);
            } else {
                FileDetails fd = new FileDetails(filename, 1);
                ArrayList<FileDetails> fdList = new ArrayList<>();
                ArrayList<Integer> pagenums = fd.getPages();
                pagenums.add(pgnum);
                fd.setPages(pagenums);
                fdList.add(fd);
                wordmap.put(word, fdList);
            }
        }
    }

    public static boolean isArticle(String word)
    {
        var articles = List.of("a","an","the", "am", "is", "are", "was", "were");
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
