public class Main {
    public static void main(String[] args) {
        Hash hash = new Hash();
        hash.ReadFileandGenerateHash("C:\\Users\\onatb\\OneDrive\\Masaüstü\\text.txt",692); //you can change size of the tablo for observe different results
        hash.DisplayResult();
        hash.DisplayResult("C:\\Users\\onatb\\frequencies.txt"); // you can change path and name of the text file.
        hash.DisplayResultOrdered("C:\\Users\\onatb\\frequenciesOrdered.txt"); // you can change path and name of the text file.
        System.out.println(hash.checkWord("Ministry")); //you can search different words
        System.out.println(hash.showMaxRepeatedWord());
        System.out.println(hash.NumberOfCollusion());
        System.out.println(hash.TestEfficiency());

    }
}