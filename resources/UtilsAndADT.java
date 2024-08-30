import java.util.*;

public class UtilsAndADT {
    public static void main(String[] args) {
        List<String> list = getWords("D:\\JavaLearn\\CS61B2018SPRING\\library-sp18\\data\\words.txt");
        System.out.println(countUniqueWords(list));
        List<String> t = new ArrayList<>(list);
        System.out.println(collectWordCount(list, t));
    }
    public static String cleanString(String str) {
        return str.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
    }

    public static List<String> getWords(String inputFileName) {
        List<String> lst = new ArrayList<String>();
        In in = new In(inputFileName);
        while (!in.isEmpty()) {
            lst.add(cleanString(in.readString())); //optionally, define a cleanString() method that cleans the string first.
        }
        return lst;
    }

    public static int countUniqueWords(List<String> words) {
        Set<String> ss = new HashSet<>(words);
        return ss.size();
    }

    public static Map<String, Integer> collectWordCount(List<String> words, List<String> target) {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (String t: target) {
            counts.put(t, 0);
        }
        for (String s: words) {
            if (counts.containsKey(s)) {
                counts.put(s, counts.get(s)+1);
            }
        }
        return counts;
    }

}
