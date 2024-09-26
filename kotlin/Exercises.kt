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
        return "0"
    }
}

// Write your Binary Search Tree interface and implementing classes here
