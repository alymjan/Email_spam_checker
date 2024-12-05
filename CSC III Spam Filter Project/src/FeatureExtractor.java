/*
This is a feature extractor. We used this clas
to determine which one of our methods were useful
and which ones were redundant.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    public int wordCount() {
        int min = 20;
        int max = 300;
        int count = email.getWord().size();
        if (count < min || count > max) {
            score++;
            return 1;
        }
        return 0;
    }

    public int hyperLinkCheck() {
        ArrayList<String> arr = new ArrayList<>(email.getWord());
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equalsIgnoreCase("hyperlink")) {
                score += 4;
                return 1;
            }
        }
        return 0;
    }

    public int wordLength() {
        int breakOut = 0;
        for (int i = 0; i < email.getWord().size(); i++) {
            if (breakOut > 4) {
                score += 4;
                return 1;
            }
            if (email.getWord().get(i).length() > 12) breakOut++;
        }
        return 0;
    }

    public int specialCharaters() {//this was proven redundant after hours of testing via Feature Extractor
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
        if (breakOut > 2) {//might need to be adjusted
            score += 3;
            return 1;
        }
        return 0;
    }

    public int repeatedWords() {
        int breakout = 0;
        for (int i = 0; i < email.getWord().size() - 1; i++) {
            String current = email.getWord().get(i);
            String next = email.getWord().get(i + 1);
            if (current.equals(next) && !current.equalsIgnoreCase("NUMBER")) {
                breakout++;
            }
            if (breakout > 1) {
                score += 2;
                return 1;
            }
        }
        return 0;
    }

    public int triggerWords() {
        try {
            int count = 0;
            for (int i = 0; i < email.getWord().size(); i++) {
                File file = new File("C:\\Users\\Admin\\Documents\\GitHub\\Email_spam_checker\\CSC III Spam Filter Project\\spamWords.csv");
                Scanner sc = new Scanner(file);
                String str = email.getWord().get(i).toLowerCase();
                while (sc.hasNextLine()) {
                    String trigger = sc.next().toLowerCase();
                    if (str.equals(trigger)) count++;
                }
            }
            if (count > 2) {
                score += 3;
                return 1;
            }
        } catch (FileNotFoundException e) {
            System.out.print("File spamWords.csv not found");
        }
        return 0;
    }

    public int caseSensitivity() {
        int caseSens = 0;
        for(int i = 0; i < email.getWord().size(); i++){
            String str = email.getWord().get(i);
            str = str.substring(1);
            for(int j = 0; j < str.length() - 1; j++){
                if((Character.isUpperCase(str.charAt(j)) && Character.isLowerCase(str.charAt(j+1))) || Character.isLowerCase(str.charAt(j)) && Character.isUpperCase(str.charAt(j+1))){
                    caseSens++;
                }
            }
            if(caseSens > 7){
                score++;
                return 1;
            }
        }
        return 0;
    }

    public int checkNonsenseText() {
        int breakOut = 0;
        for (String word : email.getWord()) {
            if (word.length() > 12 && word.matches("[a-zA-Z0-9]+")) {
                breakOut++;
                if (breakOut > 4) {
                    score += 2;
                    return 1;
                }
            }
        }
        return 0;
    }

    public int triggerPhrases() {
        try {
            int count = 0;
            File file = new File("C:\\Users\\Admin\\Documents\\GitHub\\Email_spam_checker\\CSC III Spam Filter Project\\spamPhrases.csv");
            Scanner sc = new Scanner(file);

            // Load all spam phrases into an array
            String[] spamPhrases = sc.useDelimiter("\\Z").next().split(",");
            sc.close();

            // Loop through email words for comparison
            ArrayList<String> emailWords = email.getWord(); // Assuming this returns an ArrayList of words
            int emailSize = emailWords.size();

            for (int i = 0; i < emailSize; i++) {
                // Check for one-word phrases
                String oneWord = emailWords.get(i);
                if (isPhraseMatch(oneWord, spamPhrases)) {
                    count++;
                }

                // Check for two-word phrases
                if (i < emailSize - 1) {
                    String twoWord = emailWords.get(i) + " " + emailWords.get(i + 1);
                    if (isPhraseMatch(twoWord, spamPhrases)) {
                        count++;
                    }
                }

                // Check for three-word phrases
                if (i < emailSize - 2) {
                    String threeWord = emailWords.get(i) + " " + emailWords.get(i + 1) + " " + emailWords.get(i + 2);
                    if (isPhraseMatch(threeWord, spamPhrases)) {
                        count++;
                    }
                }
            }

            // Determine if the score needs updating
            if (count >= 1) {
                score += 4; // Assuming `score` is a class-level variable
                return 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File spamPhrases.csv not found");
        }
        return 0;
    }

    // Helper method to check if a phrase matches any in the spam phrases
    private boolean isPhraseMatch(String phrase, String[] spamPhrases) {
        for (String spamPhrase : spamPhrases) {
            if (phrase.equalsIgnoreCase(spamPhrase.trim())) {
                return true;
            }
        }
        return false;
    }

    public void scoreChecker() {
        if (score < 6) {
            isHam = true;
        }
    }
}