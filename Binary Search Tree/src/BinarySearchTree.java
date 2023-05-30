import java.util.ArrayList;

public class BinarySearchTree <E extends Comparable<E>> {
    TreeNode<E> root;
    public BinarySearchTree(){
    }

    //true if inserted
    public boolean insert(E newNode){
        if (root == null){
            root = new TreeNode(newNode);
            return true;
        }
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null){
            if (newNode.compareTo(current.getValue()) < 0){
                parent = current;
                current = current.getLeft();
            }else if(newNode.compareTo(current.getValue()) > 0){
                parent = current;
                current = current.getRight();
            }else{
                System.out.println("This value exists in the tree.");
                return false;
            }
        }
        if (newNode.compareTo(parent.getValue()) < 0){
            parent.setLeft(new TreeNode(newNode));
        }else{
            parent.setRight(new TreeNode(newNode));
        }
        return true;

    }

    //true if found
    public boolean search(E find){
        ArrayList<E> path = new ArrayList<>();
        TreeNode<E> current = root;
        while (current != null){
            if (current.getValue() == find){
                for (E i : path){
                    System.out.print(i+" ");
                }
                return true;
            }
            if (current.getValue().compareTo(find) > 0){
                path.add( current.getValue());
                current = current.getLeft();
            }else{
                path.add(current.getValue());
                current = current.getRight();
            }
        }
        return false;
    }

    //true if found
    public boolean remove(E delete){
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        if (current == null) return false;
        while (current != null){
            if (delete.compareTo(current.getValue()) < 0){
                parent = current;
                current = current.getLeft();
            }else if (delete.compareTo(current.getValue()) > 0){
                parent = current;
                current = current.getRight();
            }else{
                if (current.getLeft() == null){
                    //Prints the path
                    System.out.println(current.getValue()+" "+parent.getValue());
                    if (current.getRight().getValue().compareTo(parent.getValue()) < 0){
                        parent.setLeft(current.getRight());
                        return true;
                    }else if (current.getRight().getValue().compareTo(parent.getValue()) > 0){
                        parent.setRight(current.getRight());
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    break;
                }
                
            }
        }
        //current equals delete
        TreeNode<E> rightMostParent = current;
        TreeNode<E> rightMost = current.getLeft();
        while (rightMost.getRight() != null){
            rightMostParent =rightMost;
            rightMost = rightMost.getRight();
        }
        current.setValue(rightMost.getValue());
        if (rightMostParent.getRight() != current){
            rightMostParent.setLeft(rightMost.getLeft());
        }else{
            rightMostParent.setRight(rightMost.getLeft());
        }
        return true;
    }




}
