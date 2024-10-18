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
export function firstThenApply<T, U>(a: Array<T>, p: (s: T) => boolean, f: (x: T) => U): U | undefined {
  // Use find() to search for the first value from the array that satisfies the predicate
  const found = a.find((value) => p(value));

  // If a value was found, return the result of applying the function, otherwise return undefined
  return found !== undefined ? f(found) : undefined;
}

// Write your powers generator here

// Write your line count function here

// Write your shape type and associated functions here

// Write your binary search tree implementation here
