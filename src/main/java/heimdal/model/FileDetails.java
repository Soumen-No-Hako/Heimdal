package heimdal.model;

public class FileDetails {
    private String filename;
    private long frequency;

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
    }
    @Override
    public String toString()
    {
        return "\nFiledetails\n{\n\t\"Name\":\""+this.filename+"\"\n\t\"Word-freq\":\""+this.frequency+"\"\n}";
    }
}
