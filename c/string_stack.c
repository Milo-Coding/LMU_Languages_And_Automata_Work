#include "string_stack.h"
#include <stdlib.h>
#include <string.h>

#define INITIAL_CAPACITY 16

struct _Stack {
    char** elements;
    int top;
    int capacity;
};

stack_response create() {
    stack s = malloc(sizeof(struct _Stack));
    
    // make sure we have enough memory to make the stack
    if (s == NULL) {
        return (stack_response){.code = out_of_memory, .stack = NULL};
    }

    s->top = 0;
    s->capacity = INITIAL_CAPACITY;
    s->elements = malloc(INITIAL_CAPACITY * sizeof(char*));

    // check out of memory

    return(stack_response){.code = success, .stack = s};
}

int size(const stack s) {
    return s->top;
}

bool is_empty(const stack s) {
    // TODO
    return size(s) == 0;
}

bool is_full(const stack s) {
    return size(s) == MAX_CAPACITY;
}

response_code push(stack s, char* item) {
    // can't push if stack is full
    if (is_full(s)) {
        return stack_full;
    }
    // if we need to resize
    if (s->top == s->capacity) {
        // increase the capacity
        int new_capacity = s->capacity * 2;
        if (new_capacity > MAX_CAPACITY) {
            new_capacity = MAX_CAPACITY;
        }

        // make sure we have enough memory to add the new element
        char** new_elements = realloc(s->elements, new_capacity * sizeof(char*));
        if (new_elements == NULL) {
            return out_of_memory;
        }

        // make sure new string isn't too big
        // if (?)
        // return stack_element_too_large

        // store those values
        s->elements = new_elements;
        s->capacity = new_capacity;
        s->elements[s->top++] = strdup(item);
        return success;
    }
}

string_response pop(stack s) {
    // can't pop from an empty stack
    if (is_empty(s)) {
        return (string_response){.code = stack_empty, .string = NULL};
    }
    // get the last element
    char* popped = s->elements[--s->top];

    // if capacity went below 1/4, shrink the stack
    if (s->top < s->capacity / 4) {
        int new_capacity = s->capacity / 2;
        if (new_capacity < 1) {
            new_capacity = 1;
        }
        
        // make sure we have enough memory
        char** new_elements = realloc(s->elements, new_capacity * sizeof(char*));
        if (new_elements == NULL) {
            return (string_response){.code = out_of_memory, .string = NULL};
        }

        // update the values
        s->elements = new_elements;
        s->capacity = new_capacity;
    }

    // return the popped string
    return (string_response){.code = success, .string = popped};
}

void destroy(stack* s) {
    // TODO
}
