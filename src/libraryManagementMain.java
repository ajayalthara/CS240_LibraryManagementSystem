import dataStructures.*;
public class libraryManagementMain {
    public static void main(String[] args) {
        myLinkedList ajList = new myLinkedList();


        System.out.println("Debug:Hello there");
        System.out.println(ajList.isListEmpty());
        ajList.addNode("Ajay testing");
        ajList.addNode("Ajay testing2");
        ajList.addNode("Ajay testing3");
        ajList.addNode("Ajay testing4");

        //ajList.getAllNodeData(ajList);
        System.out.println(ajList.getAllNodeDataInArray());
//        System.out.println(ajList.isListEmpty());
//        System.out.println("Before remove");
//        System.out.println(ajList.sizeOfList());
//        System.out.println(ajList.getNodeDataByIndex(1));
//        ajList.removeNodeByIndex(1);
//        System.out.println("After remove");
//        System.out.println(ajList.sizeOfList());
//        //ajList.getAllNodeData(ajList);
//        System.out.println(ajList.getNodeDataByIndex(1));
//        //ajList.clear();
//        System.out.println(ajList.isListEmpty());


    }
}