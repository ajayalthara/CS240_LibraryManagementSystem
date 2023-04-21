package dataStructures;

import java.util.ArrayList;

//Generic class that can take any type of valid Java class as input
public class myLinkedList<T> {
    //Class variables
    private int size;
    private listNode<T> head;

    //Constructor for the class
    public myLinkedList() {
        this.size = 0;
        this.head = null;
    }

    // Inner private class that defines the individual list node
    private class listNode<T> {
        //Data and the next pointer variables
        private T data;
        private listNode<T> next;
        //Constructor for the class
        public listNode(T data) {
            this.data = data;
            this.next = null;
        }
        //Getter and setter
        public T getData() {
            return data;
        }
        public void setData(T data) {
            this.data = data;
        }
        public listNode<T> getNext() {
            return next;
        }
        public void setNext(listNode<T> next) {

            this.next = next;
        }
    }
    //Clearing all the data in the LL
    public void clear(){
        size=0;
        head=null;
    }
    //To get the size of a LL
    public int sizeOfList(){
        return this.size;
    }
    //To check if the LL is empty
    public boolean isListEmpty(){
        return sizeOfList() == 0;
    }
    //Adding a node in the end
    public void addNode(T data){
        listNode<T> newNode = new listNode<>(data);
        newNode.data = data;
        newNode.next = null;
        // if the head of list is null, set the head of list as node
        if (head == null) {
            head = newNode;
        } else {
            listNode<T> currentNode = head;  // start at the head of the list
            while (currentNode.getNext() != null) { // keeps jumping until it finds end node
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(newNode); // sets end node value as the new node
        }
        size++;
    }

    public T getNodeDataByIndex (int index) throws IllegalArgumentException{
       if (index >= this.size) {
            throw new IllegalArgumentException("Index value is larger than the list capacity");
        }
        listNode<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        return (T) currentNode.getData();
    }

    public ArrayList<T> getAllNodeDataInArray() throws IllegalStateException {
        if (this.isListEmpty()) {
            throw new IllegalStateException("List is empty");
        } else {
            listNode<T> currentNode = head;
            ArrayList<T> resultArray = new ArrayList<>();
            for (int i=0; i<this.sizeOfList(); i++)
            {
                //System.out.println(currentNode.getData());
                resultArray.add(currentNode.getData());
                currentNode=currentNode.getNext();
            }
            return resultArray;
        }
    }

    public T removeNodeByIndex(int index) throws IllegalArgumentException{
            if (index >= this.size) {
                throw new IllegalArgumentException("Index value is larger than the list capacity");
            }
            if (index == 0) {
                listNode<T> currentNode = head;
                head = currentNode.next;
                size--;
                return currentNode.getData();
            } else {
                listNode<T> currentNode = head;
                listNode<T> nextNode = null;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.getNext();
                }
                nextNode = currentNode.getNext();
                currentNode.setNext(nextNode.next);
                size--;
                return nextNode.getData();
            }
    }

    public void addAtStart(T data) {
        listNode<T> newNode = new listNode<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public void addAtIndex(int index, T data) {
        try {
            if (index > this.size) {
                throw new IllegalArgumentException("Index given is larger than current list size. Adding node to the end");
            }
            listNode<T> newNode = new listNode<>(data);
            // If new node is added at start or node is empty
            if (index == 0 || head == null) {
                addAtStart(data);
            } else {
                listNode<T> currentNode = head;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.next;
                }
                newNode.next = currentNode.next;
                currentNode.next = newNode;
                size++;
            }
        }
        catch (IllegalArgumentException e){
            this.addNode(data);
            System.out.println(e.getMessage());
        }
    }
}