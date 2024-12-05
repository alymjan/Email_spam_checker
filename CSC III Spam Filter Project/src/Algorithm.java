/* Written and edited by
Alymjan Rejepov, student id# 1014524
Kevin Ha, student id# 1051033

This is the main code for our algorithm.
This is the email spam filter with accuracy of 81%.
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Algorithm {
    EmailStorage email;
    int score;
    public boolean isHam;
    public Algorithm(EmailStorage email) {
        this.email = email;
        score = 0;
        isHam = false;
    }

    public int wordCount() {//this method counts the number of words in the email, and then checks the count and checking it against a averaged min and max word count of general emails. if it excedes below the min or above the max it will then add 1 to the score that will be used to classify the email ham or spam after all the test are done.
        int min = 20;
        int max = 300;
        int count = email.getWord().size();
        if (count < min || count > max) {
            score++;
            return 1;
        }
        return 0;
    }

    public int hyperLinkCheck() {//this checks the email for hyperlinks which are generally in spam emails. therefore one of our better spam checks. if the hyperlink is found it will add 4 to the score that we would use at the end of all the test's to see if it's ham or spam.
        ArrayList<String> arr = new ArrayList<>(email.getWord());
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equalsIgnoreCase("hyperlink")) {
                score += 4;
                return 1;
            }
        }
        return 0;
    }

    public int wordLength() {//this checks the length of each word to a averaged word length. if the word length exceed the average length it adds 1 to breakout then once break out reaches it's condition it'll add 4 to the score that we would than use after all the test is performed to see if it is a ham or spam.
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

    public int repeatedWords() {//this checks for repeated words by checking the word at the current index to the next index. checking to see how many times a word is repeated one after another. if the condition is met it adds 1 to breakout and once break out meets the limit it than adds 2 to our score to grade at the end of all of the test to see if it's a spam or not.
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

    public int triggerWords() {// this checks word for word of the email to a set list of words linked to spam emails. Once it finds one 1 will be added to the count until it meets its condition to then add 3 to the score which will be used to classify the email ham or spam.
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

    public int caseSensitivity() { //this method takes each word from the EmailStorage arraylist and compares the consistency in the cases of each words. It ignores the first letter because of the English grammar and it would have takes us too long to add that too.
        int caseSens = 0;           // caseSens is a checking score to check how many times casesensitivity does not pass the test.
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

    public int checkNonsenseText() {// this checks for most gibberish words for example fjibdi or 89dwss08 and if it finds this in the email it will add 1 to breakout and once breakout meets its condition it will then add 2 to the score which after all the test are run will be used to calcualte ham or spam.
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

    public int triggerPhrases() {//this compares the email to a set of phrases linked to spam emails. if it does match it will than add one to the count and as long as if the count is 1 or more 4 would be added to the score to be evaluated as ham or spam after all the test.
        try {
            int count = 0;
            File file = new File("C:\\Users\\Admin\\Documents\\GitHub\\Email_spam_checker\\CSC III Spam Filter Project\\spamPhrases.csv");
            Scanner sc = new Scanner(file);


            String[] spamPhrases = sc.useDelimiter("\\Z").next().split(",");
            sc.close();


            ArrayList<String> emailWords = email.getWord();
            int emailSize = emailWords.size();

            for (int i = 0; i < emailSize; i++) {

                String oneWord = emailWords.get(i);
                if (isPhraseMatch(oneWord, spamPhrases)) {
                    count++;
                }


                if (i < emailSize - 1) {
                    String twoWord = emailWords.get(i) + " " + emailWords.get(i + 1);
                    if (isPhraseMatch(twoWord, spamPhrases)) {
                        count++;
                    }
                }


                if (i < emailSize - 2) {
                    String threeWord = emailWords.get(i) + " " + emailWords.get(i + 1) + " " + emailWords.get(i + 2);
                    if (isPhraseMatch(threeWord, spamPhrases)) {
                        count++;
                    }
                }
            }

            if (count >= 1) {
                score += 4;
                return 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File spamPhrases.csv not found");
        }
        return 0;
    }


    private boolean isPhraseMatch(String phrase, String[] spamPhrases) {
        for (String spamPhrase : spamPhrases) {
            if (phrase.equalsIgnoreCase(spamPhrase.trim())) {
                return true;
            }
        }
        return false;
    }

    public void scoreChecker() {//this is what classifies ham or spam as a result of all the test.
        if (score < 6) {
            isHam = true;
        }
    }
}