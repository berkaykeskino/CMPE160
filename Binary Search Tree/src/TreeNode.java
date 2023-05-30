public class TreeNode <E> {

    private E value;
    private TreeNode<E> left;
    private TreeNode<E> right;

    public TreeNode(E value){
        this.value = value;
    }
    public E getValue(){
        return this.value;
    }
    public TreeNode<E> getLeft(){
        return this.left;
    }
    
    public TreeNode<E> getRight(){
        return this.right;
    }
    public void setLeft(TreeNode<E> node){
        this.left = node;
    }
    public void setRight(TreeNode<E> node){
        this.right = node;
    }
    public void setValue(E value){
        this.value = value;
    }
}
