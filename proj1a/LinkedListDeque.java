public class LinkedListDeque<T> {

    public class Node {
        public Node next;
        public Node previous;
        public T item;

        Node(Node next, Node previous, T item){
            this.next = next;
            this.previous = previous;
            this.item = item;
        }
    }

    public Node sentinel;
    public int size;

//    Creates an empty linked list deque.
    LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }

//    Creates a deque with the first item;
    LinkedListDeque(T item){
        sentinel = new Node(null, null, null);
        sentinel.next = new Node(sentinel, sentinel, item);
        sentinel.previous = sentinel.next;
        size = 1;
    }

//    Adds an item to the front of the deque
    public void addFirst(T item) {
        sentinel.next = new Node(sentinel.next, sentinel, item);
        sentinel.next.next.previous = sentinel.next;

        size = size + 1;
    }

//    Adds and item to the back of the deque
    public void addLast(T item) {
        sentinel.previous = new Node(sentinel, sentinel.previous, item);
        sentinel.previous.previous.next = sentinel.previous;

        size = size + 1;
    }

    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }

    public T removeFirst() {
        if (size == 0) return null;

        T result = sentinel.next.item;
        sentinel.next.next.previous = sentinel;
        sentinel.next = sentinel.next.next;

        size = size - 1;

        return result;
    }

    public T removeLast() {

        if (size == 0) return null;

        T result = sentinel.previous.item;
        sentinel.previous.previous.next = sentinel;
        sentinel.previous = sentinel.previous.previous;

        size = size - 1;

        return result;
    }

//    Gets the ith item using iteration.
    public T get(int index) {

        Node p = sentinel.next;

        if (index >= size()) return null;

        for (int i = 0; i < size(); i++) {
            if (i == index) {
                break;
            }
            p = p.next;
        }

        return p.item;
    }

//    Gets the ith item using recursion. Needs a helper private method.
//    finds the node at the ith position.
    private Node findNode(int index){
        Node result = sentinel.next;

        while (index > 0) {
            result = result.next;
            index = index - 1;
        }

        return result;
    }

    public T getRecursive(int index) {
        Node result = getRecursionHelper(sentinel.next, index);
        return result.item;
    }

    public Node getRecursionHelper(Node pointer, int index) {
        if (index == 0) {
            return pointer;
        } else {
            if (pointer.next == null) {
                return pointer;
            }
            index--;
            pointer = pointer.next;
        }
        return pointer;
    }

}
