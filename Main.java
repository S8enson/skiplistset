package skiplistset;

import java.util.Iterator;
import java.util.Random;
import skiplistset.SkipListSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sam
 */
public class Main {

    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("TESTING SKIP LIST SET\n\n");
        SkipListSet<Integer> set = new SkipListSet();

        for (int i = 1; i <= 20; i++) {

            set.add(i);

        }
        System.out.println("Size of Skip List Set is: " + set.size());
        set.printString();

        System.out.println("ITERATE THROUGH ELEMENTS\n");

        Iterator<Integer> it = set.iterator();

        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }

        System.out.println("\nREMOVE 12, 2, 0\n");

        System.out.println("REMOVING 12");
        set.remove(12);
        set.printString();

        System.out.println("REMOVING 2");
        set.remove(2);
        set.printString();

        System.out.println("REMOVING 0");
        set.remove(0);

        System.out.println("REMOVE 19, 12, 29\n");

        System.out.println("REMOVING 19");
        set.remove(19);
        set.printString();

        System.out.println("REMOVING 12");
        set.remove(12);

        System.out.println("REMOVING 29");
        set.remove(29);

        System.out.println("ADDing 2");
        set.add(2);
        set.printString();

        System.out.println("CLEARING SET");
        set.clear();
        set.printString();

    }

}
