# Assignment 01

## Compilation and Running
### Java 
In the top-most folder:

#### Problem 01
Viewable in [QuickFind](src/uf/QuickFind.java)

#### Problem 02
Viewable in [Weighted Union Find](src/uf/WeightedUnionFind.java)

#### Problem 03
Viewable in [Timeit](src/Timeit.java)

#### Problem 04
```shell
javac src/Unionfind.java
java src/Unionfind
```

#### Problem 05
Viewable in [Threesum](src/ksum/ThreeSum.java)

#### Problem 06
Viewable in [Threesum Cached](src/ksum/ThreeSumCached.java)

#### Problem 07
```shell
javac src/Sum.java
java src/Sum
```

#### Problem 08
Viewable in [PercEstimator.java](src/PercEstimator.java)
```shell
javac src/PercEstimator.java
java src/PercEstimator
```
tThreshold Results Viewable in [runs.csv](src/graphs/perc/runs.csv)

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