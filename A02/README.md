# Assignment 02

## Problem 01 
The double-ended queue is defined in [Deque](src/Task1/Deque.java).
The queue uses a Linked List which is defined [here](src/Task1/LinkedList.java)

## Problem 02
The randomized queue is defined in [DequeRand](src/Task2/DequeRand.java).
Simple tests for it are defined in [Task2](src/Task2/Task2.java)

```shell
javac src/Task2/Task2.java
java src/Task2/Task2
```


## Probelm 03
The LCRS directory tree is defined in [Tree LCRS](src/Task3/TreeLCRS.java).
Simple example for testing is in [Task3](src/Task3/Task3.java)
Left or right slashes ("/" or "\") can be used for paths, but please stick to one convention.

```shell
javac src/Task3/Task3.java
java src/Task3/Task3
```

## Problem 04
The BST implementation is defined in [BST](src/Task4/BST.java).
Tests for it are defined in [Task4](src/Task4/Task4.java)

```shell
javac src/Task4/Task4.java
java src/Task4/Task4
```

## Problem 05
The Isomorphic test method is the static BSTIso.isIsomorhphic() method defined in [BSTIso](src/Task5/BSTIso.java).
Tests for different tree types are in [Task 5](src/Task5/Task5.java)
Time Complexity: Time complexity is O(min(m,n)*2) or O(min(m,n)) where m and n are number of nodes in given trees.

```shell
javac src/Task5/Task5.java
java src/Task5/Task5
```

## Problem 06
The AVL Tree is defined in [AVLTree](src/Task6/AVLTree.java).
The Tree Comparison is defined in [TreeComp](src/Task6/Task6.java).
Code used to 

```shell
javac src/TreeComp.java
java src/TreeComp
```


## Problem 07
The huffman tree is defined in [](src/Task7/Task7.java)

### Python
The plotter script uses numpy and matplotlib.

The plotter script can be run independently by passing args in the form:
```
python src/scripts/pyplot.py Graph path.extension [x1, x2, ... xn] [y1, y2, ... yn] "X-Label" "Y-label" "Title" [Plot Type 1, ... Plot Type n] [Label 1, ... Label n]

- The keyword used is OS dependant. I tried to accomodate this but I'm unsure if it works across all platforms. If the keyword is incorrect, you can change the Plotter.KEYWORD attribute.
```

## Test statement

python3 src/scripts/plotter.py graphs/uf/FS1_000_000_QuickFind.png [100000,125000,150000,175000] [578595.1,523364.3929,455945.3793,565627.0] 'Unions' 'Time(ns)' 'Quick Find_1_000_000 elements' Linear

python src/scripts/plotter.py graphs/uf/FS1_000_000_QuickFind.png [100000,125000,150000,175000] [578595.1,523364.3929,455945.3793,565627.0] 'Unions' 'Time(ns)' 'Quick Find_1_000_000 elements' Linear