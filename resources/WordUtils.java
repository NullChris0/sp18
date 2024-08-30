/**
 * includes functions we can run on lists of words
 */
public class WordUtils {

    /* method overloading is ok for given AList as parameter
    however, this means code duplication*/
    /* use interface inheritance, AList and SLList share an "is-a" relationship */
    public static String longest(List61B<String> list) {
        int maxDex = 0;
        for (int i = 0; i < list.size(); i += 1) {
            String longestString = list.get(maxDex);
            String thisString = list.get(i);
            if (thisString.length() > longestString.length()) {
                maxDex = i;
            }
        }
        return list.get(maxDex);
    }

    public static void main(String[] args) {
        /* subclass is always a superclass
        * static type is List61B and dynamic type is AList
        * It’s dynamic because it changes based on the type of the object it’s currently referring to
        * When Java runs a method that is overriden, it searches for the appropriate method signature
         in it's dynamic type and runs it.
        * IMPORTANT: This does not work for overloaded methods which checks static type
        */
        List61B<String> someList = new AList<String>();
        someList.addLast("elk");
        someList.addLast("abc");
        System.out.println(longest(someList));
    }
}
