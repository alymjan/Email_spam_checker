import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
public class FeatureExtractor {
    EmailStorage email;
    int score;
    public FeatureExtractor(EmailStorage email) {
        this.email = email;
        score = 0;
    }
    public void wordCount() {
        int min = 20;
        int max = 300;
        int count = email.getWord().size();
        if (count < min || count > max) {
            score++;
        }
    }
    public void URLcheck() {
        ArrayList<String> arr = new ArrayList<>(email.getWord());
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals("URL")) {
                score++;
            }
        }
    }
    public void wordLength() {
        for (int i = 0; i < email.getWord().size(); i++) {
            if (email.getWord().get(i).length() > 45) score++;
        }
    }
    public void specialCharaters() {
        for (int i = 0; i < email.getWord().size(); i++) {
            String str = email.getWord().get(i).toUpperCase();
            for (int j = 0; j < str.length(); j++) {
                char currentChar = str.charAt(j);
                if (!Character.isLetter(currentChar)) {
                    if (currentChar != ',' && currentChar != '.' && currentChar != '?'
                            && currentChar != '!' && currentChar != '\'') {
                        score++;
                    }
                }
            }
        }
    }
    public void repeatedWords() {
        for (int i = 0; i < email.getWord().size() - 1; i++) {
            String current = email.getWord().get(i);
            String next = email.getWord().get(i + 1);
            if (current.equals(next) && !current.equals("NUMBER")) score++;
        }
    }
    public void triggerWords() {
        try {
            File file = new File("1");
            Scanner sc = new Scanner(file);
            int count = 0;
            for (int i = 0; i < email.getWord().size(); i++) {
                String str = email.getWord().get(i).toLowerCase();
                while(sc.hasNextLine()){
                    String trigger = sc.next().toLowerCase();
                    if(str.equals(trigger)) count++;
                }
            }
            if(count > 10) score++;
        } catch (FileNotFoundException e) {
            System.out.print("File not found");
        }
    }
    public void caseSensitivity() {
        int caseSens = 0;
        for (int i = 0; i < email.getWord().size(); i++) {
            String str = email.getWord().get(i);
            char Char = str.charAt(0);
            if (!str.equals("NUMBER") && Character.isUpperCase(Char)) {
                for (int j = 1; j < str.length(); j++) {
                    char currentChar = str.charAt(j);
                        if (Character.isUpperCase(currentChar)) {
                            caseSens++;
                            if(caseSens > 5){
                                score++;
                                break;
                            }
                        }
                }
            }
        }
    }
    public void scoreChecker(){
        if(score > 4){
            System.out.println("spam");//this will be more properl
        }else{
            System.out.println("not spam");//this will be more properl
        }
    }
}