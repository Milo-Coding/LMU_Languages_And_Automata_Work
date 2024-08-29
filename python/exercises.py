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
    # Our starting power is zero
    current_power = 0
    # Any number to the power of zero is one so our starting number is one
    current_num = 1
    # Keep iterating until we reach our limit
    while current_num <= limit:
        # yield returns a value then pauses until the function is called again
        yield current_num
        # Once the code is called again we pick up here and increment our power, increasing the current number
        current_power += 1
        current_num = base ** current_power
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
    good_lines = 0
    # Start checking lines in the file one at a time. The encoding part is needed to recognize emojis as defined characters
    with open(my_file, 'r', encoding='utf-8') as file:
        for line in file:
            # Check if the line has non whitespace characters and doesn't start with a hashtag
            if line.strip() is not "" and line.strip()[0] is not "#":
                good_lines += 1
    return good_lines


# Write your Quaternion class here
