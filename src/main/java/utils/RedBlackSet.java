package utils;

import java.util.*;

public class RedBlackSet<E extends Comparable<E>> {

    private RedBlackNode<E> root = null;
    private RedBlackNode<E> nill = new RedBlackNode<E>((E)"N", "b", null, null);
    protected Comparator<? super E> c = Comparator.naturalOrder();
    RedBlackNode<E> lastAdded;
    public int size;

    public RedBlackSet() {

    }

    public RedBlackNode<E> getRoot () {
        return root;
    }

    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }
        if (root == null) {
            root = new RedBlackNode<>(element, "b", null, null);
            root.parent = nill;
            root.right = nill;
            root.left = nill;
            return;
        }
        addRecursive(element, root);
        fixup(lastAdded);
    }

    private RedBlackNode<E> addRecursive(E element, RedBlackNode<E> node) {
        if (node == nill) {
            size++;
            RedBlackNode<E> redBlackNode = new RedBlackNode<E>(element);
            redBlackNode.left = nill;
            redBlackNode.right = nill;
            lastAdded = redBlackNode;
            return redBlackNode;
        }
        int cmp = c.compare(element, node.element);
        if (cmp < 0) {
            RedBlackNode<E> n = addRecursive(element, node.left);
            node.left = n;
            if (n.parent == null){
                n.parent = node;
            }
        } else if (cmp > 0) {
            RedBlackNode<E> n = addRecursive(element, node.right);
            node.right = n;
            if (n.parent == null){
                n.parent = node;
            }
        }
        return node;
    }

    private void fixup(RedBlackNode<E> node) {
        while (Objects.equals(lastAdded.parent.color, "r")) {
            // for right node left node
            if (lastAdded.parent == lastAdded.parent.parent.left) {
                RedBlackNode<E> uncle = lastAdded.parent.parent.right;
                if (uncle.color.equals("r")) {
                    //case 1
                    lastAdded.parent.color = "b";
                    lastAdded.parent.parent.color = "r";
                    uncle.color = "b";
                    lastAdded = lastAdded.parent.parent;
                    continue;
                } else {
                    if (lastAdded == lastAdded.parent.right) {
                        // case2
                        lastAdded.parent = leftRotation(lastAdded);
                    }
                    // case3
                    lastAdded.parent.parent.parent = rightRotation(lastAdded.parent.parent);
                    lastAdded.parent.color = "b";
                    lastAdded.parent.parent.color = "r";
                }
            }
            // for right node
            else {
                RedBlackNode<E> uncle = lastAdded.parent.parent.left;
                if (uncle.color.equals("r")) {
                    //case 1
                    lastAdded.parent.color = "b";
                    lastAdded.parent.parent.color = "r";
                    uncle.color = "b";
                    lastAdded = lastAdded.parent.parent;
                    continue;
                } else {
                    if (lastAdded == lastAdded.parent.left) {
                        // case2
                        lastAdded.parent = rightRotation(lastAdded);
                    }
                    // case3
                    lastAdded.parent.parent.parent.right = leftRotation(lastAdded.parent.parent);
                    lastAdded.parent.color = "b";
                    lastAdded.parent.parent.color = "r";
                }
            }
        }
        root.color = "b";
    }

    private RedBlackNode<E> rightRotation(RedBlackNode<E> downNode) {
        RedBlackNode<E> upNode = downNode.left;
        downNode.left = upNode.right;
        upNode.right = downNode;
        if (downNode == root)
        {
            root = upNode;
        }
        return upNode;
    }

    private RedBlackNode<E> leftRotation(RedBlackNode<E> downNode) {
        RedBlackNode<E> upNode = downNode.right;
        downNode.right = upNode.left;
        upNode.left = downNode;
        if (downNode == root)
        {
            root = upNode;
        }
        return upNode;
    }

    private RedBlackNode<E> get(RedBlackNode<E> node, boolean findMax) {
        RedBlackNode<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    protected static class RedBlackNode<N> {

        // Element
        protected N element;
        // Pointer to the left subtree
        protected RedBlackNode<N> left;
        // Pointer to the right subtree
        protected RedBlackNode<N> right;
        protected RedBlackNode<N> parent;
        protected String color;

        protected RedBlackNode() {
        }
        protected RedBlackNode(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
            this.color = "r";
            this.parent = null;
        }
        protected RedBlackNode(N element, String color, RedBlackNode<N> left, RedBlackNode<N> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.color = color;
            this.parent = null;
        }
    }

    public static class BTreePrinter {

        public static <T extends Comparable<?>> void printNode(RedBlackNode<T> root) {
            int maxLevel = BTreePrinter.maxLevel(root);

            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        private static <T extends Comparable<?>> void printNodeInternal(List<RedBlackNode<T>> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
                return;

            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

            BTreePrinter.printWhitespaces(firstSpaces);

            List<RedBlackNode<T>> newNodes = new ArrayList<RedBlackNode<T>>();
            for (RedBlackNode<T> node : nodes) {
                if (node != null) {
                    System.out.print(node.element + node.color);
                    newNodes.add(node.left);
                    newNodes.add(node.right);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                BTreePrinter.printWhitespaces(betweenSpaces);
            }
            System.out.println(" ");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    BTreePrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).left != null)
                        System.out.print(" /");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(i + i - 1);

                    if (nodes.get(j).right != null)
                        System.out.print("\\");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
                }

                System.out.println("");
            }

            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++)
                System.out.print(" ");
        }

        private static <T extends Comparable<?>> int maxLevel(RedBlackNode<T> node) {
            if (node == null)
                return 0;

            return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
        }

        private static <T> boolean isAllElementsNull(List<T> list) {
            for (Object object : list) {
                if (object != null)
                    return false;
            }

            return true;
        }

    }


}
