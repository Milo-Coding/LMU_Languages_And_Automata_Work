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

// Write your meaningfulLineCount function here

// Write your Quaternion data class here

// Write your Binary Search Tree interface and implementing classes here
