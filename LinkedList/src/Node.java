public class Node <E> {
    //Value of the current item
    private E value;
    //Next item
    private Node<E> next;
    //Previous item
    private Node<E> previous;

    public Node(E value){
        this.value = value;
    }

    public void setNext(Node<E> next){
        this.next = next;
    }

    public void setPrevious(Node<E> previous){
        this.previous = previous;
    }

    public Node<E> getNext(){
        return this.next;
    }

    public Node<E> getPrevious(){
        return this.previous;
    }

    public E getValue(){
        return this.value;
    }

}
