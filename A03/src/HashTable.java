package src;

public class HashTable<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<K, V>[] table;
    private int size;

    public HashTable() {
        this.table = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
        this.size = 0;
    }

    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    public void insert(K key, V value) {
        ensureCapacity();
        int index = findEmptySlot(key);
        table[index] = new Entry<>(key, value);
        size++;
    }

    public V find(K key) {
        int index = findIndex(key);
        return (index != -1) ? table[index].getValue() : null;
    }

    public void delete(K key) {
        int index = findIndex(key);
        if (index != -1) {
            table[index] = null;
            size--;
        }
    }

    private int findIndex(K key) {
        int hash = hash(key);
        int index = hash % table.length;
        int quadratic = 1;

        while (table[index] != null && !table[index].getKey().equals(key)) {
            index = (index + quadratic * quadratic) % table.length;
            quadratic++;
        }

        return (table[index] != null && table[index].getKey().equals(key)) ? index : -1;
    }

    private int findEmptySlot(K key) {
        int hash = hash(key);
        int index = hash % table.length;
        int quadratic = 1;

        while (table[index] != null) {
            index = (index + quadratic * quadratic) % table.length;
            quadratic++;
        }

        return index;
    }

    private int hash(K key) {
        int hash = 0;
        for (char c : key.toString().toCharArray()) {
            hash += c;
        }
        return hash;
    }

    private void ensureCapacity() {
        if ((double) size / table.length > LOAD_FACTOR) {
            Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[table.length * 2];
            System.arraycopy(table, 0, newTable, 0, table.length);
            table = newTable;
        }
    }

    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new HashTable<>();

        hashTable.insert("one", 1);
        hashTable.insert("two", 2);
        hashTable.insert("three", 3);

        System.out.println("Value for key 'one': " + hashTable.find("one")); // 1
        System.out.println("Value for key 'four': " + hashTable.find("four")); // null

        hashTable.delete("two");
        System.out.println("Value for key 'two' after deletion: " + hashTable.find("two")); // null
    }
}
