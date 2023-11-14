# Assignment 01

## Compilation and Running
### Java 
In the top-most folder:

#### Problem 04
```shell
javac src/Unionfind.java
java src/Unionfind
```

#### Problem 07
```shell
javac src/Sum.java
java src/Sum
```

#### Problem 08
```shell
javac src/PercEstimator.java
java src/PercEstimator
```

### Python
The plotter script uses numpy and matplotlib. If you dont have these installed, you can do this via:
```shell
pip install -r requirements.txt
```
The plotter script can be run independently by passing args in the form:
```
python src/scripts/pyplot.py Graph path.extension [x1, x2, ... xn] [y1, y2, ... yn] "X-Label" "Y-label" "Title" [Plot Type 1, ... Plot Type n] [Label 1, ... Label n]

- The keyword used is OS dependant. I tried to accomodate this but I'm unsure if it works across all platforms. If the keyword is incorrect, you can change the Plotter.KEYWORD attribute.
```

## Test statment

python3 src/scripts/plotter.py graphs/uf/FS1_000_000_QuickFind.png [100000,125000,150000,175000] [578595.1,523364.3929,455945.3793,565627.0] 'Unions' 'Time(ns)' 'Quick Find_1_000_000 elements' Linear


python src/scripts/plotter.py graphs/uf/test.png [1,2,3,4,5] [2,4,6,8,10] TEST 'TEST Y' 'TEST SPACE' Linear

## Report

<embed src="report.pdf" type="application/pdf">