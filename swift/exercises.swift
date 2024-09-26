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

// Write your first then lower case function here

// Write your say function here

// Write your meaningfulLineCount function here

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
