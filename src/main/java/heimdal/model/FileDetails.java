package heimdal.model;

import java.util.ArrayList;

public class FileDetails {
    private String filename;
    private long frequency;

    public ArrayList<Integer> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Integer> pages) {
        this.pages = pages;
    }

    private ArrayList<Integer> pages;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }
    public FileDetails(String name, long wordFreq)
    {
        this.filename = name;
        this.frequency = wordFreq;
        pages = new ArrayList<>();
    }
    @Override
    public String toString()
    {
        return "{\n\t\"Name\":\""+this.filename+"\"" +
                "\n\t\"Word-freq\":\""+this.frequency+"\"" +
                "\n\t\"page-numbers\":"+pages+"\n}";
    }
}
