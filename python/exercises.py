from dataclasses import dataclass
from collections.abc import Callable
from typing import *  # just to help me set up the functions more clearly


def change(amount: int) -> dict[int, int]:
    if not isinstance(amount, int):
        raise TypeError('Amount must be an integer')
    if amount < 0:
        raise ValueError('Amount cannot be negative')
    counts, remaining = {}, amount
    for denomination in (25, 10, 5, 1):
        counts[denomination], remaining = divmod(remaining, denomination)
    return counts


# I cannot define predicate as a function (even though mypy allows it) because python gives an error stating that an unknown function cannot be called
def first_then_lower_case(list_of_strings: list[str], predicate) -> Optional[str]:
    # Check each string in the list in order
    for current_string in list_of_strings:
        # If it satisfies the predicate, return that string
        if predicate(current_string):
            return current_string.lower()
    # If no strings satisfy the predicate return None
    return None


def powers_generator(base: int, limit: int) -> Generator:
    power = 1 # Any number to the power of zero (our starting power) is one so our starting number is one
    # Keep iterating until we reach our limit
    while power <= limit:
        # yield returns a value then pauses until the function is called again
        yield power
        # Once the code is called again we pick up here and increment our power, increasing the current number
        power = power * base
    # Once we reach the end of the function a StopIterating error is raised


# I cannot define the return type of these functions as strings becasue it confuses mypy too much when going through recursion
def say(word: Optional[str] = None):
    # Somewhere to store all the words in order
    sentence: list = []
    
    # A nested function to handle chained arguments
    def inner(word: Optional[str] = None):
        # When the last call is made without an argument, return the sentence as a string
        if word is None:
            return " ".join(sentence)
        # If it is not the last call, just add the word to the sentance list and move on to the next call
        sentence.append(word)
        return inner
    
    # If we have at least one word in our sentance, add it to our list and start the recursion
    if word is not None:
        sentence.append(word)
        return inner
    # Otherwise our sentance is just an empty string 
    return ""


# No mypy type exists for files
def meaningful_line_count(my_file) -> Optional[int]:
    # Somewhere to count meaningul lines
    line_count = 0
    # Start checking lines in the file one at a time. The encoding part is needed to recognize emojis as defined characters
    with open(my_file, 'r', encoding='utf-8') as file:
        for line in file:
            # Check if the line has non whitespace characters and doesn't start with a hashtag
            if line.strip() != "" and line.strip()[0] != "#":
                line_count += 1
    file.close()
    return line_count


class Quaternion:
    # Setup the constructor
    def __init__(self, a, b, c, d):
        # List the variables indevidually
        self.a = a
        self.b = b
        self.c = c
        self.d = d

    # Store our four variables together
    @property
    def coefficients(self) -> tuple:
        return (self.a, self.b, self.c, self.d)

    # Store our conjugate
    @property
    def conjugate(self) -> object:
        return Quaternion(self.a, -self.b, -self.c, -self.d)
    
    # Handle quaternion addition
    def __add__(self, other: object) -> object:
        if isinstance(other, Quaternion):
            return Quaternion(self.a + other.a, self.b + other.b, self.c + other.c, self.d + other.d)
        return None  # If the other value is not a quaternion, cannot do quaternion addition
        
    # Handle quaternion multiplication (equations from https://en.wikipedia.org/wiki/Quaternion)
    def __mul__(self, other: object) -> object:
        if isinstance(other, Quaternion):
            new_a = self.a * other.a - self.b * other.b - self.c * other.c - self.d * other.d
            new_b = self.a * other.b + self.b * other.a + self.c * other.d - self.d * other.c
            new_c = self.a * other.c - self.b * other.d + self.c * other.a + self.d * other.b
            new_d = self.a * other.d + self.b * other.c - self.c * other.b + self.d * other.a
            return Quaternion(new_a, new_b, new_c, new_d)
        return None  # If the other value is not a quaternion, cannot do quaternion multiplication
    
    # Handle checking for equivolance
    def __eq__(self, other: object) -> bool:
        # if the coefficents are equal than so are the quaternions
        if isinstance(self, Quaternion) and isinstance(other, Quaternion):
            return self.coefficients == other.coefficients
        return False
    
    # Convert quaternion into a string in the format a+bi+cj+dk
    # Additional format stuff: Only display non-zero values. Don't print out value of one. Subtract instead of adding negative numbers. If all zeros, return "0"
    def __str__(self) -> str:
        # edge case: all zeros
        if self.coefficients == (0, 0, 0, 0):
            return "0"
        
        # Track all used varables in a string to return
        result = ""
        
        # A - if there is a non-zero value add it to the string
        if self.a != 0:
            result += f"{self.a}"

        # B - if there is a value of one, add "i" to the string, otherwise, add any non-zero value with "i" and the appropriate sign
        if self.b != 0:
            if result and self.b > 0:
                result += "+"  # only the "+" needs to be added explicitly because the "-" is included in negative numbers
            if self.b == 1:
                result += "i"
            elif self.b == -1:
                result += "-i"
            else:
                result += f"{self.b}i"

        # C - if there is a value of one, add "j" to the string, otherwise, add any non-zero value with "j" and the appropriate sign
        if self.c != 0:
            if result and self.c > 0:
                result += "+"  # only the "+" needs to be added explicitly because the "-" is included in negative numbers
            if self.c == 1:
                result += "j"
            elif self.c == -1:
                result += "-j"
            else:
               result += f"{self.c}j"

        # D - if there is a value of one, add "k" to the string, otherwise, add any non-zero value with "k" and the appropriate sign
        if self.d != 0:
            if result and self.d > 0:
                result += "+"  # only the "+" needs to be added explicitly because the "-" is included in negative numbers
            if self.d == 1:
                result += "k"
            elif self.d == -1:
                result += "-k"
            else:
                result += f"{self.d}k"

        # return the compleated text
        return result