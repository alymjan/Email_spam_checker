import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.io.FileNotFoundException;
public class FileScanner {
    public static void main(String[] args) {
        System.out.println("Please enter file name:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        try {
            readFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    public static void readFile(String name) throws FileNotFoundException {
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
        }
    }
}