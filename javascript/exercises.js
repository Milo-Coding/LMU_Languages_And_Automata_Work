import { open } from "node:fs/promises"

export function change(amount) {
  if (!Number.isInteger(amount)) {
    throw new TypeError("Amount must be an integer")
  }
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let [counts, remaining] = [{}, amount]
  for (const denomination of [25, 10, 5, 1]) {
    counts[denomination] = Math.floor(remaining / denomination)
    remaining %= denomination
  }
  return counts
}

export function firstThenLowerCase(listOfStrings, predicate) {
  // Check each string in the list of strings if it can be looped through, otherwise skip 
  for (let i = 0; i < listOfStrings?.length; i++) {  // the chaining operator ? checks if listOfStrings has a .length property and if so allows the loop, otherwise skips
    // If it (as a lowercase) satisfies the predicate, return that string
    if (predicate(listOfStrings[i].toLowerCase())) {
      return listOfStrings[i].toLowerCase()
    }
  }
  // If no strings satisfy the predicate, return undefined
  return undefined
}

export function* powersGenerator({ofBase, upTo}) {
  // starting base and corrosponding number
  let currentNum = 1  // any base to the power of 0 is 1
  while(currentNum <= upTo){
    yield currentNum
    currentNum *= ofBase
  }
}

export function say() {
  return
}

// Write your line count function here

// Write your Quaternion class here
