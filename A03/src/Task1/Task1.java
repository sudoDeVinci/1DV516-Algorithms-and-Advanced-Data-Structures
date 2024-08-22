package Task1;

public class Task1 {
    public static void main(String[] args) {
        
        // Basic operation test
        HashTable<String, Integer> ht = new HashTable<>(10);
        assert (ht != null) : "Hash table not instantiated";
        
        ht.insert("hello", 2);
        assert (ht.contains("hello")) : "Hello not found in the hash table";

        ht.remove("hello");
        assert (!ht.contains("hello")) : "Hello still found in the hash table after deletion";


        // test for adding duplicate items
        HashTable<Integer, Integer> intht = new HashTable<>(10);
        intht.insert(1, 1);
        intht.insert(1, 1);
        intht.insert(1, 1);
        intht.insert(1, 1);
        intht.insert(1, 2);
        assert (intht.contains(1)) : "1 not found in the hash table";
        assert (intht.size() == 1) : "Size of hash table not updated correctly";
    
        intht.insert(5, 2);
        intht.insert(5, 2);
        intht.insert(5, 3);
        assert (intht.contains(5)) : "5 not found in the hash table";
        assert (intht.size() == 2) : "Size of hash table not updated correctly";

    }
}
