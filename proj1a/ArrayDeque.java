public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst = 3;
    private int nextLast = 4;

    //    Creates a empty list and with an array with a length of 8
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    private void resize() {

    }

    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = nextFirst - 1;
    }

    public void addLast(T item) {

    }

    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {

    }

//    public T removeFirst() {
//        return 0;
//    }
//
//    public T removeLast() {
//
//    }
//
//    public T get(int index) {
//
//    }
}
