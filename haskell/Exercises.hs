module Exercises
  ( change,
  -- put the proper exports here
  )
where

import Data.Char (isSpace)
import Data.List (find, isPrefixOf)
import Data.Map qualified as Map
import Data.Text (pack, replace, unpack)

change :: Integer -> Either String (Map.Map Integer Integer)
change amount
  | amount < 0 = Left "amount cannot be negative"
  | otherwise = Right $ changeHelper [25, 10, 5, 1] amount Map.empty
  where
    changeHelper [] remaining counts = counts
    changeHelper (d : ds) remaining counts =
      changeHelper ds newRemaining newCounts
      where
        (count, newRemaining) = remaining `divMod` d
        newCounts = Map.insert d count counts

-- Write your first then apply function here

-- Write your infinite powers generator here

-- Write your line count function here

-- Write your shape data type here

-- Write your binary search tree algebraic type here
