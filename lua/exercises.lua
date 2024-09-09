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
    local power = 1
    -- yield each power until we reach our limit
    while (power <= up_to) do
      coroutine.yield(power)
      -- increase our current power by one
      power = power * of_base
    end
  end)
end

function say(word)
  -- check for edge case no value passed
  if word == nil then
    return ""
  end
  -- handle chained arguments
  return function(next_word)
    -- end sentance when we reach an empty argument
    if next_word == nil then
      return word
    else
      return say(word .. " " .. next_word)
    end
  end
end

function meaningful_line_count(file_path)
  -- store the meaningful lines
  local line_count = 0

  -- find our file
  local file = io.open(file_path, 'r')

  -- if no such file exists, raise an error
  if file == nil then
    error("No such file")
  end

  -- count the number of meaningul lines
  for line in file:lines() do
    line = line:gsub("%s+", "")
    if line ~= "" and string.sub(line, 1, 1) ~= "#" then
      line_count = line_count + 1
    end
  end

  -- close our file
  file:close()

  -- return the number of meaninful lines
  return good_lines
end

Quaternion = (function(class)
  -- this is the init stuff that accepts arguments
  class.new = function (a, b, c, d)
    -- this is where the arguments are assigned to values
    return setmetatable({a = a, b = b, c = c, d = d}, {

      -- here additional properties can be assigned
      __index = {
        -- setup coefficient property
        coefficients = function(self)
          return ({self.a, self.b, self.c, self.d})
        end,
        -- setup conjugate property
        conjugate = function(self)
          return class.new(self.a, -self.b, -self.c, -self.d)
        end
      },

      -- handles check equals
      __eq = function(self, other)
        return self.a == other.a and self.b == other.b and self.c == other.c and self.d == other.d
      end,

      -- handles additon
      __add = function(self, other)
        return class.new(self.a + other.a, self.b + other.b, self.c + other.c, self.d + other.d)
      end,

      -- handles multiplication
      __mul = function(self, other)
        local new_a = self.a * other.a - self.b * other.b - self.c * other.c - self.d * other.d
        local new_b = self.a * other.b + self.b * other.a + self.c * other.d - self.d * other.c
        local new_c = self.a * other.c - self.b * other.d + self.c * other.a + self.d * other.b
        local new_d = self.a * other.d + self.b * other.c - self.c * other.b + self.d * other.a
        return class.new(new_a, new_b, new_c, new_d)
      end,

      -- handles printing out the Quaternion as a string
      __tostring = function(self)
        -- edge case: all zeros
        if self == Quaternion.new(0, 0, 0, 0) then
          return "0"
        end

        -- Track all used variables in a string to return
        local quat_str = ""

        -- A - if there is a non-zero value add it to the string
        if self.a ~= 0 then
          quat_str = quat_str .. tostring(self.a)
        end

        -- B - if there is a value of one, add "i" to the string, otherwise, add any non-zero value with "i" and the appropriate sign
        if self.b ~= 0 then
          if quat_str ~= "" and self.b > 0 then
            quat_str = quat_str .. "+"
          end
          if self.b == 1 then
            quat_str = quat_str .. "i"
          elseif self.b == -1 then
            quat_str = quat_str .. "-i"
          else
            quat_str = quat_str .. tostring(self.b) .. "i"
          end
        end

        -- C - if there is a value of one, add "j" to the string, otherwise, add any non-zero value with "j" and the appropriate sign
        if self.c ~= 0 then
          if quat_str ~= "" and self.c > 0 then
            quat_str = quat_str .. "+"
          end
          if self.c == 1 then
            quat_str = quat_str .. "j"
          elseif self.c == -1 then
            quat_str = quat_str .. "-j"
          else
            quat_str = quat_str .. tostring(self.c) .. "j"
          end
        end

        -- D - if there is a value of one, add "k" to the string, otherwise, add any non-zero value with "k" and the appropriate sign
        if self.d ~= 0 then
          if quat_str ~= "" and self.d > 0 then
            quat_str = quat_str .. "+"
          end
          if self.d == 1 then
            quat_str = quat_str .. "k"
          elseif self.d == -1 then
            quat_str = quat_str .. "-k"
          else
            quat_str = quat_str .. tostring(self.d) .. "k"
          end
        end

        -- return the completed text
        return quat_str
      end
      })
    end
  return class
end) ({})