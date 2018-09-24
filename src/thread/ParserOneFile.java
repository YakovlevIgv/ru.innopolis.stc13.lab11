package thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserOneFile implements Runnable {

    private List<String> resultList;
    private String[] words;
    private String source;


    public ParserOneFile(List<String> resultList, String[] words, String source) {
        this.resultList = resultList;
        this.words = words;
        this.source = source;
    }

    @Override
    public void run() {
        System.out.println("\nметод RUN потока " + Thread.currentThread().getName());
        parse(source);
    }

    private void parse(String source) {
        try (Scanner scanner = new Scanner(new File(source))) {
            Pattern pattern = Pattern.compile("[\\w\\d\\s]*[\\?\\.…!]");
            Matcher matcher;
            scanner.useDelimiter("[\n]");
            while (scanner.hasNext()) {
                matcher = pattern.matcher(scanner.next());
                while (matcher.find()) {
                    String sentence = matcher.group();
                    checkWordsInSentence(sentence, words);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveResultSentence(String sentence) {
        synchronized (this.resultList)
        {
            if(resultList.isEmpty()){
                resultList.add(sentence.trim() + "\n");
            }
            else{
                resultList.add(" " + sentence.trim() + "\n");
            }
        }
    }

    private boolean checkWordInSentence(String sentence, String word) {
        sentence = sentence.toLowerCase();
        return sentence.contains(word);
    }

    private void checkWordsInSentence(String sentence, String[] words) {
        for (String word : words) {
            if (checkWordInSentence(sentence, word)) {
                saveResultSentence(sentence);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "SingleFileParser{" +
                "source='" + source + '\'' +
                '}';
    }
}
