import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

fun change(amount: Long): Map<Int, Long> {
    require(amount >= 0) { "Amount cannot be negative" }
    
    val counts = mutableMapOf<Int, Long>()
    var remaining = amount
    for (denomination in listOf(25, 10, 5, 1)) {
        counts[denomination] = remaining / denomination
        remaining %= denomination
    }
    return counts
}

fun firstThenLowerCase(strings: List<String>, predicate: (String) -> Boolean): String? {
    // check all values in our list against our predicate
    strings.forEach { string -> if (predicate(string)) { return string.lowercase() } }
    // if nothing passed the predicate
    return null
}

// create a say class where the default phrase is the empty string
class Say(val toSay: String = "") {
    // set our phrase property to what we want to say
    val phrase: String = toSay

    // if we want to add a word to our phrase
    fun and(toSay: String): Say {
        return Say(phrase + " " + toSay)
    }
}
// say with a parameter
fun say(toSay: String = ""): Say {
    return Say(toSay)
}

fun meaningfulLineCount(filePath: String): Long {
    // create a counter for our meaningful lines (must be a long to match return type)
    var goodLines: Long = 0L

    // use BufferedReader to go through each line of the file
    BufferedReader(FileReader(filePath)).use { reader ->
        // read the first line of the file (it will be null if there are no lines in the file)
        var line: String? = reader.readLine()
        // loop until we reach the end of the file
        while (line != null) {
            // if the line is meaningful
            if (line.trim().isNotEmpty() && line.trim().substring(0, 1) != "#") {
                // count it
                goodLines++
            }
            // read the next line (it will be null if we are currently on the last line)
            line = reader.readLine()
        }
    }
    // return our count
    return goodLines
}

data class Quaternion(val a: Double, val b: Double, val c: Double, val d: Double) {
    // static constants
    companion object {
        // can't actually put const for each var because it is incompatable with non-primitive types (or non-strings) like Quaternion
        val ZERO = Quaternion(0.0, 0.0, 0.0, 0.0)
        val I = Quaternion(0.0, 1.0, 0.0, 0.0)
        val J = Quaternion(0.0, 0.0, 1.0, 0.0)
        val K = Quaternion(0.0, 0.0, 0.0, 1.0)
    }

    // gets coefficients
    fun coefficients(): List<Double> {
        return listOf(this.a, this.b, this.c, this.d)
    }

    // gets conjugate
    fun conjugate(): Quaternion {
        return Quaternion(this.a, -this.b, -this.c, -this.d)
    }

    // overload addition
    operator fun plus(other: Quaternion): Quaternion {
        return Quaternion(this.a + other.a, this.b + other.b, this.c + other.c, this.d + other.d)
    }
    
    // overload multiplication
    operator fun times(other: Quaternion): Quaternion {
        var newA: Double = this.a * other.a - this.b * other.b - this.c * other.c - this.d * other.d;
        var newB: Double = this.a * other.b + this.b * other.a + this.c * other.d - this.d * other.c;
        var newC: Double = this.a * other.c - this.b * other.d + this.c * other.a + this.d * other.b;
        var newD: Double = this.a * other.d + this.b * other.c - this.c * other.b + this.d * other.a;
        return Quaternion(newA, newB, newC, newD);
    }

    // overload toString
    override fun toString(): String {
        // create a string to store our output
        var printString = ""

        // create a list for the order of our coefficient symbols
        val symbols = arrayOf("", "i", "j", "k")

        // handle each coefficient in order
        for (i in symbols.indices) {
            // If our coefficient is zero we can skip its loop
            if (this.coefficients()[i] == 0.0) {
                continue
            }

            // first check if we need a plus symbol (don't need if first non-zero coefficient or negative value)
            if (printString.isNotEmpty() && this.coefficients()[i] > 0.0) {
                printString += "+"
            }

            // next add our coefficient if it isn't 1 or negative 1, unless it is the real number space
            if (kotlin.math.abs(this.coefficients()[i]) != 1.0 || symbols[i].isEmpty()) {
                printString += this.coefficients()[i].toString()
            }
            // if it was -1 and not the real number component, add just the - sign
            else if (this.coefficients()[i] == -1.0) {
                printString += "-"
            }

            // finally add the symbol for that coefficient
            printString += symbols[i]
        }

        // check edge case all zeros
        if (printString.isEmpty()) {
            printString = "0"
        }

        // return the string
        return printString
    }
}

// interface for our BST Classes
sealed interface BinarySearchTree {
    fun size(): Int
    override fun toString(): String
    fun contains(target: String): Boolean
    fun insert(addNode: String): BinarySearchTree

    // the empty BST will always be our starting point and will be at the end of each leaf node
    object Empty : BinarySearchTree {
        // the size of an empty tree is 0
        override fun size(): Int = 0
        // the string that represents an empty string is "()"
        override fun toString(): String = "()"
        // empty trees don't contain anything
        override fun contains(target: String): Boolean = false
        // when a node is added to an empty tree, replace the tree with that node
        override fun insert(nodeName: String): BinarySearchTree = Node(nodeName)
    }

    // data class for a non-empty node in the tree
    data class Node(val nodeName: String, val childL: BinarySearchTree = Empty, val childR: BinarySearchTree = Empty) : BinarySearchTree {

        // the size of a node is 1 + the size of its children
        override fun size(): Int {
            return 1 + this.childL.size() + this.childR.size()
        }

        // the string is formated "((childL)name(childR))" where each child also obeys this format
        // empty children are ignored rather than represented as ()
        override fun toString(): String {
            // get the names of each child if they aren't Empty nodes
            var cLName = "";
            if (this.childL is Node) {
                cLName = this.childL.toString()
            }
            var cRName = "";
            if (this.childR is Node) {
                cRName = this.childR.toString()
            }

            // return our formatted full name
            return "(" + cLName + this.nodeName + cRName + ")"
        }

        // if this node or one of its children is the target, the target is in the tree
        override fun contains(target: String): Boolean {
            if (this.nodeName == target || this.childL.contains(target) || this.childR.contains(target)) {
                return true
            }
            return false
        }

        // add target to the correct position in the tree
        override fun insert(addNode: String): BinarySearchTree {
            // if the value to add is less than the current node, it will be inserted along the left branch
            if (addNode.compareTo(this.nodeName) < 0) {
                // pass it down to the child for insertion and return the new tree
                return Node(this.nodeName, this.childL.insert(addNode), this.childR)
            }

            // if the value to add is greater than the current node, it will be inserted along the right branch
            if (addNode.compareTo(this.nodeName) > 0) {
                // pass it down to the child for insertion and return the new tree
                return Node(this.nodeName, this.childL, this.childR.insert(addNode))
            }

            // if the value to add is already in our tree (or equal to one of those values), no need to add it
            return this
        }
    }
}
