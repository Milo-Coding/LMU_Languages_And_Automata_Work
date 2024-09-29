import Foundation

struct NegativeAmountError: Error {}
struct NoSuchFileError: Error {}

func change(_ amount: Int) -> Result<[Int:Int], NegativeAmountError> {
    if amount < 0 {
        return .failure(NegativeAmountError())
    }
    var (counts, remaining) = ([Int:Int](), amount)
    for denomination in [25, 10, 5, 1] {
        (counts[denomination], remaining) = 
            remaining.quotientAndRemainder(dividingBy: denomination)
    }
    return .success(counts)
}

func firstThenLowerCase(of strings: [String], satisfying predicate: (String) -> Bool) -> String? {
    // for each string in the list of strings
    for string in strings {
        // if it passes the predicate
        if predicate(string) {
            // return the lower case string
            return string.lowercased()
        } 
    }
    // if no string passed the predicate, return nil
    return nil
}

// object for say
struct Say {
    // store our phrase
    let phrase: String

    // set the phrase to the given string
    init(_ toSay: String) {
        self.phrase = toSay
    }

    // create a new Say with the additional word
    func and(_ toSay: String) -> Say {
        return Say(self.phrase + " " + toSay)
    }
}
// function to create a say object with a default of the empty string
func say(_ toSay: String = "") -> Say {
    return Say(toSay)
}

// Handle errors while reading file
enum FileReadError: Error {
    case noSuchFile
}
// meaninful line counter
func meaningfulLineCount(_ filePath: String) async -> Result<Int, FileReadError> {
    // store our meaningfulLines
    var goodLines = 0

    // make sure we are able to read the file
    do {
        // get the contents of the file
        let fileContents = try String(contentsOfFile: filePath, encoding: .utf8)

        // check each line in the file
        for line in fileContents.split(separator: "\n") {
            // trim whitespace off the line
            let trimmedLine = line.trimmingCharacters(in: .whitespacesAndNewlines)

            // check if the line is meaningful (not empty and no leading #)
            if (trimmedLine != "" && trimmedLine[trimmedLine.startIndex] != "#") {
                goodLines += 1
            }
        }
    } catch {
        // if we got an error trying to find/open the file, return an Error Result
        return .failure(.noSuchFile)
    }

    // if everything went right, return the total number of meaningful lines
    return .success(goodLines)
}

// the CustomStringConvertible type allows us to change the description of the struct
struct Quaternion: CustomStringConvertible {
    // declareing coefficinets
    let a: Double
    let b: Double
    let c: Double
    let d: Double

    // initializer with default values in case we don't pass four args
    init(a: Double = 0, b: Double = 0, c: Double = 0, d: Double = 0) {
        self.a = a
        self.b = b
        self.c = c
        self.d = d
    }

    // coefficients together
    var coefficients: [Double] {[self.a, self.b, self.c, self.d]}

    // conjugate
    var conjugate: Quaternion {Quaternion(a: self.a, b: -self.b, c: -self.c, d: -self.d)}

    // static constant members
    static let ZERO = Quaternion(a: 0, b: 0, c: 0, d: 0)
    static let I = Quaternion(a: 0, b: 1, c: 0, d: 0)
    static let J = Quaternion(a: 0, b: 0, c: 1, d: 0)
    static let K = Quaternion(a: 0, b: 0, c: 0, d: 1)

    // toString override
    var description: String {
        // create a string to store our output
        var printString = ""

        // create a list for the order of our coefficient symbols
        let symbols = ["", "i", "j", "k"]

        // handle each coefficient in order
        for i in symbols.indices {
            // If our coefficient is zero we can skip its loop
            if (self.coefficients[i] == 0.0) {
                continue
            }

            // first check if we need a plus symbol (don't need if first non-zero coefficient or negative value)
            if (!printString.isEmpty && self.coefficients[i] > 0.0) {
                printString += "+"
            }

            // next add our coefficient if it isn't 1 or negative 1, unless it is the real number space
            if (abs(self.coefficients[i]) != 1.0 || symbols[i].isEmpty) {
                printString += String(self.coefficients[i])
            }
            // if it was -1 and not the real number component, add just the - sign
            else if (self.coefficients[i] == -1.0) {
                printString += "-"
            }

            // finally add the symbol for that coefficient
            printString += symbols[i]
        }

        // check edge case all zeros
        if (printString.isEmpty) {
            printString = "0"
        }

        // return the string
        return printString
    }

}
// overrides for addition, multiplication, and equality
func + (left: Quaternion, right: Quaternion) -> Quaternion {
    return Quaternion(a: left.a + right.a, b: left.b + right.b, c: left.c + right.c, d: left.d + right.d)
}
func == (left: Quaternion, right: Quaternion) -> Bool {
    return (left.a == right.a && left.b == right.b && left.c == right.c && left.d == right.d)
}
func * (left: Quaternion, right: Quaternion) -> Quaternion {
    let newA: Double = left.a * right.a - left.b * right.b - left.c * right.c - left.d * right.d
    let newB: Double = left.a * right.b + left.b * right.a + left.c * right.d - left.d * right.c
    let newC: Double = left.a * right.c - left.b * right.d + left.c * right.a + left.d * right.b
    let newD: Double = left.a * right.d + left.b * right.c - left.c * right.b + left.d * right.a
    return Quaternion(a: newA, b: newB, c: newC, d: newD);
}

enum BinarySearchTree {
    // list the possible cases
    case empty
    indirect case node(BinarySearchTree, Int, BinarySearchTree)

    // return the number of nodes in our tree
    var size: Int {
        switch self{
            // if it's empty, return 0
            case .empty: return 0
            // if it isn't empty, get the size of its children and add 1 for the size of the current node
            case let .node(left, _, right): return left.size + 1 + right.size
        }
    }
}
