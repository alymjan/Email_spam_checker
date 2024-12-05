/*
This is our FileScanner
It reads the file and stores each email and it's words as
an ArrayList in EmailStorage class.
This also was our class where we checked our FeatureExtractor
and that is why it contains the main method.
*/



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
    }
    public static ArrayList<EmailStorage> readFile(String name) throws FileNotFoundException { // this method reads the file and stores each email as an EmailStorage type ArrayList
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