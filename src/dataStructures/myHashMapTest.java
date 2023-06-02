package dataStructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class myHashMapTest {

    @Test
    void get() {
        myHashMap<String, String> mapTest = new myHashMap<>();
        mapTest.put("testKey", "testValue");
        // Testing to see if the hashMap will return the correct value
        assertEquals("testValue", mapTest.get("testKey"));
        assertNotEquals("testFail", mapTest.get("testKey"));
    }

    @Test
    void put() {
        myHashMap<String, String> mapTest = new myHashMap<>();
        mapTest.put("testKey", "testValue");
        // Testing to see if the HashMap contains the key and value
        assertTrue(mapTest.containsKey("testKey"));
        assertEquals("testValue", mapTest.get("testKey"));
    }

    @Test
    void remove() {
        myHashMap<String, String> mapTest = new myHashMap<>();
        mapTest.put("testKey", "testValue");
        mapTest.remove("testKey");
        // Testing to see if key and value are removed from HashMap
        assertFalse(mapTest.containsKey("testKey"));
        assertNotEquals("testValue", mapTest.get("testKey"));
    }

    @Test
    void containsKey() {
        myHashMap<String, String> mapTest = new myHashMap<>();
        mapTest.put("testKey", "testValue");
        // Checks if the key is present in HashMap
        assertTrue(mapTest.containsKey("testKey"));
        assertFalse(mapTest.containsKey("testFail"));
    }
}

