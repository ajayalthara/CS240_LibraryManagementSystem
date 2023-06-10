package dataStructures;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class myLinkedListTest {

    @org.junit.jupiter.api.Test
    void clear() {
        myLinkedList junitLLTest = new myLinkedList<>();
        junitLLTest.addNode("Unit test data 1");
        junitLLTest.addNode("Unit test data 2");
        junitLLTest.addNode("Unit test data 3");
        junitLLTest.addNode("Unit test data 4");
        //Clear the list and see if it's empty
        junitLLTest.clear();
        assertTrue(junitLLTest.isListEmpty());
    }

    @org.junit.jupiter.api.Test
    void sizeOfList() {
        myLinkedList junitLLTest = new myLinkedList<>();
        junitLLTest.addNode("Unit test data 1");
        junitLLTest.addNode("Unit test data 2");
        junitLLTest.addNode("Unit test data 3");
        junitLLTest.addNode("Unit test data 4");
        //Adding 4 elements and verifying the size is equal to 4
        assertEquals(4,junitLLTest.sizeOfList());
    }

    @org.junit.jupiter.api.Test
    void isListEmpty() {
        myLinkedList junitLLTest = new myLinkedList<>();
        junitLLTest.addNode("Unit test data 1");
        junitLLTest.addNode("Unit test data 2");
        junitLLTest.addNode("Unit test data 3");
        junitLLTest.addNode("Unit test data 4");

        /*First assert statement should be 'False'.
        In the second step the LL is cleared and evaluated id the method returns 'True'*/
        assertFalse(junitLLTest.isListEmpty());
        junitLLTest.clear();
        assertTrue(junitLLTest.isListEmpty());
    }

    @org.junit.jupiter.api.Test
    void addNode() {
        myLinkedList junitLLTest = new myLinkedList<>();
        //Checks if the list is empty and size=0 before node addition
        assertEquals(0,junitLLTest.sizeOfList());
        assertTrue(junitLLTest.isListEmpty());

        junitLLTest.addNode("Unit test data 1");
        junitLLTest.addNode("Unit test data 2");

        //Adds 2 elements and checked if the size is equal to 2 and list not empty
        assertEquals(2,junitLLTest.sizeOfList());
        assertFalse(junitLLTest.isListEmpty());
    }

    @org.junit.jupiter.api.Test
    void getNodeDataByIndex() {
        myLinkedList junitLLTest = new myLinkedList<>();
        junitLLTest.addNode("Unit test data 1");
        junitLLTest.addNode("Unit test data 2");
        junitLLTest.addNode("Unit test data 3");
        junitLLTest.addNode("Unit test data 4");

        //Equality test for string at index 1
        assertEquals("Unit test data 2", junitLLTest.getNodeDataByIndex(1) );
        /*Using the lambda function as input to assertThrows so that it can evaluate can catch the exception whenever it's ready (lazy evalution)*/
        Throwable exception = assertThrows(IllegalArgumentException.class,() -> junitLLTest.getNodeDataByIndex(4) );
        assertEquals("Index value is larger than the list capacity",exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    void getAllNodeDataInArray() {
        myLinkedList junitLLTest = new myLinkedList<>();
        junitLLTest.addNode("data1");
        junitLLTest.addNode("data2");

        String[] expectedArray = {"data1", "data2"};
        /*Can not use the arrayList to do jUnit test since there is no assertArrayListEquals method.
        Hence the return from the method (ArrayList) is converted before use in the below assert statement*/
        assertArrayEquals(expectedArray,junitLLTest.getAllNodeDataInArray().toArray());
        //Checking if the method throws the IllegalStateException if used on empty list
        junitLLTest.clear();
        Throwable exception = assertThrows(IllegalStateException.class,() -> junitLLTest.getAllNodeDataInArray());
        assertEquals("List is empty",exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    void removeNodeByIndex() {
        myLinkedList junitLLTest = new myLinkedList<>();
        junitLLTest.addNode("Unit test data 1");
        junitLLTest.addNode("Unit test data 2");
        junitLLTest.addNode("Unit test data 3");
        junitLLTest.addNode("Unit test data 4");
        //Checking the size before removal
        assertEquals(4,junitLLTest.sizeOfList());
        String returnVal = (String) junitLLTest.removeNodeByIndex(2);
        //Checking the size, elements at and before the index 2
        assertEquals("Unit test data 3",returnVal);
        assertEquals(3,junitLLTest.sizeOfList());
        assertEquals("Unit test data 4",junitLLTest.getNodeDataByIndex(2));
        assertEquals("Unit test data 2",junitLLTest.getNodeDataByIndex(1));
        //Check removal of element at index 0
        returnVal = (String) junitLLTest.removeNodeByIndex(0);
        assertEquals("Unit test data 1",returnVal);
        assertEquals(2,junitLLTest.sizeOfList());
        assertEquals("Unit test data 4",junitLLTest.getNodeDataByIndex(1));
        assertEquals("Unit test data 2",junitLLTest.getNodeDataByIndex(0));
        //Checks for the exception when index value given is larger than the size
        Throwable exception = assertThrows(IllegalArgumentException.class,() -> junitLLTest.removeNodeByIndex(3));
        assertEquals("Index value is larger than the list capacity",exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    void addAtStart() {
        myLinkedList junitLLTest = new myLinkedList<>();
        junitLLTest.addNode("Unit test data 1");
        junitLLTest.addNode("Unit test data 2");
        junitLLTest.addNode("Unit test data 3");
        junitLLTest.addNode("Unit test data 4");
        /*Checks size before adding data. Checks size after adding data and also the element at index 0 */
        assertEquals(4,junitLLTest.sizeOfList());
        junitLLTest.addAtStart("Data added at start");
        assertEquals(5,junitLLTest.sizeOfList());
        assertEquals("Data added at start",junitLLTest.getNodeDataByIndex(0));
    }

    @org.junit.jupiter.api.Test
    void addAtIndex() {
        myLinkedList junitLLTest = new myLinkedList<>();
        junitLLTest.addNode("Unit test data 1");
        junitLLTest.addNode("Unit test data 2");
        junitLLTest.addNode("Unit test data 3");
        junitLLTest.addNode("Unit test data 4");
        /*Adds data in the middle, end, beginning of the list and checks for the expected size and structure */
        assertEquals(4,junitLLTest.sizeOfList());
        junitLLTest.addAtIndex(2,"New data at 2");
        assertEquals(5,junitLLTest.sizeOfList());
        assertEquals("New data at 2",junitLLTest.getNodeDataByIndex(2));
        assertEquals("Unit test data 3",junitLLTest.getNodeDataByIndex(3));
        assertEquals("Unit test data 4",junitLLTest.getNodeDataByIndex(4));
        //Adding at the beginning
        junitLLTest.addAtIndex(0,"New data at 0");
        assertEquals(6,junitLLTest.sizeOfList());
        assertEquals("New data at 0",junitLLTest.getNodeDataByIndex(0));
        assertEquals("Unit test data 4",junitLLTest.getNodeDataByIndex(5));
        //Adding in the end
        junitLLTest.addAtIndex(6,"New data at end");
        assertEquals(7,junitLLTest.sizeOfList());
        assertEquals("New data at end",junitLLTest.getNodeDataByIndex(6));
        assertEquals("Unit test data 4",junitLLTest.getNodeDataByIndex(5));
        //Checks if the data is added to the end if the index value given is higher than size
        junitLLTest.addAtIndex(10,"Dummy data at index 10");
        assertEquals(8,junitLLTest.sizeOfList());
        assertEquals("Dummy data at index 10",junitLLTest.getNodeDataByIndex(7));
    }
}