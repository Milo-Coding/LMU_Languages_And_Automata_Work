module Exercises
  ( change,
    firstThenApply,
    powers,
    meaningfulLineCount,
    Shape (Box, Sphere),
    volume,
    surfaceArea,
    BST (Empty),
    size,
    insert,
    contains,
    inorder,
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

firstThenApply :: [a] -> (a -> Bool) -> (a -> b) -> Maybe b
firstThenApply xs pred f = fmap f (find pred xs)

powers :: Integral a => a -> [a]
powers base = map (base ^) [0 ..]

meaningfulLineCount :: FilePath -> IO Int
meaningfulLineCount path = do
  contents <- readFile path
  return $ length $ filter meaningfulLine $ lines contents
  where
    meaningfulLine line =
      let trimmedLine = dropWhile isSpace line
       in not (null trimmedLine) && not ("#" `isPrefixOf` trimmedLine)

data Shape
  = Sphere Double
  | Box Double Double Double
  deriving (Eq, Show)

volume :: Shape -> Double
volume (Sphere r) = (4 / 3) * pi * r ^ 3
volume (Box l w h) = l * w * h

surfaceArea :: Shape -> Double
surfaceArea (Sphere r) = 4 * pi * r ^ 2
surfaceArea (Box l w h) = 2 * (w * l + w * h + l * h)

data BST a
  = Empty
  | Node a (BST a) (BST a)

size :: BST a -> Int
size Empty = 0
size (Node _ left right) = 1 + size left + size right

inorder :: BST a -> [a]
inorder Empty = []
inorder (Node value left right) = inorder left ++ [value] ++ inorder right

insert :: (Ord a) => a -> BST a -> BST a
insert value Empty = Node value Empty Empty
insert value (Node nodeValue left right)
  | value < nodeValue = Node nodeValue (insert value left) right
  | value > nodeValue = Node nodeValue left (insert value right)
  | otherwise = Node nodeValue left right

contains :: Eq a => a -> BST a -> Bool
contains _ Empty = False
contains x (Node value left right) = x == value || contains x left || contains x right

instance (Show a) => Show (BST a) where
  show :: (Show a) => BST a -> String
  show Empty = "()"
  show tree = showNode tree
    where
      showNode :: (Show a) => BST a -> String
      showNode Empty = ""
      showNode (Node value left right) = "(" ++ showNode left ++ show value ++ showNode right ++ ")"
