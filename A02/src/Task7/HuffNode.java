package src.Task7;

public class HuffNode {
    public int frequency;
    public char value;
    public HuffNode left, right;

    public HuffNode(char data, int frequency) {
        this.value = data;
        this.frequency = frequency;
        this.left = this.right =  null;
    }

    public HuffNode(int frequency) {
        this.value = '\0';
        this.frequency = frequency;
        this.left = this.right =  null;
    }
}


