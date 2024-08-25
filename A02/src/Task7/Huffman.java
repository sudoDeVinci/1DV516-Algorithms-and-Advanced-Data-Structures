package src.Task7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 1. Hashmap of the frequencies of each letter and its frequency.
 *      1.1. Make this into a sorted de-queue of nodes.
 * 2. Remove the two least frequent nodes and find the sum of their frequencies.
 *      2.1. Add this back into the queue and repeat until the queue is empty. 
 * 3. Going left in the tree is 0, going right is 1.
 * 
 */

public class Huffman {

    private HuffNode root;
    private String string;

    public Huffman(String string) {
        this.string  = string;
        this.root = construct();
    }

    public Huffman() {
        this.root = null;
        this.string = "";
    }

    public HuffNode root () {
        return this.root;
    }

    private HuffNode fromHashMap(HashMap<Character, Integer> freqmap) {

        PriorityQueue<HuffNode> freqQ = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));

        for (var entry : freqmap.entrySet()) freqQ.add(new HuffNode(entry.getKey(), entry.getValue()));
        
        while (freqQ.size() > 1) {
            HuffNode left = freqQ.poll();
            HuffNode right = freqQ.poll();
            HuffNode parent = new HuffNode(left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            freqQ.add(parent);
        }


        return freqQ.poll();
    }

    private HuffNode construct() {
        HashMap<Character, Integer> freqmap = new HashMap<>();
        for (char c : this.string.toCharArray()) freqmap.put(c, freqmap.getOrDefault(c, 0) + 1);
        return fromHashMap(freqmap);
    }

    public HuffNode fromFilePath(String filePath) {
        HashMap<Character, Integer> freqmap = new HashMap<>();
        StringBuilder out = new StringBuilder(); 

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int currentChar;
            while ((currentChar = br.read())!= -1) {
                char c = (char) currentChar;
                out.append(c);
                if(Character.isLetter(c)) freqmap.put(c, freqmap.getOrDefault(c, 0) + 1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        this.root = fromHashMap(freqmap);
        this.string = out.toString();
        return fromHashMap(freqmap);
    }

    private String getHuffmanString(HuffNode node, char target, String huffCode) {
        if (node != null) {
            if (node.value == target && node.left == null && node.right == null) {
                return huffCode;
            }

            String leftHuffCode= getHuffmanString(node.left, target, huffCode + '0');
            if (leftHuffCode!= null) return leftHuffCode;

            String rightHuffCode = getHuffmanString(node.right, target, huffCode + '1');
            if (rightHuffCode!= null) return rightHuffCode;
        }
        return null;
    }

    public String getHuffmanString(char target) {
        if (root == null) return null;
        return getHuffmanString(root, target, "");
    }


    
}