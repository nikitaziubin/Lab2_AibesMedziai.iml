package demo;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import utils.*;
import utils.Set;

import java.lang.reflect.Array;
import java.util.*;


/*
 * Set testing without Gui
 * Working with Intellij to get a nice rotated tree in the console,
 * You need to change the File -> Settings -> Editor -> File Encodings -> Global encoding to UTF-8
 *
 */
public class ManualTest {
    static RedBlackSet.BTreePrinter redBlackSetPrinter = new RedBlackSet.BTreePrinter();
    public static void main(String[] args) throws CloneNotSupportedException {
        RedBlackSet<Integer> redBlackSet = new RedBlackSet<Integer>();
        redBlackSet.add(9);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(8);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(3);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(6);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(5);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(7);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(10);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(13);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(11);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(14);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(21);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.add(20);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());

        redBlackSet.iteratorTest();

        /*redBlackSet.remove(5);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 3);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 20);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 11);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 14);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 9);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 21);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 7);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 13);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 6);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());
        redBlackSet.remove( 10);
        redBlackSetPrinter.printNode(redBlackSet.getRoot());*/
    }
}


