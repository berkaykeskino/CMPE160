public class App {
    public static void main(String[] args) throws Exception {
        //Create a BST
        BinarySearchTree<Integer> BST = new BinarySearchTree<>();
        BST.insert(20);
        BST.insert(10);
        BST.insert(30);
        BST.insert(32);
        BST.insert(33);
        BST.insert(34);
        BST.insert(35);
        BST.insert(22);
        BST.insert(23);
        BST.insert(21);
        BST.insert(15);

        //Remove an item
        BST.remove(30);

        System.out.println(BST.search(35));
    }
}
