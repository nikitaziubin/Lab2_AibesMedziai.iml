package utils;
import java.util.*;

public class RedBlackSet<E extends Comparable<E>> {
    public static final String RED1 = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    protected RedBlackNode<E> root = null;
    private RedBlackNode<E> nill = new RedBlackNode<>((E) "N", BLACK, null, null);
    RedBlackNode<E> specialNill = new RedBlackNode<>((E)"N", BLACK, root, null);
    protected Comparator<? super E> c = Comparator.naturalOrder();
    RedBlackNode<E> lastAdded;
    public int size;
    private static final String RED = "r";
    private static final String BLACK = "b";

    public RedBlackSet() {

    }

    public RedBlackNode<E> getRoot() {
        return root;
    }

    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }
        if (root == null) {
            size++;
            root = new RedBlackNode<>(element, BLACK, null, null);
            root.parent = specialNill;
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
            redBlackNode.left.parent = redBlackNode;

            redBlackNode.right = nill;
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
        while (Objects.equals(lastAdded.parent.color, RED)) {
            // for right node left node
            if (lastAdded.parent == lastAdded.parent.parent.left) {
                RedBlackNode<E> uncle = lastAdded.parent.parent.right;
                if (uncle.color.equals(RED)) {
                    //case 1
                    lastAdded.parent.color = BLACK;
                    lastAdded.parent.parent.color = RED;
                    uncle.color = BLACK;
                    lastAdded = lastAdded.parent.parent;
                    continue;
                } else {
                    if (lastAdded == lastAdded.parent.right) {
                        // case2
                        RedBlackNode<E> c = lastAdded.parent;
                        leftRotation(lastAdded.parent, lastAdded.parent.parent.parent);
                        lastAdded = c;
                    }
                    // case3
                    lastAdded.parent.color = BLACK;
                    lastAdded.parent.parent.color = RED;
                    rightRotation(lastAdded.parent.parent, lastAdded.parent.parent.parent);
                }
            }
            // for right node
            else {
                RedBlackNode<E> uncle = lastAdded.parent.parent.left;
                if (uncle.color.equals(RED)) {
                    //case 1
                    lastAdded.parent.color = BLACK;
                    lastAdded.parent.parent.color = RED;
                    uncle.color = BLACK;
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
                    lastAdded.parent.color = BLACK;
                    lastAdded.parent.parent.color = RED;
                    leftRotation(lastAdded.parent.parent, lastAdded.parent.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private void leftRotation(RedBlackNode<E> x){
        RedBlackNode<E> y = x.right;
        x.right = y.left;
        if (y.left != nill && y.left != null){
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == specialNill){
            root = y;
        }
        else if(x == x.parent.left) {
            x.parent.left = y;
        }
        else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotation(RedBlackNode<E> x) {
        RedBlackNode<E> y = x.left;
        x.left = y.right;
        if (y.right != nill && y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == specialNill) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.right = x;
        x.parent = y;
    }


    private void rightRotation(RedBlackNode<E> downNode, RedBlackNode<E> nodeToConnectRotatedChain) {
        RedBlackNode<E> nodeWithOutChanges = new RedBlackNode<>(nodeToConnectRotatedChain.element, nodeToConnectRotatedChain.color,
                nodeToConnectRotatedChain.left, nodeToConnectRotatedChain.right, nodeToConnectRotatedChain.parent);
        RedBlackNode<E> upNode = downNode.left;
        downNode.left = upNode.right;

        upNode.right.parent = downNode;

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

        upNode.left.parent = downNode;

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
        int cmp;
        if (upNode.element == "N")
        {
            cmp = 1;
        } else {
            cmp = c.compare(nodeToConnectRotatedChain.element, upNode.element);
        }

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
        while (node != nill) {
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

    public void clear() {
        root = null;
        size = 0;
    }

    private RedBlackNode<E> lastDeleted;
    private RedBlackNode<E> y;
    private RedBlackNode<E> deleted;
    private String originalColorOfDeletedNode;
    public void remove(E element) {
        removeRecursive(element, root);
        if ( deleted == null ){
            return;
        }
        if (deleted == root)
        {
            return;
        }
        removeRecursiveFromBook(deleted);
        size--;
        if (originalColorOfDeletedNode == BLACK)
        {
            deleteionFixUp();
        }
    }

    private void removeRecursiveFromBook(RedBlackNode<E> nodeThatWasDeleted) {
        y = nodeThatWasDeleted;
        originalColorOfDeletedNode = y.color;
        if (nodeThatWasDeleted.left == nill)
        {
            lastDeleted = nodeThatWasDeleted.right;
            rbTransplant(nodeThatWasDeleted, nodeThatWasDeleted.right);
        }
        else if (nodeThatWasDeleted.right == nill){
            lastDeleted = nodeThatWasDeleted.left;
            rbTransplant(nodeThatWasDeleted, nodeThatWasDeleted.left);
        }
        else {
            y = get(nodeThatWasDeleted.right, false);
            originalColorOfDeletedNode = y.color;
            lastDeleted = y.right;
            if (y != nodeThatWasDeleted.right){
                rbTransplant(y, y.right);
                y.right = nodeThatWasDeleted.right;
                y.right.parent = y;
            }
            else {
                lastDeleted.parent = y;
            }
            rbTransplant(nodeThatWasDeleted, y);
            y.left = nodeThatWasDeleted.left;
            y.left.parent = y;
            y.color = nodeThatWasDeleted.color;
        }
    }
    private void rbTransplant(RedBlackNode<E> u, RedBlackNode<E> v) {
        if (u.parent == nill) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }

        if (v != null) {
            v.parent = u.parent;
        }
    }

    private RedBlackNode<E> removeRecursive(E element, RedBlackNode<E> node) {
        if (node == nill) {
            return nill;
        }

        int cmp = c.compare(element, node.element);
        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else {
            deleted = node;
            if (node.left == nill) {
                return node.right;
            } else if (node.right == nill) {
                return node.left;
            } else {
                RedBlackNode<E> successor = get(node.right, true);
                node.element = successor.element;
                node.right = removeRecursive(successor.element, node.right);
            }
        }
        return node;
    }

    private void deleteionFixUp() {
        while (lastDeleted != root && lastDeleted.color == BLACK) {
            if (lastDeleted == lastDeleted.parent.left) {
                // For the left node
                RedBlackNode<E> siblingOfLastDeleted = lastDeleted.parent.right;

                if (siblingOfLastDeleted != null && siblingOfLastDeleted.color == RED) {
                    siblingOfLastDeleted.color = BLACK;
                    lastDeleted.parent.color = RED;
                    leftRotation(lastDeleted.parent);
                    siblingOfLastDeleted = lastDeleted.parent.right;
                }

                if (siblingOfLastDeleted != null &&
                        (siblingOfLastDeleted.right == null || siblingOfLastDeleted.left == null ||
                                (siblingOfLastDeleted.right != null && siblingOfLastDeleted.right.color == BLACK) &&
                                        (siblingOfLastDeleted.left != null && siblingOfLastDeleted.left.color == BLACK))) {
                    siblingOfLastDeleted.color = RED;
                    lastDeleted = lastDeleted.parent;
                } else if (siblingOfLastDeleted != null) {
                    if (siblingOfLastDeleted.right != null && siblingOfLastDeleted.right.color == BLACK) {
                        if (siblingOfLastDeleted.left != null) {
                            siblingOfLastDeleted.left.color = BLACK;
                        }
                        siblingOfLastDeleted.color = RED;
                        rightRotation(siblingOfLastDeleted);
                        siblingOfLastDeleted = lastDeleted.parent.right;
                    }

                    siblingOfLastDeleted.color = lastDeleted.parent.color;
                    lastDeleted.parent.color = BLACK;
                    if (siblingOfLastDeleted.right != null) {
                        siblingOfLastDeleted.right.color = BLACK;
                    }
                    leftRotation(lastDeleted.parent);
                    lastDeleted = root;
                }
            } else {
                // For the right node
                RedBlackNode<E> siblingOfLastDeleted = lastDeleted.parent.left;

                if (siblingOfLastDeleted != null && siblingOfLastDeleted.color == RED) {
                    siblingOfLastDeleted.color = BLACK;
                    lastDeleted.parent.color = RED;
                    rightRotation(lastDeleted.parent);
                    siblingOfLastDeleted = lastDeleted.parent.left;
                }

                if (siblingOfLastDeleted != null &&
                        (siblingOfLastDeleted.right == null || siblingOfLastDeleted.left == null ||
                                (siblingOfLastDeleted.right != null && siblingOfLastDeleted.right.color == BLACK) &&
                                        (siblingOfLastDeleted.left != null && siblingOfLastDeleted.left.color == BLACK))) {
                    siblingOfLastDeleted.color = RED;
                    lastDeleted = lastDeleted.parent;
                } else if (siblingOfLastDeleted != null) {
                    if (siblingOfLastDeleted.left != null && siblingOfLastDeleted.left.color == BLACK) {
                        if (siblingOfLastDeleted.right != null) {
                            siblingOfLastDeleted.right.color = BLACK;
                        }
                        siblingOfLastDeleted.color = RED;
                        leftRotation(siblingOfLastDeleted);
                        siblingOfLastDeleted = lastDeleted.parent.left;
                    }

                    siblingOfLastDeleted.color = lastDeleted.parent.color;
                    lastDeleted.parent.color = BLACK;
                    if (siblingOfLastDeleted.left != null) {
                        siblingOfLastDeleted.left.color = BLACK;
                    }
                    rightRotation(lastDeleted.parent);
                    lastDeleted = root;
                }
            }
        }
        lastDeleted.color = BLACK;
    }

    public  void iteratorTest(){
        IteratorRedBlackTree iteratorRedBlackTree  = new IteratorRedBlackTree(false);
        while (iteratorRedBlackTree.hasNext()) {
            iteratorRedBlackTree.next();
            iteratorRedBlackTree.remove();
            BTreePrinter.printNode(root);
        }
    }

    public class IteratorRedBlackTree implements Iterator<E> {

        private final Stack<RedBlackNode<E>> stack = new Stack<>();
        // Specifies the direction of the iterator, true for ascending, false for descending
        private final boolean ascending;
        // Required for the remove() method.
        private RedBlackNode<E> lastInStack;
        private RedBlackNode<E> last;

        public IteratorRedBlackTree(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public E next() {// If stack is empty
            if (stack.empty()) {
                lastInStack = root;
                last = null;
                return null;
            } else {
                // Returns the top element of the stack
                RedBlackNode<E> n = stack.pop();
                // The last returned element is saved, as well as the last element in the stack.
                // Needed for remove() method
                lastInStack = stack.isEmpty() ? root : stack.peek();
                last = n;
                RedBlackNode<E> node = ascending ? n.right : n.left;
                // If ascending is true, the minimum element of the right sub-tree is searched,
                // and all elements in the search path are placed on the stack
                toStack(node);
                return n.element;
            }
        }

        @Override
        public void remove() {
            if (lastInStack == null) {
                root = null;
            }
            if (last == root) {
                root = removeRecursive(last.element, lastInStack);
                size--;
                return;
            }

            removeRecursive(last.element, lastInStack);
            if ( deleted == null ){
                return;
            }
            if (deleted == root)
            {
                return;
            }
            removeRecursiveFromBook(deleted);
            size--;
            if (originalColorOfDeletedNode == BLACK)
            {
                deleteionFixUp();
            }
        }

        private void toStack(RedBlackNode<E> node) {
            while (node != nill) {
                stack.push(node);
                node = ascending ? node.left : node.right;
            }
        }
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
            this.color = RED;
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
            this.left = node.left;
            this.right = node.right;
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
                        if (Objects.equals(node.color, RED))
                        {
                            System.out.print(RED1 + node.element + RESET);
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
