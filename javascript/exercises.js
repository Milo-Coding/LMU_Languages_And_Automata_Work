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
  // Use find() to search for the first string that satisfies the predicate
  const found = listOfStrings?.find((str) => predicate(str.toLowerCase()))

  // Return the string in lowercase if found, otherwise return undefined
  return found?.toLowerCase()
}

export function* powersGenerator({ ofBase: base, upTo: limit }) {
  // starting base and corrosponding number
  let power = 1 // any base to the power of 0 is 1
  while (power <= limit) {
    // yield the current power then move on to the next power
    yield power
    power *= base
  }
}

export function say(word) {
  // set up a variable to store all our words
  let sentence = []

  // nested function to handle chained arguments
  function inner(word) {
    // add the words to the sentance if there are any left
    if (word != undefined) {
      sentence.push(word)
      // check for more words
      return inner
    }
    // if there are no words left to add, return our whole sentence
    return sentence.join(" ")
  }

  // if we have at least one word, start adding words to the sentance
  if (word != undefined) {
    sentence.push(word)
    // check for more words
    return inner
  }

  // if there were no words just return an empty string
  return ""
}

export async function meaningfulLineCount(filePath) {
  // count the lines that have text and don't start with a #
  let lineCount = 0

  // open the file
  const file = await open(filePath, "r")

  // read the file content
  const data = await file.readFile({ encoding: "utf-8" })

  // Split the file content into lines and filter out empty lines becasue we can
  const lines = data.split("\n").filter((line) => line.trim())

  // count all the lines that don't start with a #
  lines.forEach((line) => {
    if (line.trim()[0] != "#") {
      lineCount++
    }
  })

  // ensure the file is closed if it was opened
  if (file) {
    await file.close()
  }

  // return the count of non-empty, non-# lines
  return lineCount
}

// Write your Quaternion class here
export class Quaternion {
  // setup and store our properties
  constructor(a, b, c, d) {
    Object.assign(this, { a, b, c, d })
    // don't allow people to change the properties directly
    Object.freeze(this)
  }

  // add coeficients as a property
  get coefficients() {
    return [this.a, this.b, this.c, this.d]
  }

  // add conjugate as a property
  get conjugate() {
    return new Quaternion(this.a, -this.b, -this.c, -this.d)
  }

  // lets us check if quaternions are equal
  equals(that) {
    // I am very funny
    return (
      this.a === that.a &&
      this.b === that.b &&
      this.c === that.c &&
      this.d === that.d
    )
  }

  // handles addition
  plus(that) {
    return new Quaternion(
      this.a + that.a,
      this.b + that.b,
      this.c + that.c,
      this.d + that.d
    )
  }

  // handles multiplication
  times(that) {
    const a =
      this.a * that.a - this.b * that.b - this.c * that.c - this.d * that.d
    const b =
      this.a * that.b + this.b * that.a + this.c * that.d - this.d * that.c
    const c =
      this.a * that.c - this.b * that.d + this.c * that.a + this.d * that.b
    const d =
      this.a * that.d + this.b * that.c - this.c * that.b + this.d * that.a
    return new Quaternion(a, b, c, d)
  }

  // Convert quaternion into a string in the format a+bi+cj+dk
  // Additional format stuff: Only display non-zero values. Don't print out value of one. Subtract instead of adding negative numbers. If all zeros, return "0"
  toString() {
    // Edge case: all zeros
    if (this.coefficients == [0, 0, 0, 0]) {
      return "0"
    }

    // store the string we will return
    let quatStr = ""

    // A - if there is a non-zero value, add it to the string
    if (this.a !== 0) {
      quatStr += `${this.a}`
    }

    // B - handle the "i" component
    if (this.b !== 0) {
      if (quatStr && this.b > 0) {
        quatStr += "+" // only the "+" needs to be added explicitly because the "-" is included in negative numbers
      }
      if (this.b === 1) {
        quatStr += "i"
      } else if (this.b === -1) {
        quatStr += "-i"
      } else {
        quatStr += `${this.b}i`
      }
    }

    // C - handle the "j" component
    if (this.c !== 0) {
      if (quatStr && this.c > 0) {
        quatStr += "+" // only the "+" needs to be added explicitly because the "-" is included in negative numbers
      }
      if (this.c === 1) {
        quatStr += "j"
      } else if (this.c === -1) {
        quatStr += "-j"
      } else {
        quatStr += `${this.c}j`
      }
    }

    // D - handle the "k" component
    if (this.d !== 0) {
      if (quatStr && this.d > 0) {
        quatStr += "+" // only the "+" needs to be added explicitly because the "-" is included in negative numbers
      }
      if (this.d === 1) {
        quatStr += "k"
      } else if (this.d === -1) {
        quatStr += "-k"
      } else {
        quatStr += `${this.d}k`
      }
    }

    // Edge case: all zeros
    if (quatStr == "") {
      return "0"
    }
    // Return the completed string
    return quatStr
  }
}
