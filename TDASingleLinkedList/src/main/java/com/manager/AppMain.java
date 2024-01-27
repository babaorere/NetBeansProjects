package com.manager;

public class AppMain {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // create list
        LinkedList<Integer> list = new LinkedList<>();

        // initialize list
        for (int i = 4; i >= 1; i--) {
            list.addFirst(i);
        }

        // print list
        System.out.println(list.print());

        // list size
        System.out.println(list.size());

        // replace element
        list.setToCopy(3, 777);

        // print list
        System.out.println(list.print());

        // get the last entry
        System.out.println(list.getLastCopyData());

        // add last
        list.addLast(123);

        // print list
        System.out.println(list.print());

        // add last
        list.addLast(123);

        // print list
        System.out.println(list.print());

        list.insert(0, 30);

        // print list
        System.out.println(list.print());

        list.insert(list.size(), 40);

        // print list
        System.out.println(list.print());

        list.insert(1, 50);

        // print list
        System.out.println(list.print());

        list.setToCopy(1, 33);

        // print list
        System.out.println(list.print());

    }

}
