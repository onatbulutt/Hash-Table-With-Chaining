public interface Hash_Interface {
    public Integer GetHash(String mystring);
    public void ReadFileandGenerateHash(String filename, int size);
    public void DisplayResult(String Outputfile);
    public void DisplayResult();
    public void DisplayResultOrdered(String Outputfile);
    public int showFrequency(String myword);
    public String showMaxRepeatedWord();
    public String checkWord(String myword);
    public float TestEfficiency();
    public int NumberOfCollusion();
}