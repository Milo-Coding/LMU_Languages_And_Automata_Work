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


def first_then_lower_case(list_of_strings: list[str], predicate) -> Optional[str]:
    # I cannot define predicate as a function (predicate: function) because python gives an error stating that an unknown function cannot be called
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


# Write your say function here


# Write your line count function here


# Write your Quaternion class here
