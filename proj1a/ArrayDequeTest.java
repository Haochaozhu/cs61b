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

        a.removeLast();
        a.removeFirst();
        a.removeLast();
        a.removeFirst();
        a.removeFirst();
        a.removeFirst();

        a.printDeque();



    }

}
