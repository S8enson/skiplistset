/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skiplistset;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import javax.lang.model.element.Element;

/**
 *
 * @author Sam
 */
public class SkipListSet<E extends Comparable> extends AbstractSet<E> {

    /**
     * @param args the command line arguments
     */
    private int numElements, numLevels;
    public Node<E> firstNode;
    private Node<E> lastNode;
    Random random;

    SkipListSet() {

        firstNode = new Node(null);
        lastNode = new Node(null);
        random = new Random();
        firstNode.next = lastNode;
        //numLevels = 1;

    }

    SkipListSet(Collection<? extends E> c) {

    }

    public static void main(String[] args) {
        // TODO code application logic here
        SkipListSet<Integer> set = new SkipListSet<>();

        set.add(4);
        System.out.println(set);
        set.add(4);
        System.out.println(set);
        set.add(2);
        System.out.println(set);
        set.add(3);
        System.out.println(set);
        set.add(6);
        System.out.println(set);
        set.add(5);
        System.out.println(set);
        set.add(5);
        System.out.println(set);
    }

    @Override
    public boolean add(E o) {
        Node<E> newNode = new Node<>(o);
        Node<E> prevnewNode;
        Node<E> currentNode = new Node<>(null);
        Node<E> nullNode = new Node<E>(null);
        Node<E> nullNode2 = new Node<E>(null);
        int levels = numLevels();
        int dif = 0;
        boolean inserted = false;

        if (contains(o)) {
            return false;
        }
        numElements++;
        //currentNode = firstNode.next;
        currentNode = firstNode;

        // increase levels if more than current
        while (levels > numLevels + 1) {
            nullNode = new Node<E>(null);
            nullNode2 = new Node<E>(null);
            nullNode.down = firstNode;
            nullNode.next = nullNode2;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            nullNode2.down = currentNode;
            firstNode = nullNode;
            numLevels++;

        }

        // find level to start adding
        currentNode = firstNode;
        dif = numLevels + 1 - levels;
        for (int i = 0; i < dif; i++) {
            currentNode = currentNode.down;
        }
        // add to each level

        //seperate function add to all lists then link them together at end recursion possible?
        for (int i = 0; i < levels; i++) {
            //Node<E> newNode = new Node<>(o);
            if (currentNode.next.element == null || newNode.element.compareTo(currentNode.next.element) < 0) {
                newNode.next = currentNode.next;
                currentNode.next = newNode;

                //numElements++;
                //return true;
            } else {

                while (currentNode.next.element != null && newNode.element.compareTo(currentNode.next.element) != 0) {
                    if (newNode.element.compareTo(currentNode.next.element) < 0) {
                        newNode.next = currentNode.next;
                        currentNode.next = newNode;
                        //numElements++;
                        //return true;
                        inserted = true;
                        break;
                    }

                    currentNode = currentNode.next;

                }

                if (newNode.element.compareTo(currentNode.element) > 0 && !inserted) {
                    //return false;
                    newNode.next = currentNode.next;
                    currentNode.next = newNode;
                    //currentNode = newNode.next;//

                }
                //currentNode.next = newNode;

//numElements++;
                //return true;
                inserted = false;

//needto link to self above
            }
            if(i<levels-1){
            prevnewNode = newNode;
            newNode = new Node<>(o);
            prevnewNode.down = newNode;
            currentNode = currentNode.down;
            //newNode.down = newNode;
            }
        }

        return true;

    }

    public boolean remove(E o) {
        if (!contains(o)) {
            System.out.println("Not removed, not in the list");
            return false;
        } else {
            Node currentNode = firstNode;
            Node tempNode;
            while (currentNode != null) {
                while (currentNode.next.element != null && o.compareTo(currentNode.next.element) > 0) {
                    currentNode = currentNode.next;
                }
                if (currentNode.next.element == null || o.compareTo(currentNode.next.element) < 0 ) {
                    currentNode = currentNode.down;
                } else if (o.compareTo(currentNode.next.element) == 0) {
                    tempNode = currentNode.next;
                    currentNode.next = tempNode.next;
                    tempNode = null;
                    currentNode = currentNode.down;
                }
            }
            if(firstNode.next.element == null){
            firstNode = firstNode.down;
            numLevels--;
            }
            numElements--;
            return true;
        }
    }

    public int size() {

        return numElements;
    }

    public void clear() {
        Node lastNode = new Node(null);
        firstNode.next = lastNode;
        firstNode.down = null;
        numElements = 0;
        numLevels = 0;
    }

    public int numLevels() {
        boolean nextLevel = true;
        int level = 0;
        while (nextLevel) {
            level++;
            nextLevel = random.nextBoolean();
        }
        return level;
    }

    public void printString() {
        if (isEmpty()) {
            System.out.println("Set is empty!");
        } else {
            Node currentNode = firstNode;
            Node tempNode = firstNode;
            for (int i = numLevels + 1; i > 0; i--) {
                currentNode = tempNode;
                System.out.print("Level " + i + ": ");
                while (currentNode != null) {
                    System.out.print("-" + currentNode.element + "-");
                    currentNode = currentNode.next;

                }
                System.out.print("\n");
                tempNode = tempNode.down;
            }

            System.out.print("\n");
        }

    }

    public Iterator<E> iterator() {
        return new SkipIterator<E>(firstNode);
    }
// inner class which represents an iterator for a singly-linked list

    private class SkipIterator<E> implements Iterator<E> {

        private Node<E> nextNode; // next node to use for the iterator

        // constructor which accepts a reference to first node in list
        // and prepares an iterator which will iterate through the
        // entire linked list
        public SkipIterator(Node<E> firstNode) {
            nextNode = firstNode;
            for (int i = 0; i < numLevels; i++) {
                nextNode = nextNode.down;
            }
            // start with first non null node in bottom list
            nextNode = nextNode.next;
        }

        // returns whether there is still another element
        public boolean hasNext() {
            return (nextNode.next != null);
        }

        // returns the next element or throws a NoSuchElementException
        // it there are no further elements
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = nextNode.element;
            nextNode = nextNode.next;
            return element;
        }

        // remove method not supported by this iterator
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    protected class Node<E> {

        public E element;
        public Node<E> next;
        public Node<E> down;

        public Node(E element) {
            this.element = element;
            next = null;
            down = null;
        }
    }
}
