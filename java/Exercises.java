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

    static Integer meaningfulLineCount(String filePath)
        // this handles file not found errors (note it is outside the function handling)
        throws IOException
    {
        // a counter for our meaninful lines
        var goodLines = 0;

        // first create a FileReader object
        FileReader fr = new FileReader(filePath);
        // the FileReader allows us to create a BufferedReader
        BufferedReader br = new BufferedReader(fr);

        // use try-with-resources to make sure the file gets closed at the end
        try {
            // count the number of lines that meet the conditions of a meaninful line
            long count = br.lines().filter(line -> isMeaningfulLine(line)).count();
            // convert to an int to match our return type
            goodLines = (int) count;

        // the finally gets run even if the try had an error
        } finally {
            // we close the file at the end
            br.close();
        }
        return goodLines;
    }
    // helper function to check if a line is meaninful
    static boolean isMeaningfulLine(String line) {
        // strip the whitespace off the line
        line = line.strip();
        // if the stripped line isn't empty and doesn't start with a #, we can count it
        return (line != "" && !line.substring(0, 1).equals("#"));
    }
}

// Write your Quaternion record class here

record Quaternion(double a, double b, double c, double d) {

    // static constant members
    static public Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    static public Quaternion I = new Quaternion(0, 1, 0, 0);
    static public Quaternion J = new Quaternion(0, 0, 1, 0);
    static public Quaternion K = new Quaternion(0, 0, 0, 1);

    // constructor
    public Quaternion(double a, double b, double c, double d) {
        // don't allow NaNs in constructor
        if (Double.isNaN(a) | Double.isNaN(b) | Double.isNaN(c) | Double.isNaN(d)) {
            throw new IllegalArgumentException("Coefficients cannot be NaN");
        }

        // set coefficients to their non-NaN values
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    // getter for all the coefficients
    public List<Double> coefficients() {
        return List.of(this.a, this.b, this.c, this.d);
    }

    // getter for the conjugate
    public Quaternion conjugate() {
        return new Quaternion(this.a, -this.b, -this.c, -this.d);
    }

    // addition
    public Quaternion plus(Quaternion other) {
        return new Quaternion(this.a + other.a(), this.b + other.b(), this.c + other.c(), this.d + other.d());
    }

    // multiplication
    public Quaternion times(Quaternion other) {
        double newA = this.a * other.a - this.b * other.b - this.c * other.c - this.d * other.d;
        double newB = this.a * other.b + this.b * other.a + this.c * other.d - this.d * other.c;
        double newC = this.a * other.c - this.b * other.d + this.c * other.a + this.d * other.b;
        double newD = this.a * other.d + this.b * other.c - this.c * other.b + this.d * other.a;
        return new Quaternion(newA, newB, newC, newD);
    }

    // equals
    public boolean equals(Quaternion other) {
        return (this.a == other.a() | this.b == other.b() | this.c == other.c() | this.d == other.d());
    }

    // toString
    public String toString() {
        // create a string to store our output
        var printString = "";

        // create a list for the order of our coefficient symbols
        String[] symbols = {"", "i", "j", "k"};

        // handle each coefficient in order (we have to use an indexed loop becasue Lists are stinky in java)
        for (int i = 0; i < symbols.length; i++) {
            // If our coefficient is zero we can skip its loop
            if (this.coefficients().get(i) == 0) {
                continue;
            }

            // first check if we need a plus symbol (don't need if first non-zero coefficient or negative value)
            if (!printString.isEmpty() && this.coefficients().get(i) > 0) {
                printString += "+";
            }
            // next add our coefficient if it isn't 1 or negative 1, unless it is the real numbers space
            if (Math.abs(this.coefficients().get(i)) != 1 | symbols[i].equals("")) {
                printString += this.coefficients().get(i);
            }
            // if it was -1 and not the real number component, ad just the - sign
            else if (this.coefficients().get(i) == -1) {
                printString += "-";
            }
            // finally add the symbol for that coefficent and remove it from our list of symbols
            printString += symbols[i];
        }
        
        // check edge case all zeros
        if (printString.isEmpty()) {
            printString = "0";
        }

        // return the string
        return printString;
    }
}

// interface for our BST Classes
sealed interface BinarySearchTree permits Empty, Node {
    public int size();
    public String toString();
    public boolean contains(String node);
    public BinarySearchTree insert(String node);
}

// the empty BST will always be our starting point and will be at the end of each leaf node
final class Empty implements BinarySearchTree {

    // the size of an empty tree is 0
    public int size() {
        return 0;
    }

    // the string that represents an empty string is "()"
    public String toString() {
        return "()";
    }

    // empty trees don't contain anything
    public boolean contains(String target) {
        return false;
    }

    // when a node is added to an empty tree, replace the tree with that node
    public BinarySearchTree insert(String node) {
        return new Node(node);
    }
}

final class Node implements BinarySearchTree {

    // all the properties of a node in a BST
    final private String name;
    final private BinarySearchTree childL;
    final private BinarySearchTree childR;

    // construct our node (it is a leaf by default)
    public Node(String node) {
        this.name = node;
        this.childL = new Empty();
        this.childR = new Empty();
    }

    // constructor for nodes with children given
    public Node(String node, BinarySearchTree childL, BinarySearchTree childR) {
        this.name = node;
        this.childL = childL;
        this.childR = childR;
    }

    // the size of a node is 1 + the size of its children
    public int size() {
        return 1 + this.childL.size() + this.childR.size();
    }

    // the string is formated "((childL)name(childR))" where each child also obeys this format
    // empty children are ignored rather than represented as ()
    public String toString() {
        // get the names of each child if they aren't Empty nodes
        var cLName = "";
        if (this.childL instanceof Node) {
            cLName = this.childL.toString();
        }
        var cRName = "";
        if (this.childR instanceof Node) {
            cRName = this.childR.toString();
        }

        // return our formatted full name
        return "(" + cLName + this.name + cRName + ")";
    }

    // if this node or one of its children is the target, the target is in the tree
    public boolean contains(String target) {
        if (this.name == target | this.childL.contains(target) | this.childR.contains(target)) {
            return true;
        }
        return false;
    }

    // add target to the correct position in the tree
    public BinarySearchTree insert(String addNode) {
        // if the value to add is less than the current node, it will be inserted along the left branch
        if (addNode.compareTo(this.name) < 0) {
            // pass it down to the child for insertion and return the new tree
            return new Node(this.name, this.childL.insert(addNode), this.childR);
        }

        // if the value to add is greater than the current node, it will be inserted along the right branch
        if (addNode.compareTo(this.name) > 0) {
            // pass it down to the child for insertion and return the new tree
            return new Node(this.name, this.childL, this.childR.insert(addNode));
        }

        // if the value to add is already in our tree (or equal to one of those values), no need to add it
        return new Node(this.name, this.childL, this.childR);
    }
} 