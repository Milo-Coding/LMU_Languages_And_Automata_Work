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

// TODO: ask about <T, U> vs any vs String | number
export function firstThenApply<T, U>(
  a: Array<T>,
  p: (s: T) => boolean,
  f: (x: T) => U
): U | undefined {
  // Use find() to search for the first value from the array that satisfies the predicate
  const found = a.find((value) => p(value))

  // If a value was found, return the result of applying the function, otherwise return undefined
  return found !== undefined ? f(found) : undefined
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

// Write your binary search tree implementation here
