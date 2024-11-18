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
    if (s->elements == NULL) {
        return (stack_response){.code = out_of_memory, .stack = NULL};
    }

    return(stack_response){.code = success, .stack = s};
}

int size(const stack s) {
    return s->top;
}

bool is_empty(const stack s) {
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

    // Check if item length exceeds maximum size
    if (strlen(item) > MAX_ELEMENT_BYTE_SIZE) {
        return stack_element_too_large;
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

        // store updated memory and capacity
        s->elements = new_elements;
        s->capacity = new_capacity;
    }

    // allocate memory and add the new string
    s->elements[s->top] = strdup(item);
    if (s->elements[s->top] == NULL) {
        return out_of_memory;
    }

    s->top++;
    return success;
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
    // Nothing to destroy if the stack didn't have enough memory to be created in the first place
    if (*s == NULL) {
        return;
    }

    // Free each element in the stack
    for (int i = 0; i < (*s)->top; i++) {
        free((*s)->elements[i]);
    }

    // Free the elements pointer and stack pointer
    free((*s)->elements);
    free(*s);

    // Nullify the pointer to stop memory leaks
    *s = NULL;  
}
