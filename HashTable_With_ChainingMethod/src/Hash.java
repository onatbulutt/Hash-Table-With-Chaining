import java.io.*;

public class Hash implements Hash_Interface{
    private Chain[] table;
    private int size;
    private int numberofcollusion=0;
    String[] uniqueWordsArray = new String[350]; // size is arranged according to uniqueWordsCount(313)
    // uniqueWordsArray include all unique words in the text.
    String[] wordsArray= new String[550]; // size is arranged according to totalwordcount(519)
    // wordsArray include all word in the text.
    int uniqueWordsCount = 0;
    private int totalwordcount;

    @Override
    public Integer GetHash(String mystring) {
        int hash = 0;
        for (char c : mystring.toCharArray()) // Scan each character in the input string
        {
            // Update the hash value using a simple hash function
            hash = mystring.length() * hash + (int)c; // 22 is just random number.
        }
        // Take the absolute value of the modulo result of the total sum.
        hash = Math.abs(hash);
        // Take the modulo result of the hash value by the size of the hash table to determine the index
        return hash % size;
    }

    @Override
    public void ReadFileandGenerateHash(String filename, int size) {
        this.size=size;
        table=new Chain[size]; // create a hashtable with chaining method.
        int k=0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line of the file
            while ((line = bufferedReader.readLine()) != null) {
                // Process punctuation and split the line into words
                String[] words = line.split("[\\s\\p{Punct}-]+");
                //String[] words = line.split("\\W+");
                // Process each word in the line
                for (String word : words) {
                    // if word is not empty
                    if (!word.isEmpty())
                    {
                        // add word to wordsArray
                        wordsArray[k]= word;
                        k++;
                        totalwordcount++;

                        // Check if the word is unique
                        boolean isUnique = true;
                        for (int j = 0; j < uniqueWordsCount; j++) {
                            if (uniqueWordsArray[j].equals(word)) {
                                isUnique = false;
                                break;
                            }
                        }
                        if (isUnique)  // If the word is unique, add it to the uniqueWordsArray
                        {
                            uniqueWordsArray[uniqueWordsCount++] = word;
                        }
                        int index = GetHash(word); // get hash function of current word.
                        // If the index is empty, create a new chain with the word
                        if(table[index]==null)
                        {
                            table[index]=new Chain(word);
                        }
                        else // If the index is not empty
                        {
                            boolean found = false;
                            Chain temp = table[index];
                            while(temp.next!=null) // Scan through the entire chain
                            {
                                // if current word and one of the word in the chain equals each other, found become true.
                                if(temp.key.equals(word))
                                {
                                    found=true;
                                }
                                temp = temp.next;
                            }
                            // checking for last word of the chain
                            if(temp.key.equals(word) )
                            {
                                found=true;
                            }
                            //Add the word to the rest of the chain at the current index
                            temp.next=new Chain(word);
                            // if current word is different from all words in the chain, increase collusion
                            if(!found)
                            {
                                numberofcollusion++;
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException exception) {
            // Print an error message if a file is not found
            System.out.println("File not found.");
        } catch (IOException e) {
            // Print an error message if a file read error occurs
            System.out.println("Reading error.");
        }
        // Print the total word count and total unique word count
        System.out.println("Total word in the text is: "+totalwordcount);
        System.out.println("Total unique word count in the text is: "+uniqueWordsCount);
    }
    @Override
    public void DisplayResult(String Outputfile) {
        // Create a File object for the specified output file path
        File outputFile = new File(Outputfile);
        // If the file does not exist, create a new file
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                // Print an error message if there is an issue creating the output file
                System.out.println("Error creating the output file");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Outputfile))) {
            for(int j = 0; j < uniqueWordsCount; j++) // scan all unique words in the text
            {
                // Get the word and its frequency
                String word = uniqueWordsArray[j];
                int frequency = showFrequency(word);
                // Print the total frequency of the current word
                writer.write("Total frequency of '" + word + "': " + frequency);
                writer.newLine();
            }
        } catch (IOException e) {
            // Print an error message if a file write error occurs
            System.out.println("File write error");
        }
    }

    @Override
    public void DisplayResult()
    {
        for (int j = 0; j < uniqueWordsCount; j++) // scan all unique words in the text
        {
            // Get the current word and its frequency
            String word = uniqueWordsArray[j];
            int frequency = showFrequency(word);
            // Print the total frequency of the current word
            System.out.println("Total frequency of '" + word + "': " + frequency);
        }
    }

    @Override
    public void DisplayResultOrdered(String Outputfile)
    {
        // Create a File object for the specified output file path
        File outputFile = new File(Outputfile);
        // If the file does not exist, create a new file
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                // Print an error message if there is an issue creating the output file
                System.out.println("Error creating the output file");
            }
        }
        //Bubble sort based on frequency in descending order
        for (int i = 0; i < uniqueWordsCount - 1; i++) {
            for (int j = 0; j < uniqueWordsCount - i - 1; j++) {
                int frequency1 = showFrequency(uniqueWordsArray[j]);
                int frequency2 = showFrequency(uniqueWordsArray[j + 1]);

                if (frequency1 < frequency2) {
                    // Swap words in uniqueWordsArray
                    String temp = uniqueWordsArray[j];
                    uniqueWordsArray[j] = uniqueWordsArray[j + 1];
                    uniqueWordsArray[j + 1] = temp;
                }
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Outputfile))) {
            // Write the sorted array to the file
            for (int j = 0; j < uniqueWordsCount; j++) // scan all unique words in the text
            {
                // Get the word and its frequency
                String word = uniqueWordsArray[j];
                int frequency = showFrequency(word);
                // Write the word and its frequency to the file
                writer.write("Total frequency of '" + word + "': " + frequency);
                writer.newLine();
            }
        } catch (IOException e) {
            // Print an error message if a file write error occurs
            System.out.println("File write error");
        }
    }


    @Override
    public int showFrequency(String myword)
    {
        int frequency = 0;
        // Scan through the entire table
        for (Chain chain : table) {
            // If the chain is not empty
            if (chain != null) {
                Chain temp = chain;
                // Check the chain
                while (temp != null) {
                    // If the word matches, increase the frequency
                    if (temp.key.equals(myword)) {
                        frequency++;
                    }
                    // Move to the next node in current chain
                    temp = temp.next;
                }
            }
        }
        return frequency;
    }

    @Override
    public String showMaxRepeatedWord()
    {
        String maxRepeatedWord = uniqueWordsArray[0];
        int frequencymax = showFrequency(maxRepeatedWord); // Initialize first word's frequency is highest
        for (int j = 0; j < uniqueWordsCount; j++) // scan all unique words in the text
        {
            String word = uniqueWordsArray[j];
            int frequency = showFrequency(word);
            // Check if the frequency of the word is higher than the current max frequency
            if(frequency>frequencymax)
            {
                // Update the max frequency and the corresponding word
                frequencymax = frequency;
                maxRepeatedWord = word;
            }
        }
        return "Most repeated word is '"+maxRepeatedWord+"' ";
    }

    @Override
    public String checkWord(String myword)
    {
        String checkstring="";
        int checkvalue;
        boolean found=false;
        for (int j = 0; j < totalwordcount; j++) // scan all words in the text
        {
            String word = wordsArray[j];
            // If found, display its first position in the text and frequency of the word
            if(word.equals(myword))
            {
                checkvalue= j+1;
                found=true;
                checkstring="'"+myword+"' is found in the text. Repeated "+showFrequency(myword)+" times. First location :"+checkvalue;
                break; // get out of the loop
            }
        }
        // If not found, returns not found message.
        if(!found)
        {
            checkstring= "'"+myword+"' is not found in the text.";
        }
        return checkstring;
    }

    @Override
    public float TestEfficiency()
    {
        float collusionRate = (float) numberofcollusion / totalwordcount;
        float result=100-(collusionRate*100);
        System.out.print("Efficiency: %");
        return result;
    }

    @Override
    public int NumberOfCollusion()
    {
        System.out.print("Number of collusion is ");
        // return numberofcollusion that calculated in ReadFileandGenerateHash method.
        return numberofcollusion;
    }

}
