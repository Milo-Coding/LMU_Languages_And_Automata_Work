import { open } from "node:fs/promises"

export function change(amount: bigint): Map<bigint, bigint> {
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let counts: Map<bigint, bigint> = new Map()
  let remaining = amount
  for (const denomination of [25n, 10n, 5n, 1n]) {
    counts.set(denomination, remaining / denomination)
    remaining %= denomination
  }
  return counts
}

export function firstThenApply<T, U>(
  a: Array<T>,
  pred: (s: T) => boolean,
  func: (x: T) => U
): U | undefined {
  // Use find() to search for the first value from the array that satisfies the predicate
  const found = a.find((value) => pred(value))

  // If a value was found, return the result of applying the function, otherwise return undefined
  return found !== undefined ? func(found) : undefined
}

export function* powersGenerator(base: bigint): Generator<bigint> {
  let exponent: bigint = 0n

  // While loop is needed to return additional values when powersGenerateor is called again
  while (true) {
    yield base ** exponent
    exponent += 1n
  }
}

export async function meaningfulLineCount(filePath: any) {
  let lineCount: number = 0

  // Read each line of the code
  const file = await open(filePath, "r")
  for await (const line of file.readLines()) {
    //  If the line is non-empty and non-leading-#, count it
    if (line.trim() != "" && line.trim()[0] != "#") {
      lineCount++
    }
  }

  return lineCount
}

// TODO ask about tostring
export type Shape = Box | Sphere

interface Box {
  // Properties of a box
  kind: "Box"
  width: number
  length: number
  depth: number
}

interface Sphere {
  // Properties of a sphere
  kind: "Sphere"
  radius: number
}

export function volume(shape: Shape): number {
  if (shape.kind === "Box") {
    // Gets the dimensions from the box interface
    const { width, length, depth } = shape
    return width * length * depth
  }

  // Shape only has Boxes and Spheres so no else if statement needed
  // Gets the radius from the sphere interface
  const { radius } = shape
  return (4 / 3) * Math.PI * Math.pow(radius, 3)
}

export function surfaceArea(shape: Shape): number {
  if (shape.kind === "Box") {
    // Gets the dimensions from the box interface
    const { width, length, depth } = shape
    return 2 * (width * length + width * depth + length * depth)
  }

  // Shape only has Boxes and Spheres so no else if statement needed
  // Gets the radius from the sphere interface
  const { radius } = shape
  return 4 * Math.PI * Math.pow(radius, 2)
}

export interface BinarySearchTree<T> {
  // Define the methods of a Binary Search Tree
  size(): number
  insert(value: T): BinarySearchTree<T>
  contains(value: T): boolean
  inorder(): Iterable<T>
  toString(): string
}

export class Empty<T> implements BinarySearchTree<T> {
  // An empty tree doesn't contain anything
  size(): number {
    return 0
  }
  insert(value: T): BinarySearchTree<T> {
    return new Node(value, new Empty(), new Empty())
  }
  contains(value: T): boolean {
    return false
  }
  *inorder(): Iterable<T> {
    // nothing to yield so nothing to code
  }
  toString(): string {
    return "()"
  }
}

export class Node<T> implements BinarySearchTree<T> {
  private left: BinarySearchTree<T>
  private right: BinarySearchTree<T>
  private value: T

  constructor(value: T, left: BinarySearchTree<T>, right: BinarySearchTree<T>) {
    this.value = value
    this.left = left
    this.right = right
  }

  size(): number {
    return 1 + this.left.size() + this.right.size()
  }

  insert(value: T): BinarySearchTree<T> {
    // if the value to add is less than the current node, it will be inserted along the left branch
    if (value < this.value) {
      return new Node(this.value, this.left.insert(value), this.right)
    }

    // if the value to add is greater than the current node, it will be inserted along the right branch
    if (value > this.value) {
      return new Node(this.value, this.left, this.right.insert(value))
    }

    // if the value to add is already in our tree (or equal to one of those values), no need to add it
    return new Node(this.value, this.left, this.right)
  }

  contains(value: T): boolean {
    if (
      this.value == value ||
      this.left.contains(value) ||
      this.right.contains(value)
    ) {
      return true
    }
    return false
  }

  *inorder(): Iterable<T> {
    // Order is: values of nodes on the left branch, then current node, then right branch for given node
    if (this.left instanceof Node) {
      yield* this.left.inorder()
    }
    yield this.value
    if (this.right instanceof Node) {
      yield* this.right.inorder()
    }
  }

  toString(): string {
    // get the names of each child if they aren't Empty nodes
    let leftValue = ""
    if (this.left instanceof Node) {
      leftValue = this.left.toString()
    }
    let rightValue = ""
    if (this.right instanceof Node) {
      rightValue = this.right.toString()
    }

    // return our formatted full name
    return "(" + leftValue + this.value + rightValue + ")"
  }
}
