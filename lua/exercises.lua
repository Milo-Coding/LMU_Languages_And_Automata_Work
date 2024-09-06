function change(amount)
  if math.type(amount) ~= "integer" then
    error("Amount must be an integer")
  end
  if amount < 0 then
    error("Amount cannot be negative")
  end
  local counts, remaining = {}, amount
  for _, denomination in ipairs({25, 10, 5, 1}) do
    counts[denomination] = remaining // denomination
    remaining = remaining % denomination
  end
  return counts
end

function first_then_lower_case(list_of_strings, predicate)
  -- Check to make sure list isn't empty
  if list_of_strings == {} then
    -- if it is, return nil
    return nil
  end
  -- Check each string in the list of strings if it can be looped through 
  for _, my_string in ipairs(list_of_strings) do
    -- If it (as a lowercase) satisfies the predicate, return that string
    if (predicate(string.lower(my_string))) then
      return string.lower(my_string)
    end
  end
  -- If no strings satisfy the predicate, return nil
  return nil
end

function powers_generator(of_base, up_to)
  return coroutine.create(function ()
    -- we start at power of 0 which means our first number is 1
    local current_num = 1
    -- yield each power until we reach our limit
    while (current_num <= up_to) do
      coroutine.yield(current_num)
      -- increase our current power by one
      current_num = current_num * of_base
    end
  end)
end

-- Write your say function here
function say()

end

-- Write your line count function here
function meaningful_line_count()

end

-- Write your Quaternion table here
Quaternion = (function(class)

end) ({})