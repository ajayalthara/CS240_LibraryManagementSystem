public class MyLinkedList {
    private ListNode head;

    public void add(String data) {
        ListNode node = new ListNode();
        node.data = data;
        node.next = null;

        if (head == null) { // if the head of list is null, set the head of list as node
            head = node;
        } else {
            ListNode newNode = head;  // start newNode as the head
            while (newNode.next != null) { // keeps jumping until it finds end node
                newNode = newNode.next;
            }
            newNode.next = node; // sets end node value
        }
    }

    public void print() {
        ListNode node = head;
        int count = 1;
        while (node != null) {
            System.out.println(node.data);
            node = node.next;
        }
    }

    public ListNode search(int index) {
        ListNode node = head;
        int count = 1;
        for (int i = 0; i < index - 1; i++) {
            node = node.next;
            count++;
        }
        return node;
    }

    public void addAtStart(String data) {
        ListNode node = new ListNode();
        node.data = data;
        node.next = null;
        node.next = head;
        head = node;
    }

    public void addAt(int index, String data) {
        ListNode node = new ListNode();
        node.data = data;
        node.next = null;
        if (index == 0 || head == null) { // if they try to add at start or list is empty
            addAtStart(data);
        } else {
            ListNode newNode = head;
            for (int i = 0; i < index - 1; i++) {
                newNode = newNode.next;
            }
            node.next = newNode.next;
            newNode.next = node;
        }
    }

    public void deleteAt(int index) {
        if (index == 0) {
            index++;
        }
        if (index == 1) {
            head = head.next;
        } else {
            ListNode newNode = head;
            ListNode n = null;
            for (int i = 0; i < index -1; i++) {
                newNode = newNode.next;
            }
            n = newNode.next;
            newNode.next = n.next;
        }
    }
}
