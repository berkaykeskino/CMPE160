public class LinkedList <E> {
    //First element of the list
    private Node<E> head;
    //Last element of the list
    private Node<E> tail;
    //Number of elements in the list
    private int size = 0;

    public LinkedList(){

    }

    //Add new element to the end
    public void addElement(E value){
        Node<E> newItem = new Node<E>(value);
        if (size == 0){
            head = newItem;
            tail = newItem;
            size++;
            return;
        }
        //former tail's next is the new tail
        tail.setNext(newItem);
        //former tail is the previous of the new tail
        newItem.setPrevious(tail);
        //adjust tail
        tail = newItem;
        size++;
    }

    //get n'th element
    public E getElement(int index) throws IndexOutOfBoundsException{
        //if index out of bounds
        if (index > size || index < 0){
            System.out.println("Index out of bounds");
            throw new IndexOutOfBoundsException("Index out of bounds");
        }else if (index < (double) size / 2){ //if index is lower than the length / 2, start from the head
            int counter = 0;
            Node<E> currentElement = head;
            while (counter != index){
                currentElement = currentElement.getNext();
                counter++;
            }
            return currentElement.getValue();
        }else{//if index is higher than the length / 2, start from the tail
            int counter = size - 1;
            Node<E> currentElement = tail;
            while (counter != index){
                currentElement = currentElement.getPrevious();
                counter--;
            }
            return currentElement.getValue();
        }
        
    }

    public void printAll(){
        if (head == null){
            System.out.println("There are no elements in the list.");
            return;
        }
        Node<E> currentElement = head;
        while (currentElement.getNext() != null){
            System.out.println(currentElement.getValue());
            currentElement = currentElement.getNext();
        }
        //For the last element, the next is null
        //We need to write it seperately
        System.out.println(currentElement.getValue());
    }



}
