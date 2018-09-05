public class ArrayDequeTest {

    public static void main(String[] args) {

        ArrayDeque<Integer> a = new ArrayDeque();

        a.addFirst(21);
        a.addFirst(43);
        a.addLast(212);
        a.addFirst(63);
        a.addLast(212);
        a.addFirst(45);
        a.addLast(67);
        a.addFirst(12);
        a.addFirst(53);



        System.out.println(a.get(0));
        System.out.println(a.get(1));
        System.out.println(a.get(2));
        System.out.println(a.get(3));
        System.out.println(a.get(4));
        System.out.println(a.get(5));
        System.out.println(a.get(6));


    }

}
