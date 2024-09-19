import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Exercises {
    static Map<Integer, Long> change(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        var counts = new HashMap<Integer, Long>();
        for (var denomination : List.of(25, 10, 5, 1)) {
            counts.put(denomination, amount / denomination);
            amount %= denomination;
        }
        return counts;
    }

    static Optional<String> firstThenLowerCase(List<String> strings, Predicate<String> predicate) {
        // convert our list of strings into a stream
        // filter the list to only keep values that satisfy the predicate
        // take the first element of the stream
        // use .map to convert the sting to lower case because it allows optional strings
        return strings.stream().filter(predicate).findFirst().map(String::toLowerCase);
    }

    // the object that supports the say functions
    public static class SayObject {
        // This is is the string where we store everthing we will say. It is private so partial states aren't shared
        private String phrase = "";

        // This allows a SayObject to be created without any arguments
        public SayObject() {
            // default constructor
        }

        // This allows a SayObject to be created with an argument
        public SayObject(String word) {
            // the passed string becomes the word we will say when phrase() is called
            phrase = word;
        }

        // handles chaining .and(word) to an existing SayObject
        public SayObject and(String anotherWord) {
            // to allow multiple .ands to be chained, return a new SayObject with an updated phrase
            return new SayObject(phrase + " " + anotherWord);
        }

        // returns the whole phrase in the SayObject when called
        String phrase(){  // not static becasue that's not how the tests call it
            return phrase;
        }
    }

    // the functions to create a SayObject and chain from
    public static SayObject say() {
        return new SayObject();  // creates an empty SayObject
    }
    public static SayObject say(String word) {
        return new SayObject(word);  // craetes a SayObject with one word initialized as the phrase
    }

    // Write your line count function here
}

// Write your Quaternion record class here

// Write your BinarySearchTree sealed interface and its implementations here
