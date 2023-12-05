I have a task which says:
"Implement one of the “balanced” trees discussed (AVL, splay, or red-black) and compare it to a
binary search tree without effort to balance it. You should devise a reasonable way to create re-
alistic average case trees (e.g., combinations of inserts and deletes) and then compare in terms of
operation cost/time, height, etc. Describe and reflect upon your experiment and findings in the
report."

To create this, I have a few things made. I have created an AVLNode:

```Java
public class AVLNode<T extends Comparable<T>> extends LRNode<T,AVLNode<T>>{
    public int height;

    public AVLNode(T data) {
        this.height = 0;
        this.value = data;
        this.left = null;
        this.right = null;
    }
}
```


and BST Node:

```Java
public class BSTNode<T extends Comparable<T>> extends LRNode<T, BSTNode<T>>{
    BSTNode(T data) {
      this.value = data;
      this.left = null;
      this.right = null;
    }
}
```

which both extend a common class. Then I've ceated a common BST class for my trees to follow the structure of:

```Java
public abstract class BST<T extends Comparable<T>, U extends LRNode<T, U>> {

    private U root;
    
    public abstract void add(T key);
    public abstract void remove(T key);
    public abstract int height();

    public abstract void printTree();

    public abstract void printTree(U n);
}
```

To be able to time things, I have the Timeit class I've created which takes a method with args and
returns the time to run it:

```Java
/**
 * Pass in a method whioch takes in args to be timed.
 * Then, call the method corresponding to the time scale wanted.
 */
public class Timeit {
    private long start;
    private long stop;
    private Consumer<Object[]> timedMethod;

    /**
     * Take in the method to be measured.s
     * @param m
     */
    public Timeit(Consumer<Object[]> m) {
        this.timedMethod = m;
    }

    /**
     * Measure the execution of the given method in nanoseconds.
     * @param args
     * @return
     */
    public double measureNanos(Object...args) {
        this.start = System.nanoTime();
        timedMethod.accept(args);
        this.stop = System.nanoTime();
        double elapsed = (this.stop - this.start);
        return elapsed;
    }

    /**
     * Measure the execution of the given method in microseconds.
     * @param args
     * @return
     */
    public double measureMicros(Object...args) {
        double elapsed =  measureNanos(args);
        return elapsed/1e3;
    }

    /**
     * Measure the execution of the given method in miliseconds.
     * @param args
     * @return
     */
    public double measureMilis(Object...args) {
        double elapsed =  measureNanos(args);
        return elapsed/1e6;
    }

    /**
     * Measure the execution of the given method in seconds.
     * @param args
     * @return
     */
    public double measureSecs(Object...args) {
        double elapsed =  measureNanos(args);
        return elapsed/1e9;
    }
}
```

The AVLTree and BSTtree classes which hold the implementations of my tree logic are created and extend the BST class. Please help solve the Task. I don't need explanations, I need code for the task.
1. devise a reasonable way to create re-
alistic average case trees (e.g., combinations of inserts and deletes).
2. compare in terms of
operation cost/time, height, etc.

also, you Can't use List, ArrayList or collections.
Skip in outlining the avltree and bsttree classes, I DON'T need you to repeat their logic to me.