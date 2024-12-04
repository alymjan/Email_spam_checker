import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileScanner {
    public static void main(String[] args) {
        System.out.println("Please enter file name:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        ArrayList<EmailStorage> mainStorage = new ArrayList<>();
        try {
            mainStorage = readFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Spam file not found");
        }

        int spamCount = 0;
        int hamCount = 0;
        for(int i = 0; i < mainStorage.size(); i++){
            FeatureExtractor test = new FeatureExtractor(mainStorage.get(i));
            test.wordCount();
            test.wordLength();
            test.hyperLinkCheck();
            test.specialCharaters();
            test.repeatedWords();
            test.triggerWords();
            test.caseSensitivity();
            test.scoreChecker();

            if(test.isHam){
                hamCount++;
                System.out.println("Ham" + test.email.getLabel());
            }
            else {
                spamCount++;
                System.out.println("Spam" + test.email.getLabel());
            }
        }
        System.out.println("the amount of spams: " + spamCount);
        System.out.println("the amount of hams: " + hamCount);
    }
    public static ArrayList<EmailStorage> readFile(String name) throws FileNotFoundException {
        File fileName = new File(name);
        try (Scanner scanner = new Scanner(fileName)) {
            ArrayList<EmailStorage> storage = new ArrayList<>();
            int labelNumber = 0;
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                EmailStorage tempStore = new EmailStorage(String.valueOf(labelNumber));
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!word.equals(",0") || !word.equals(",1")) {
                        tempStore.addWord(word);
                    } else {
                        String str2 = scanner.next();
                        break;
                    }
                }
                storage.add(tempStore);
                labelNumber++;
            }
            return storage;
        }
    }
}