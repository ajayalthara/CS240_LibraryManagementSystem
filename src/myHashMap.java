public class myHashMap<K, V> {

    private class Entry<K, V> { // class within class so I don't have to have extra files
        private K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value) {
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
            Entry<K,V> temp = this;
            StringBuilder sb = new StringBuilder();
            while (temp != null) {
                sb.append(temp.key + " -> " + temp.value + ",");
                temp = temp.next;
            }
            return sb.toString();
        }
    }

    private final int SIZE = 5;
    private Entry<K, V> table[];

    public myHashMap() {
        table = new Entry[SIZE];
    }

    public void put(K key, V value) { // adds value with key into HashMap
        int hash = key.hashCode() % SIZE;
        Entry<K, V> e = table[hash];

        if (e == null) {
            table[hash]= new Entry<K, V>(key, value);
        } else {
            while (e.next != null) { // checks if next equals null
                if (e.getKey() == key) {
                    e.setValue(value);
                    return;
                }
                e = e.next;
            }

            if (e.getKey() == key) { // if key exists, override it
                e.setValue(value);
                return;
            }

            e.next = new Entry<K, V>(key, value); // sets the next value (which is null originally)
        }
    }


    public V get(K key) { // gets value associated with key
        int hash = key.hashCode() % SIZE;
        Entry<K, V> e = table[hash];

        if (e == null) {
            return null;
        }

        while (e != null) {
            if (e.getKey() == key) { // if entry's key is equal to the key we are looking for
                return e.getValue(); // return the value associated with key
            }
            e = e.next; // otherwise look to next
        }

        return null; // key does not exist
    }
    public Entry<K, V> remove(K key) { // remove value & key
        int hash = key.hashCode() % SIZE;
        Entry<K, V> e = table[hash];

        if(e == null) {
            return null;
        }

        if(e.getKey() == key) { // if the key matches given key for the head
            table[hash] = e.next; // the table needs to point to next value, cutting out e
            e.next = null;
            return e;
        }

        Entry<K, V> prev = e;
        e = e.next;

        while(e != null) {
            if(e.getKey() == key) {
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
        for (int i = 0; i < SIZE; i++) {
            if (table[i] != null) {
                sb.append(i + " " + table[i] + "\n");
            } else {
                sb.append(i + " " + "null" + "\n");
            }
        }

        return sb.toString();
    }

    public boolean containsKey(K key) {
        int hash = key.hashCode() % SIZE;
        Entry<K, V> e = table[hash];

        if(e == null) {
            return false;
        }

        while(e != null) {
            if(e.getKey() == key) { // if entry's key is equal to the key we are looking for
                return true;
            }
            e = e.next; // otherwise look to next
        }
        return false; // key does not exist
    }
}
