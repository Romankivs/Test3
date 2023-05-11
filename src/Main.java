import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

class Pair<L,R> {
    public L a;
    public R b;
    public Pair(L a, R b){
        this.a = a;
        this.b = b;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        Pair pair = (Pair) obj;
        return a.equals(pair.a) && b.equals(pair.b);
    }
}

class ResultsPrinter
{
    public void print(String str) {
        System.out.println(str);
    }

    public void printPair(String a, String b) {
        print(a + " - " + b);
    }

    public void printResults(List<Pair<String, String>> resultsList) {
        if (resultsList.isEmpty()) {
            throw new IllegalArgumentException("Empty input");
        }

        for (int i = 0; i < resultsList.size(); i++) {
            printPair(resultsList.get(i).a, resultsList.get(i).b);
        }
    }

    public void printError(String msg) {
        print("Error: " + msg);
    }
}
class InputReader {
    String readInputFromPath(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner input = new Scanner(file);
        String text = input.useDelimiter("\\A").next();
        return text;
    }
}

class WordDistanceAnalyzer {
    ResultsPrinter printer;

    InputReader inputReader;

    public WordDistanceAnalyzer(ResultsPrinter printer, InputReader inputReader) {
        this.printer = printer;
        this.inputReader = inputReader;
    }

    public int distanceBetweenWords(String a, String b) {
        int maxLength = Math.max(a.length(), b.length());
        int minLength = Math.min(a.length(), b.length());
        int distance = maxLength - minLength;
        for (int i = 0; i < minLength; i++) {
            if (Character.toLowerCase(a.charAt(i)) != Character.toLowerCase(b.charAt(i))) {
                distance++;
            }
        }
        return distance;
    }

    public void getPairsWithMaxDistance() {
        try {
            String input = inputReader.readInputFromPath("C:\\Users\\forus\\Desktop\\test.txt");

            Scanner scanner = new Scanner(input);

            Pattern delimPattern = Pattern.compile("[\\p{javaWhitespace}\\.!\"#$%&()*+^,-./:;<=>\\?@_\\{|\\}~\\[\\]\\\\]+");
            scanner.useDelimiter(delimPattern);
            List<String> words = new ArrayList<String>();
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (word.length() > 30) {
                    word = word.substring(0, 30);
                }
                String finalWord = word;
                if (words.stream().filter(x -> x.equalsIgnoreCase(finalWord)).count() == 0) {
                    words.add(word);
                }
            }

            int maxDistance = Integer.MIN_VALUE;
            List<Pair<String, String>> maxDistWordPairs = new ArrayList<Pair<String, String>>();
            for (int i = 0; i < words.size(); i++) {
                for (int j = i + 1; j < words.size(); j++) {
                    String firstWord = words.get(i);
                    String secondWord = words.get(j);
                    int distanceBetweenWords = distanceBetweenWords(firstWord, secondWord);
                    if (distanceBetweenWords == maxDistance) {
                        Pair<String, String> pairOfWords = new Pair<String, String>(firstWord, secondWord);
                        maxDistWordPairs.add(pairOfWords);
                    }
                    else if (distanceBetweenWords > maxDistance) {
                        maxDistWordPairs.clear();
                        Pair<String, String> pairOfWords = new Pair<String, String>(firstWord, secondWord);
                        maxDistWordPairs.add(pairOfWords);
                        maxDistance = distanceBetweenWords;
                    }
                }
            }

            printer.printResults(maxDistWordPairs);
        }
        catch (FileNotFoundException exception) {
            printer.printError("file not found");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        var analyzer = new WordDistanceAnalyzer(new ResultsPrinter(), new InputReader());
        analyzer.getPairsWithMaxDistance();
    }
}