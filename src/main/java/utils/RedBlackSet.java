package utils;

import java.util.*;

public class RedBlackSet<E extends Comparable<E>> {
    static RedBlackSet.BTreePrinter redBlackSetPrinter = new RedBlackSet.BTreePrinter();
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    private RedBlackNode<E> root = null;
    //private RedBlackNode<E> nillLeftRight = new RedBlackNode<E>((E)"N", "b", null, null);
    private RedBlackNode<E> nill = new RedBlackNode<E>((E)"N", "b", null, null);
    protected RedBlackNode<E> specialNill = new RedBlackNode<E>((E)"N", "b", root, null);
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
            size++;
            root = new RedBlackNode<>(element, "b", null, null);
            root.parent = specialNill;
            root.right = nill;
            root.left = nill;
            return;
        }
        addRecursive(element, root);
        fixup(lastAdded);
    }

    private RedBlackNode<E> addRecursive(E element, RedBlackNode<E> node) {
        if (node.element == "N") {
            size++;
            RedBlackNode<E> redBlackNode = new RedBlackNode<E>(element);
            redBlackNode.left = new RedBlackNode<>(nill);
            redBlackNode.left.parent = redBlackNode;

            redBlackNode.right = new RedBlackNode<>(nill);
            redBlackNode.right.parent = redBlackNode;
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
                        RedBlackNode<E> c = lastAdded.parent;
                        leftRotation(lastAdded, lastAdded.parent.parent.parent);
                        lastAdded = c;
                    }
                    // case3
                    lastAdded.parent.color = "b";
                    lastAdded.parent.parent.color = "r";
                    rightRotation(lastAdded.parent.parent, lastAdded.parent.parent.parent);
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
                        RedBlackNode<E> c = lastAdded.parent;
                        rightRotation(lastAdded.parent, lastAdded.parent.parent);
                        lastAdded = c;
                    }
                    // case3
                    lastAdded.parent.color = "b";
                    lastAdded.parent.parent.color = "r";
                    leftRotation(lastAdded.parent.parent, lastAdded.parent.parent.parent);
                }
            }
        }
        root.color = "b";
    }

    private void rightRotation(RedBlackNode<E> downNode, RedBlackNode<E> nodeToConnectRotatedChain) {
        RedBlackNode<E> nodeWithOutChanges = new RedBlackNode<>(nodeToConnectRotatedChain.element, nodeToConnectRotatedChain.color,
                nodeToConnectRotatedChain.left, nodeToConnectRotatedChain.right, nodeToConnectRotatedChain.parent);
        RedBlackNode<E> upNode = downNode.left;
        downNode.left = upNode.right;
        downNode.parent = upNode;
        upNode.right = downNode;
        if (downNode == root)
        {
            upNode.parent = specialNill;
            root = upNode;
            return;
        }
        connectChainAfterRotation(nodeToConnectRotatedChain, upNode, nodeWithOutChanges);
    }

    private void leftRotation(RedBlackNode<E> downNode, RedBlackNode<E> nodeToConnectRotatedChain) {
        RedBlackNode<E> nodeWithOutChanges = new RedBlackNode<>(nodeToConnectRotatedChain.element, nodeToConnectRotatedChain.color,
                nodeToConnectRotatedChain.left, nodeToConnectRotatedChain.right, nodeToConnectRotatedChain.parent);
        RedBlackNode<E> upNode = downNode.right;
        downNode.right = upNode.left;
        downNode.parent = upNode;
        upNode.left = downNode;
        if (downNode == root)
        {
            upNode.parent = specialNill;
            root = upNode;
            return;
        }
        connectChainAfterRotation(nodeToConnectRotatedChain, upNode, nodeWithOutChanges);
    }

    private void connectChainAfterRotation(RedBlackNode<E> nodeToConnectRotatedChain, RedBlackNode<E> upNode, RedBlackNode<E> nodeWithOutChanges)
    {
        int cmp = c.compare(nodeToConnectRotatedChain.element, upNode.element);
        if (cmp > 0) {
            nodeToConnectRotatedChain.left = upNode;
        }
        else {
            nodeToConnectRotatedChain.right = upNode;
        }
        if(nodeToConnectRotatedChain.element == nodeWithOutChanges.element)
        {
            upNode.parent = nodeToConnectRotatedChain;
        }
    }

    private RedBlackNode<E> get(RedBlackNode<E> node, boolean findMax) {
        RedBlackNode<E> parent = null;
        while (node.element != "N") {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    public boolean find(E element) {
        if (element == root.element)
        {
            return true;
        }
        RedBlackNode<E> current = root;
        while (current != nill) {
            int cmp = c.compare(current.element,element);
            if(cmp > 0)
            {
                current = current.left;
            }
            else {
                current = current.right;
            }
            if (current.element.equals(element))
            {
                return true;
            }
        }
        return false;
    }

    private RedBlackNode<E> lastDeleted;
    private  String originalColorOfDeletedNode;
    public void remove(E element) {
        removeRecursive(element, root);
        size--;
        BTreePrinter.printNode(root);
        if (originalColorOfDeletedNode == "b")
        {
            deleteionFixUp(lastDeleted);
        }
    }
    private RedBlackNode<E> removeRecursive(E element, RedBlackNode<E> node) {
        if (node == null) {
            throw new IllegalArgumentException("No element in binary search tree");
        }
        int cmp = c.compare(element, node.element);
        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
            return node;
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
            return node;
        }
        originalColorOfDeletedNode = node.color;
        if (node.left.element == "N" ) {
            node.right.parent = node.parent;
            lastDeleted = node.right;
            return node.right;
        }
        else if (node.right.element == "N") {
            node.left.parent = node.parent;
            lastDeleted = node.left;
            return node.left;
        }
        else if (node.right.element != "N" && node.left.element != "N") {
            RedBlackNode<E> a = get(node.right, false);
            a.left = node.left;
            node.left.parent = a;

            if (a != node.right) {
                a.parent.left = a.right;
                if (a.right != nill) {
                    a.right.parent = a.parent;
                }
                a.right = node.right;
                node.right.parent = a;
            }

            a.parent = node.parent;
            RedBlackNode<E> nil = new RedBlackNode<>(nill);
            nil.parent = a;
            lastDeleted = (a.right.element != "N" && a.right != null) ? a.right : a;
            BTreePrinter.printNode(root);
            return a;
            //a.parent = node.parent;
            //lastDeleted = node.right;
            //return node.right;
        }

        return null;
    }
    private void deleteionFixUp(RedBlackNode<E> node)
    {
        while (lastDeleted != root && lastDeleted.color == "b")
        {
            if (lastDeleted == lastDeleted.parent.left)
            {
                //for the left node
                RedBlackNode<E> siblingOfLastDeleted = lastDeleted.parent.right;
                if (siblingOfLastDeleted.color == "r")
                {
                    siblingOfLastDeleted.color = "b";
                    lastDeleted.parent.color = "r";
                    leftRotation(lastDeleted.parent, lastDeleted.parent.parent);
                    siblingOfLastDeleted = lastDeleted.parent.right;
                }
                if (siblingOfLastDeleted.left.color == "b" && siblingOfLastDeleted.right.color == "b")
                {
                    siblingOfLastDeleted.color = "r";
                    lastDeleted = lastDeleted.parent;
                }
                else {
                    if (siblingOfLastDeleted.right.color == "b")
                    {
                        siblingOfLastDeleted.left.color = "b";
                        siblingOfLastDeleted.color = "r";
                        rightRotation(siblingOfLastDeleted, lastDeleted.parent);
                        siblingOfLastDeleted = lastDeleted.parent.right;
                    }
                    siblingOfLastDeleted.color = lastDeleted.parent.color;
                    lastDeleted.parent.color = "b";
                    siblingOfLastDeleted.right.color = "b";
                    leftRotation(lastDeleted.parent, lastDeleted.parent.parent);
                    lastDeleted = root;
                }
            }
            else {
                //for the right node
                RedBlackNode<E> siblingOfLastDeleted = lastDeleted.parent.left;
                if (siblingOfLastDeleted.color == "r")
                {
                    siblingOfLastDeleted.color = "b";
                    lastDeleted.parent.color = "r";
                    rightRotation(lastDeleted.parent, lastDeleted.parent.parent);
                    siblingOfLastDeleted = lastDeleted.parent.left;

                }
                if (siblingOfLastDeleted.right.color == "b" && siblingOfLastDeleted.left.color == "b")
                {
                    siblingOfLastDeleted.color = "r";
                    lastDeleted = lastDeleted.parent;
                }
                else {
                    if (siblingOfLastDeleted.left.color == "b")
                    {
                        siblingOfLastDeleted.right.color = "b";
                        siblingOfLastDeleted.left.color = "r";
                        leftRotation(siblingOfLastDeleted, lastDeleted.parent);
                        siblingOfLastDeleted = lastDeleted.parent.left;
                    }
                    siblingOfLastDeleted.color = lastDeleted.parent.color;
                    lastDeleted.parent.color = "b";
                    siblingOfLastDeleted.left.color = "b";
                    rightRotation(lastDeleted.parent, lastDeleted.parent.parent);
                    lastDeleted = root;
                }

            }
        }
        lastDeleted.color = "b";
    }
    protected static class RedBlackNode<N> {
        protected N element;
        protected RedBlackNode<N> left;
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
        protected RedBlackNode(N element, String color, RedBlackNode<N> left, RedBlackNode<N> right, RedBlackNode<N> parent) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.color = color;
            this.parent = parent;
        }
        protected RedBlackNode(RedBlackNode<N> node) {
            this.element = node.element;
            this.left = new RedBlackNode<N>((N)"N", "b", null, null);
            this.right = new RedBlackNode<N>((N)"N", "b", null, null);;
            this.color = node.color;
            this.parent = node.parent;
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
                    if (node.element == "N") {
                     System.out.print("N");
                    }
                    else {
                        if (Objects.equals(node.color, "r"))
                        {
                            System.out.print(RED + node.element + RESET);
                        }
                        else {
                            System.out.print(node.element);
                        }
                    }
                    newNodes.add(node.left);
                    newNodes.add(node.right);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                BTreePrinter.printWhitespaces(betweenSpaces);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    BTreePrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).left != null)
                        System.out.print("/");
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
