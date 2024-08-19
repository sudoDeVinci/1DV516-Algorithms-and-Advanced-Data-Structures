package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;


/**
 * 1. Hashmap of the frequencies of each letter and its frequency.
 *      1.1. Make this into a sorted de-queue of nodes.
 * 2. Remove the two least frequent nodes and find the sum of their frequencies.
 *      2.1. Add this back into the queue and repeat until the queue is empty. 
 * 3. Going left in the tree is 0, going right is 1.
 * 
 */

public class Huffman implements Iterable<HuffNode>{

    private HuffNode root;

    public void construct(Deque<HuffNode> queue) {
        if (queue.isEmpty()) return; 

        if (queue.size() == 1) this.root = queue.removeLast();


        HuffNode parent = null;
        while (queue.size() > 1) {
            HuffNode left = queue.removeLast();
            HuffNode right = queue.removeLast();

            parent = new HuffNode(left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;

            queue.addLast(parent);
        }

        this.root = parent;
    } 

    public static Map<Character, Integer> getCharacterFrequency(String filePath) {
        Map<Character, Integer> charFrequencyMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int currentChar;
            while ((currentChar = br.read()) != -1) {
                char character = (char) currentChar;

                // Skip non-alphabetic characters if needed
                if (Character.isLetter(character)) {
                    charFrequencyMap.put(character, charFrequencyMap.getOrDefault(character, 0) + 1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Use TreeMap with a custom comparator to sort by frequency (values) in descending order
        Map<Character, Integer> sortedCharFrequencyMap = new TreeMap<>(
            (o1, o2) -> charFrequencyMap.get(o2).compareTo(charFrequencyMap.get(o1))
        );
        sortedCharFrequencyMap.putAll(charFrequencyMap);

        return sortedCharFrequencyMap;
    }

     
    private class InOrderIterator implements Iterator<HuffNode> {
        private Deque<HuffNode> stack = new Deque<>();
    
        private InOrderIterator() {
            pushLeftChildren(root);
        }
    
        private void pushLeftChildren(HuffNode node) {
            while (node != null) {
                stack.addFirst(node);
                node = node.left;
            }
        }
    
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }
    
        @Override
        public HuffNode next() {
            HuffNode node = stack.removeFirst();
            pushLeftChildren(node.right);
            return node;
        }
    }

    public Iterator<HuffNode> iterator() throws NoSuchElementException {
        return new InOrderIterator();
    }
    
    
    public static void main(String[] args) {


        /**
         * This queue holds the tree nodes in descending order.
         */
        Deque<HuffNode> queue = new Deque<>();

        /**
         * Construct Map.
         */
        String path = "src/myFile.dat";
        Map<Character, Integer> chars = getCharacterFrequency(path);

        /**
         * Add to dequeue
         */
        chars.forEach((k, v) -> queue.addLast(new HuffNode(k.charValue(), v.intValue())));
        for(HuffNode node : queue) System.out.print(node.value + ": " + node.frequency + ", ");
        System.out.println("\n"); 

        /**
         * Construct Tree.
         */
        Huffman tree = new Huffman();
        tree.construct(queue);
        
        for (HuffNode node : tree) {
            System.out.print(node.value + ": " + node.frequency + ", ");
        }
        System.out.println("\n");
    }
}