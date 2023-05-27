public class App {
    public static void main(String[] args) throws Exception {
        
        LinkedList<Circle> linkedList1 = new LinkedList<Circle>();
        linkedList1.addElement(new Circle());
        linkedList1.addElement(new Circle());
        linkedList1.addElement(new Circle());
        linkedList1.addElement(new Circle());
        linkedList1.addElement(new Circle());
        linkedList1.printAll();
        /* for (int i = 0; i < linkedList1.getSize(); i++){
            System.out.println(linkedList1.getElement(i));
        } */

        System.out.println("----------------");

        LinkedList<String> linkedList2 = new LinkedList<String>();
        linkedList2.addElement("1");
        linkedList2.addElement("2");
        linkedList2.addElement("3");
        linkedList2.addElement("4");
        linkedList2.addElement("5");
        //linkedList2.printAll();
        for (int i = 0; i < linkedList2.getSize(); i++){
            System.out.println(linkedList2.getElement(i));
        }

    }
}
