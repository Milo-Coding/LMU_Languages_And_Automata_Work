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
        // if we got an error trying to read the file, return a failure Result
        return .failure(.noSuchFile)
    }

    // if everything went right, return the total number of meaningful lines
    return .success(goodLines)
}

// Write your Quaternion struct here

// Write your Binary Search Tree enum here
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
