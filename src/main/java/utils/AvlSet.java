package utils;

import java.util.Comparator;

/**
 * The AVL-tree implementation of a ordered set.
 *
 * @param <E> Type of the set element. Must implement the Comparable<E> interface, or
 * an object that implements Comparator<E> interface must be passed through the class constructor
 * @author darius.matulis@ktu.lt
 * @task Review and understand the provided methods.
 */
public class AvlSet<E extends Comparable<E>> extends BstSet<E> implements SortedSet<E> {

    public AvlSet() {
    }

    public AvlSet(Comparator<? super E> c) {
        super(c);
    }

    /**
     * Adds a new element to the set.
     *
     * @param element
     */
    @Override
    public void add(E element) {

        root = addRecursive(element, (AVLNode<E>) root);

    }

    @Override
    public void addAll(Set<E> set) {
        if (set == null) {
            throw new IllegalArgumentException("Set is null in addAll(Set<E> set)");
        }
        for (E element : set) {
            add(element);
        }
    }
    public BstNode<E> getRoot() {
        return root;
    }
    /**
     * A recursive method used as helper method for adding a new element to the set
     *
     * @param element
     * @param node
     * @return
     */
    private AVLNode<E> addRecursive(E element, AVLNode<E> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(element);
        }
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.setLeft(addRecursive(element, node.getLeft()));
            if ((height(node.getLeft()) - height(node.getRight())) == 2) {
                int cmp2 = c.compare(element, node.getLeft().element);
                node = cmp2 < 0 ? rightRotation(node) : doubleRightRotation(node);
            }
        } else if (cmp > 0) {
            node.setRight(addRecursive(element, node.getRight()));
            if ((height(node.getRight()) - height(node.getLeft())) == 2) {
                int cmp2 = c.compare(node.getRight().element, element);
                node = cmp2 < 0 ? leftRotation(node) : doubleLeftRotation(node);
            }
        }
        node.height = Math.max(height(node.getLeft()), height(node.getRight())) + 1;
        return node;
    }

    /**
     * Removes an element from the set.
     *
     * @param element
     */
    /*@Override
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }
        root = removeRecursive(element, (AVLNode<E>) root);
        size--;
    }

    private AVLNode<E> removeRecursive(E element, AVLNode<E> n) {
        if (n == null) {
            size--;
            return new AVLNode<>(element);
        }
        int cmp = c.compare(element, n.element);
        if (cmp < 0) {
            n.setLeft(removeRecursive(element, n.getLeft()));
            int heightLeft = height(n.getLeft());
            int heightRight = height(n.getRight());
            if (Math.abs(heightLeft) - Math.abs(heightRight) == 2) {
                int cmp2 = c.compare(n.getRight().element, n.getLeft().element);
                n = cmp2 < 0 ? rightRotation(n) : doubleRightRotation(n);
            }
            return n;
        } else if (cmp > 0) {
            n.setRight(removeRecursive(element, n.getRight()));
            int heightLeft = height(n.getLeft());
            int heightRight = height(n.getRight());
            if (Math.abs(heightRight) - Math.abs(heightLeft) == 2) {
                int cmp2 = c.compare(n.getLeft().element, n.getRight().element);
                n = cmp2 < 0 ? leftRotation(n) : doubleLeftRotation(n);
            }
            return n;
        }

        if ( n.getLeft() == null ) {
            n.height = Math.max(height(n.getLeft()), height(n.getRight())) + 1;
            return n.getRight();
        }
        else if (n.getRight() == null) {
            n.height = Math.max(height(n.getLeft()), height(n.getRight())) + 1;
            return n.getLeft();
        }
        else if (n.getRight() != null && n.getLeft() != null) {
            AVLNode<E> a = (AVLNode<E>) getMin(n.getRight());
            a.left = n.getLeft();
            n = n.getRight();
            n.height = Math.max(height(n.getLeft()), height(n.getRight())) + 1;
            int heightLeft = height(n.getLeft());
            int heightRight = height(n.getRight());
            if (Math.abs(heightLeft) - Math.abs(heightRight) == 2) {
                int cmp2 = c.compare(n.getLeft().element, n.getRight().element);
                n = cmp2 < 0 ? rightRotation(n) : doubleRightRotation(n);
            }else if (Math.abs(heightRight) - Math.abs(heightLeft) == 2) {
                int cmp2 = c.compare(n.getRight().element, n.getLeft().element);
                n = cmp2 < 0 ? leftRotation(n) : doubleLeftRotation(n);
            }
            return n;
        }
        return n;
    }*/

    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }
        root = removeRecursive(element, (AVLNode<E>) root);
    }

    private AVLNode<E> removeRecursive(E element, AVLNode<E> node) {
        if (node == null) {
            return null;
        }

        int cmp = c.compare(element, node.element);
        if (cmp < 0) {
            node.setLeft(removeRecursive(element, node.getLeft()));
        } else if (cmp > 0) {
            node.setRight(removeRecursive(element, node.getRight()));
        } else {
            // Node to be deleted found
            if (node.left == null || node.right == null) {
                node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
            } else {
                AVLNode<E> minNode = (AVLNode<E>) getMin(node.getRight());
                node.element = minNode.element;
                node.setRight(removeRecursive(minNode.element, node.getRight()));
            }
        }
        if (node == null) {
            return null;
        }
        node.height = Math.max(height(node.getLeft()), height(node.getRight())) + 1;
        return balance(node);
    }

    private AVLNode<E> balance(AVLNode<E> node) {
        int balanceFactor = getBalanceFactor(node);

        // Left heavy
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.getLeft()) < 0) {
                node.setLeft(leftRotation(node.getLeft()));
            }
            return rightRotation(node);
        }

        // Right heavy
        if (balanceFactor < -1) {
            if (getBalanceFactor(node.getRight()) > 0) {
                node.setRight(rightRotation(node.getRight()));
            }
            return leftRotation(node);
        }

        return node;
    }

    private int getBalanceFactor(AVLNode<E> node) {
        return (node == null) ? 0 : height(node.getLeft()) - height(node.getRight());
    }

    // AVL tree rotation methods

    //           n2
    //          /                n1
    //         n1      ==>      /  \
    //        /                n3  n2
    //       n3

    private AVLNode<E> rightRotation(AVLNode<E> n2) {
        AVLNode<E> n1 = n2.getLeft();
        n2.setLeft(n1.getRight());
        n1.setRight(n2);
        n2.height = Math.max(height(n2.getLeft()), height(n2.getRight())) + 1;
        n1.height = Math.max(height(n1.getLeft()), height(n2)) + 1;
        return n1;
    }

    private AVLNode<E> leftRotation(AVLNode<E> n1) {
        AVLNode<E> n2 = n1.getRight();
        n1.setRight(n2.getLeft());
        n2.setLeft(n1);
        n1.height = Math.max(height(n1.getLeft()), height(n1.getRight())) + 1;
        n2.height = Math.max(height(n2.getRight()), height(n1)) + 1;
        return n2;
    }

    //            n3               n3
    //           /                /                n2
    //          n1      ==>      n2      ==>      /  \
    //           \              /                n1  n3
    //            n2           n1
    //
    private AVLNode<E> doubleRightRotation(AVLNode<E> n3) {
        n3.left = leftRotation(n3.getLeft());
        return rightRotation(n3);
    }

    private AVLNode<E> doubleLeftRotation(AVLNode<E> n1) {
        n1.right = rightRotation(n1.getRight());
        return leftRotation(n1);
    }

    private int height(AVLNode<E> n) {
        return (n == null) ? -1 : n.height;
    }
    /**
     * Inner class of tree node
     *
     * @param <N> node element data type
     */
    protected class AVLNode<N> extends BstNode<N> {

        protected int height;

        protected AVLNode(N element) {
            super(element);
            this.height = 0;
        }

        protected void setLeft(AVLNode<N> left) {
            this.left = left;
        }

        protected AVLNode<N> getLeft() {
            return (AVLNode<N>) left;
        }

        protected void setRight(AVLNode<N> right) {
            this.right = right;
        }

        protected AVLNode<N> getRight() {
            return (AVLNode<N>) right;
        }
    }
}
