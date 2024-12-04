import java.util.ArrayList;

public class EmailStorage {
    private ArrayList<String> word;
    private String label;

    public EmailStorage(String label){
        this.label = label;
        this.word = new ArrayList<>();
    }

    public void addWord(String word){
        this.word.add(word);
    }

    public String getLabel(){
        return label;
    }

    public ArrayList<String> getWord(){
        return word;
    }

    public String toString(){
        String temp = label;
        temp+=word.toString();
        return temp;
    }
}
