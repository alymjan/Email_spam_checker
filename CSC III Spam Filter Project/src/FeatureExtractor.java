import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
public class FeatureExtractor {
    EmailStorage email;
    int score;
    public boolean isHam;
    public FeatureExtractor(EmailStorage email) {
        this.email = email;
        score = 0;
        isHam = false;
    }
    public void wordCount() {
        int min = 20;
        int max = 300;
        int count = email.getWord().size();
        if (count < min || count > max) {
            score++;
        }
    }
    public void hyperLinkCheck() {
        ArrayList<String> arr = new ArrayList<>(email.getWord());
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equalsIgnoreCase("hyperlink")) {
                score++;
                break;
            }
        }
    }
    public void wordLength() {
        int breakOut = 0;
        for (int i = 0; i < email.getWord().size(); i++) {
            if(breakOut > 3) {
                score++;
                break;
            }
            if (email.getWord().get(i).length() > 12) breakOut++;
        }
    }
    public void specialCharaters() {
        int breakOut = 0;
        for (int i = 0; i < email.getWord().size(); i++) {
            String str = email.getWord().get(i).toUpperCase();
            for (int j = 0; j < str.length(); j++) {
                char currentChar = str.charAt(j);
                if (!Character.isLetter(currentChar)) {
                    if (currentChar != ',' && currentChar != '.' && currentChar != '?'
                            && currentChar != '!' && currentChar != '\'' && currentChar != '_') {
                        breakOut++;
                    }
                }
            }
        }
        if(breakOut > 4){//might need to be adjusted
            score++;
        }
    }
    public void repeatedWords() {
        int breakout = 0;
        for (int i = 0; i < email.getWord().size() - 1; i++) {
            String current = email.getWord().get(i);
            String next = email.getWord().get(i + 1);
            if (current.equals(next) && !current.equalsIgnoreCase("NUMBER")){
                breakout++;
            }
            if(breakout > 3){
                score++;
                break;
            }
        }
    }
    public void triggerWords() {
        try {
            File file = new File("C:\\Users\\Admin\\Documents\\GitHub\\Email_spam_checker\\CSC III Spam Filter Project\\1.csv");
            Scanner sc = new Scanner(file);
            int count = 0;
            for (int i = 0; i < email.getWord().size(); i++) {
                String str = email.getWord().get(i).toLowerCase();
                while(sc.hasNextLine()){
                    String trigger = sc.next().toLowerCase();
                    if(str.equals(trigger)) count++;
                }
            }
            if(count > 3) score++;
        } catch (FileNotFoundException e) {
                System.out.print("File 1.csv not found");
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
                            if(caseSens > 3){
                                score++;
                                break;
                            }
                        }
                }
            }
        }
    }
    public void scoreChecker(){
        if(score < 5){
            isHam = true;
        }
    }
}