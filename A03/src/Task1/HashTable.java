package Task1;

public class HashTable<K, V> {

    private int size;
    private static final int P = 670244987;
    private Element<K, V>[] table;
    private int collisions = 0;
    private int offset = 0;

    private static class Element<K, V> {
        final K key;
        V value;
        boolean dead;

        public Element(K key, V value) {
            this.key = key;
            this.value = value;
            this.dead = false;
        }
    }

    public HashTable(int capacity) {
        if (capacity < 2) throw new IllegalArgumentException("Capacity cannot be less than 2.");
        this.size = 0;
        this.table = new Element[capacity * 2];
    }

    public HashTable() {
        this.table = new Element[P];
        this.size = 0;
    }

    private int hash(K k) {
        int hash = k.hashCode();
        return hash < 0 ? (hash % table.length) * 1 : (hash % table.length);
    }

    private int getPos(K key) {
        int index = hash(key);
        if (this.table[index] != null) {
            int i = 1;
            while (table[index] != null) {
                if (table[index].key.equals(key) && !table[index].dead) return index;
                index = (index + i*i) % table.length;
                i++;
            }
        }

        return -1;
    }

    public boolean contains(K key) {
        return getPos(key)!= -1;
    }

    public V get(K key) throws RuntimeException {
        int index = getPos(key);
        if (index != -1) return table[index].value;
        else throw new RuntimeException("Key not found.");
    }
    
    public void remove(K key) {
        int index = getPos(key);
        if (index != -1) {
            table[index].dead = true;
            size--;
        } else throw new RuntimeException("Key not found.");
    }
       
    public int size() {
        return size;
    }

    public int conflicts() {
        return collisions;
    }

    public int offset() {
        return offset;
    }
    
    public void insert(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");
        int index = getPos(key);
        if (index != -1) table[index].value = value;
        else {
            index = hash(key);
            if (table[index]!= null) {
                collisions++;
                int probeNum = 1;
                while (table[index]!= null && !table[index].dead) {
                    index = (index + probeNum*probeNum) % table.length;
                    probeNum++;
                    offset++;
                }
            }
            table[index] = new Element<>(key, value);
            size++;
        }
    }

    public K elementAtIndex(int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds.");
        if (table[index]== null || table[index].dead) throw new RuntimeException("Element not found.");
        return table[index].key;
    }
}