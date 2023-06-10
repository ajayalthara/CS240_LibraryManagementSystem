package dataStructures;

public class myHashMap<K, V> {
    //A node class in hashMap. Adding this as an extra class to avoid creation of extra files
    private class hashMapEntry<K, V> {
        private K key;
        private V value;
        private hashMapEntry<K, V> next;

        public hashMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() { // returns key
            return this.key;
        }

        public V getValue() { // returns value
            return this.value;
        }

        public void setValue(V value) { // sets value
            this.value = value;
        }

        @Override
        public String toString() {
            hashMapEntry<K,V> temp = this;
            StringBuilder sb = new StringBuilder();
            while (temp != null) {
                sb.append(temp.key + " -> " + temp.value + ",");
                temp = temp.next;
            }
            return sb.toString();
        }
    }
    // The initial size of hashMap is assigned
    private final int hashMapSize = 50;
    // hashTable is a list of k.v pairs of type hashMapEntry
    private hashMapEntry<K, V> hashTable[];
    // Constructor for the outer class which creates a hashTable with null entries and the size is equal to hashMapSize
    public myHashMap() {
        hashTable = new hashMapEntry[hashMapSize];
    }
    //Function to add value with key into HashMap
    public void put(K key, V value) {
        //Rotating hash
        int hash = key.hashCode() % hashMapSize;
        hash = Math.abs(hash);
        hashMapEntry<K, V> e = hashTable[hash];

        if (e == null) {
            hashTable[hash]= new hashMapEntry<K, V>(key, value);
        } else {
            // Traverses the hashTable and checks each key for equality
            // If a key with the particular hash value is found then its value is updated
            while (e.next != null) {
                if (e.getKey().equals(key)) {
                    e.setValue(value);
                    return;
                }
                e = e.next;
            }
            // The following if block executes when there is a single element ('next' is null)
            if (e.getKey().equals(key)) { // if key exists, override it
                e.setValue(value);
                return;
            }
            // When is this getting executed?
            e.next = new hashMapEntry<K, V>(key, value); // sets the next value (which is null originally)
        }
    }
    //Function to retrieve value with the given key from hashMap
    public V get(K key) {
        int hash = key.hashCode() % hashMapSize;
        hash = Math.abs(hash);
        hashMapEntry<K, V> e = hashTable[hash];

        if (e == null) {
            return null;
        }

        while (e != null) {
            if (e.getKey().equals(key)) { // if entry's key is equal to the key we are looking for
                return e.getValue(); // return the value associated with key
            }
            e = e.next; // otherwise look to next
        }
        return null; // key does not exist
    }
    public hashMapEntry<K, V> remove(K key) { // remove value & key
        int hash = key.hashCode() % hashMapSize;
        hash = Math.abs(hash);
        hashMapEntry<K, V> e = hashTable[hash];

        if(e == null) {
            return null;
        }

        if(e.getKey().equals(key)) { // if the key matches given key for the head
            hashTable[hash] = e.next; // the hashTable needs to point to next value, cutting out e
            e.next = null;
            return e;
        }

        hashMapEntry<K, V> prev = e;
        e = e.next;

        while(e != null) {
            if(e.getKey().equals(key)) {
                prev.next = e.next;
                e.next = null;
                return e;
            }
        }
        return null; // key couldn't be found
    }

    @Override // child class overrides base class
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashMapSize; i++) {
            if (hashTable[i] != null) {
                sb.append(i + " " + hashTable[i] + "\n");
            } else {
                sb.append(i + " " + "null" + "\n");
            }
        }

        return sb.toString();
    }

    public boolean containsKey(K key) {
        int hash = key.hashCode() % hashMapSize;
        hash = Math.abs(hash);
        hashMapEntry<K, V> e = hashTable[hash];

        if(e == null) {
            return false;
        }

        while(e != null) {
            if(e.getKey().equals(key)) { // if entry's key is equal to the key we are looking for
                return true;
            }
            e = e.next; // otherwise look to next
        }
        return false; // key does not exist
    }
}
