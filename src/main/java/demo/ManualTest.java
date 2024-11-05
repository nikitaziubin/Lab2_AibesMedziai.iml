package demo;

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

    static Car[] cars;
    static ParsableSortedSet<Car> cSeries = new ParsableBstSet<>(Car::new, Car.byMake);

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Unify number formats
        executeTest();
    }

    public static void executeTest() throws CloneNotSupportedException {
        Car c1 = new Car("Renault", "Laguna", 2007, 50000, 1700);
        Car c2 = new Car.Builder()
                .make("Renault")
                .model("Megane")
                .year(2011)
                .mileage(20000)
                .price(3500)
                .build();
        Car c3 = new Car.Builder().buildRandom();
        Car c4 = new Car("Renault Laguna 2011 115900 700");
        Car c5 = new Car("Renault Megane 1946 365100 9500");
        Car c6 = new Car("Honda   Civic  2011  36400 80.3");
        Car c7 = new Car("Renault Laguna 2011 115900 7500");
        Car c8 = new Car("Renault Megane 1946 365100 950");
        Car c9 = new Car("Honda   Civic  2017  36400 850.3");

        Car[] carsArray = {c9, c7, c8, c5, c1, c6};
        Ks.oun("Car set:");
        ParsableSortedSet<Car> carsSet = new ParsableBstSet<>(Car::new);

        for (Car c : carsArray) {
            carsSet.add(c);
            Ks.oun("Set is expanded: " + c + ". Its size: " + carsSet.size());
        }
        Ks.oun("");
        Ks.oun(carsSet.toVisualizedString(""));
        Ks.oun("");
        Ks.oun("Testing remove Bst: ");
        Ks.oun("");
        //carsSet.remove(c9);
        Ks.oun(carsSet.toVisualizedString(""));
        Ks.oun("Testing containsAll: ");
        ParsableSortedSet<Car> carsSetToTest = new ParsableBstSet<>(Car::new);
        carsSetToTest.add(c1);
        carsSetToTest.add(c6);
        carsSetToTest.add(c9);
        boolean a = carsSet.containsAll(carsSetToTest);
        System.out.println(a);
        Ks.oun("");
        Ks.oun("Testing headSet: ");
        Ks.oun("");
        Set b = carsSet.headSet(c7);
        for (Object c : b) {
            System.out.println(c);
        }
        Ks.oun("");
        Ks.oun("Testing tailSet: ");
        Ks.oun("");
        b = carsSet.tailSet(c6);
        for (Object c : b) {
            System.out.println(c);
        }

        Ks.oun("");
        Ks.oun("Testing subSet: ");
        Ks.oun("");
        b = carsSet.subSet(c6, c6);
        for (Object c : b) {
            System.out.println(c);
        }


        Ks.oun("");
        Ks.oun("Testing retainAll: ");
        Ks.oun("");
        ParsableSortedSet<Car> carsToIterate = new ParsableBstSet<>(Car::new);
        carsToIterate.add(c9);
        carsToIterate.add(c5);
        carsToIterate.add(c7);
        carsToIterate.add(c8);
        carsSet.retainAll(carsToIterate);
        Ks.oun(carsSet.toVisualizedString(""));


        Ks.oun("");
        Ks.oun("Car set in the AVL tree:");
        Car[] expandedCarsArray = new Car[8];
        for (int i = 0; i < carsArray.length; i++) {
            expandedCarsArray[i] = carsArray[i];
        }
        expandedCarsArray[carsArray.length  ] = c2;
        expandedCarsArray[carsArray.length  + 1] = c3;
        ParsableSortedSet<Car> carsSetAvl = new ParsableAvlSet<>(Car::new);
        for (Car c : expandedCarsArray) {
            carsSetAvl.add(c);
        }
        Ks.ounn(carsSetAvl.toVisualizedString(""));
        carsSetAvl.remove(c6);
        Ks.ounn(carsSetAvl.toVisualizedString(""));
        Ks.oun("");
        Ks.oun("First additional task:");
        Ks.oun("");
        ParsableBstSet<Car> set = new ParsableBstSet<>(Car::new);
        for( Car car : carsArray ){
            set.add(car);
        }
        Ks.ounn(set.toVisualizedString(""));
        Ks.ounn(set.CheckBalance(2));
        Ks.oun("");
        Ks.oun("Second additional task:");
        Ks.oun("");
        BstSet<Integer> set2 = new BstSet<>();
        for (int i = 0; i < 255; i++)
            set2.add(i);
        System.out.println("The number of internal nodes in set with size: " + set2.size() + " is: "  + countInternalNodes(set2));


    }



    public static Integer countInternalNodes(Set<Integer> set){
        if (set.size() == 0)
        {
            return 0;
        }
        if (set.size() == 1 || set.size() == 3 || set.size() == 7 ){
            return 0;
        }
        if (set.size() == 15){
            return 2;
        }
        int setSize = 15;
        int countNodes = 2;
        int a = 2;
        while (set.size() != setSize)
        {
            a = 2 * a + 2;
            countNodes = a + countNodes;
            setSize = (setSize + 1) + setSize;
        }
        return countNodes;
    }
    static ParsableSortedSet<Car> generateSet(int amount, int n) {
        cars = new Car[n];
        for (int i = 0; i < n; i++) {
            cars[i] = new Car.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList(cars));

        cSeries.clear();
        Arrays.stream(cars).limit(amount).forEach(cSeries::add);
        return cSeries;
    }
}
