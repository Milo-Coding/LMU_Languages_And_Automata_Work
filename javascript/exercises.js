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

export function say(word) {
  // set up a variable to store all our words
  let sentence = []

  // nested function to handle chained arguments
  function inner(word) {
    // add the words to the sentance if there are any left
    if(word != undefined) {
      sentence.push(word)
      // check for more words
      return inner
    }
    // if there are no words left to add, return our whole sentence
    return sentence.join(" ")
  }

  // if we have at least one word, start adding words to the sentance
  if(word != undefined) {
    sentence.push(word)
    // check for more words
    return inner
  }

  // if there were no words just return an empty string
  return ""
}

export async function meaningfulLineCount(file) {
  
}

// Write your Quaternion class here
