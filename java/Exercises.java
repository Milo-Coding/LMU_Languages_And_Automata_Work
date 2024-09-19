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

    // Write your say function here

    // Write your line count function here
}

// Write your Quaternion record class here

// Write your BinarySearchTree sealed interface and its implementations here
