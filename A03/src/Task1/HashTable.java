package Task1;

public class HashTable<K> {
    private int size;
    private static final int P = 670244987;
    private Entry<K>[] table;

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        this.size = 0;
        this.table = new Entry[capacity];
    }

    @SuppressWarnings("unchecked")
    public HashTable() {
        this.size = 0;
        this.table = new Entry[P];
    }

    /**
     * Class to represent entry in HashTable.
     */
    private static class Entry<K> {
        public boolean Active;
        public K key;

        public Entry(K key) {
            this(key, true);
        }

        public Entry(K k, boolean active) {
            this.Active = active;
            this.key = k;
        }
    }

    /**
     * Get the hash value for a given key.
     * @param key
     * @return
     */
    private int hash(K key) {
        int hash = key.hashCode();
        //System.out.println("Before reduction, hash is " + hash);
        while(hash < 0) hash = (table.length + hash) % table.length;
        hash = hash % table.length;
        //System.out.println("After reduction, hash is " + hash);
        return hash % table.length;
    }

    /**
     * Insert a value into the table.
     * @param key
     * @return
     */
    public void insert(K key) {
        int index = find(key);
        if(exists(index)) return;
        this.table[index] = new Entry<>(key, true);
        this.size += 1;
    }

    /**
     * Check if the table contains a given key.
     * @param key
     * @return
     */
    public boolean contains (K key) {
        int index = find(key);
        return exists(index);
    }

    /**
     * Delete the key from a table..
     * @param key
     */
    public void delete(K key) {
        int index = find(key);

        if(exists(index)) {
            this.table[index].Active = false;
            this.size -= 1;
        }
    }

    private boolean exists(int index) {
        return this.table[index] != null && this.table[index].Active;
    }

    /**
     * Find the end index for a given entry.
     * @param inkey
     * @return
     */
    private int find(K inkey) {
        int index = hash(inkey);
        int offset = 1;

        while (this.table[index] != null && !this.table[index].key.equals(inkey)) {
            index += offset;
            offset += 2;
            if( index >= table.length ) index -= this.table.length;
        }

        return index;
    }

    public void print() {
        System.out.print("[ ");
        for (Entry<K> item : this.table) if(item == null || item.Active == false) System.out.print("NULL" + " ");else System.out.print(item.key + " ");
        System.out.println("] ");
    }

    public static void main(String[] args) {
        HashTable<String> table = new HashTable<>(10);
        table.insert("apple");
        table.insert("cherry");

        /*
         * This should be true.
         */
        table.insert("banana");
        System.out.println("Contains 'banana': " + table.contains("banana"));

        /*
         * Visulaizing the table after adding elements.
         */
        table.print();
        System.out.println();

        /*
         * This should be false.
         */
        table.delete("banana");
        System.out.println("Contains 'banana' after deletion: " + table.contains("banana"));

        /*
         * Visulaizing the table after deleting element.
         */
        table.print();
        System.out.println();
    }

}
