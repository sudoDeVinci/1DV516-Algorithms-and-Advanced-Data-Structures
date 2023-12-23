# Assignment 02

## Problem 01 
The double-ended queue is defined in [Deque](src/Deque.java).
The queue uses a Linked List which is defined [here](src/LinkedList.java)

## Problem 02
The randomized queue is defined in [DequeRand](src/DequeRand.java).

## Probelm 03
The LCRS directory tree is defined in [Tree LCRS](src/TreeLCRS.java).
Left or right slashes ("/" or "\") can be used for paths, but please stick to one convention.

```shell
javac src/TreeLCRS.java
java src/TreeLCRS
```

## Problem 04
The BST implementation is defined in [BST](src/BST.java).
```shell
javac src/BST.java
java src/BST
```

## Problem 05
The Isomorphic test method is the static BSTIso.isIsomorhphic() method defined in [BSTIso](src/BSTIso.java).
Time Complexity: Time complexity is O(min(m,n)*2) or O(min(m,n)) where m and n are number of nodes in given trees.
```shell
javac src/BSTIso.java
java src/BSTIso
```

## Problem 06
The AVL Tree is defined in [AVLTree](src/AVLTree.java).
The Tree Comparison is defined in [TreeComp](src/AVLTree.java).
```shell
javac src/TreeComp.java
java src/TreeComp
```


## Problem 07
N/A

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