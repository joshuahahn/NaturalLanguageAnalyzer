import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;

public class CenterEmbeddings {

    private static final String[] transitive = { "knew", "chased", "liked", "loved", "saw" };
    private static final String[] intransitive = { "snored", "laughed", "ran" };

    private static class Relation {

        String predicate;
        String subject;
        String object;

        public Relation(String predicate, String subject, String object) {
            this.predicate = predicate;
            this.subject = subject;
            this.object = object;
        }

        public String toString() {

            if (object != null)
                return "(" + subject + "," + predicate + "," + object + ")";
            else
                return "(" + subject + "," + predicate + ")";
        }

    }

    public static List<Relation> parseSentence(String sentence)
            throws IllegalArgumentException {

        List<Relation> result = new LinkedList<>();

        // Variables to check the exception cases
        boolean hasOneSubject = false;
        String tempVerb = "";
        String tempSubject = "";

        // Stack that will be used as primary data storage structure
        Stack<String> wordStack = new Stack<String>();

        // Array of articles and relative pronouns
        String[] articles = { "that", "the", "a" };

        // words is an array of strings that holds every
        // whitespace-separated word from sentences
        String[] words = sentence.split(" ");

        // Iterate through every word in the sentence
        for (String word : words) {

            // If the word is a article or relative pronoun, ignore it
            if (containsWord(articles, word)) {
            }

            // If the word is a transitive verb
            else if (containsWord(transitive, word)) {
                String topWord = wordStack.pop();

                if (wordStack.isEmpty()) {
                    // Exception case: store the subject and verb
                    tempSubject = topWord;
                    tempVerb = word;
                    hasOneSubject = true;
                }

                else {
                    // Normal case: add the relation but keep the object in the stack
                    Relation newRelation = new Relation(word, topWord, wordStack.peek());
                    result.add(newRelation);
                }
            }

            // If the word is an intransitive verb
            else if (containsWord(intransitive, word)) {

                // If intransitive, the object is null.
                Relation newRelation = new Relation(word, wordStack.pop(), null);
                result.add(newRelation);
            }

            // Any other word is a noun (subject or object)
            else {
                // Exception: Create a new relation using stored temp variables.
                if (hasOneSubject) {
                    Relation newRelation = new Relation(tempVerb, tempSubject, word);
                    tempSubject = "";
                    tempVerb = "";
                    hasOneSubject = false;
                    result.add(newRelation);

                }
                // Push all nouns into the Stack.
                wordStack.push(word);
            }

        }
        return result;
    }

    // Helper function to see if a String is in a array of Strings.
    public static boolean containsWord(String[] wordList, String target) {

        for (int i = 0; i < wordList.length; i++) {
            if (wordList[i].equals(target)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        String test1 = "the child laughed";
        String test2 = "the child that the the woman loved laughed";
        String test3 = "the child that the the woman that the man knew loved laughed";
        String test4 = "the child saw the cat"; // hard
        String test5 = "the child that the man knew saw the cat"; // hard
        String test6 = "the child saw the cat that the man loved"; // even harder

        List<Relation> result = parseSentence(test6);
        for (Relation r : result) {
            System.out.println(r);
        }
    }

}