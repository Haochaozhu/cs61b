public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst = 0;
    private int nextLast = 1;

    //    Creates a empty list. Initial array length is 8.
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    private void moveFrontPointer() {
        nextFirst = nextFirst - 1;
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
    }

    private void moveBackPointer() {
        nextLast = nextLast + 1;
        if (nextLast > items.length - 1) {
            nextLast = 0;
        }
    }

//    Doubles the size of the array.

    private void resizeGrow() {
          T[] a = (T[]) new Object[items.length * 2];
          System.arraycopy(items, 0, a, 0, nextLast);
          System.arraycopy(items, nextLast, a,
                 nextLast + items.length, items.length - nextLast);
          items = a;
          nextFirst = nextFirst + items.length / 2;
    }

    private void resizeShrink() {
//        Shrinks the size to half
        T[] a = (T[]) new Object[items.length / 2];

//        Copy items from items[] to a[]. The items need to be copied will always be items.length / 4 - 1.
//        When shrinking, the number of items will always be items.length / 4 - 1.
        for (int i = 0; i < items.length / 4; i++) {
            a[i] = getFrontItem();
            nextFirst++;
            if (nextFirst == items.length) {
                nextFirst = 0;
            }
        }

//        The position of nextLast will be the number of items + 1.
        nextLast = items.length / 4 - 1;
        items = a;
        nextFirst = items.length - 1;
    }

    public T getFrontItem() {
        if (nextFirst + 1 == items.length) {
            return items[0];
        } else {
            return items[nextFirst + 1];
        }
    }

    public T getBackItem() {
        if (nextLast - 1 < 0) {
            return items[items.length - 1];
        } else {
            return items[nextLast - 1];
        }
    }


    public void addFirst(T item) {
        items[nextFirst] = item;
        moveFrontPointer();

        size = size + 1;

        if (size == items.length) {
            resizeGrow();
        }

    }

    public void addLast(T item) {
        items[nextLast] = item;
        moveBackPointer();

        size = size + 1;


        if (size == items.length) {
            resizeGrow();
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (nextFirst < nextLast) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(items[i] + ",");
            }
        } else {
            for (int i = nextFirst + 1; i < items.length; i++) {
                System.out.print(items[i] + ",");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(items[i] + ",");
            }
        }


    }

//    Removes and returns the item at the front of the deque.
// If no such item exists, returns null.

    public T removeFirst() {
        int firstPointer = nextFirst + 1;
        if (firstPointer == items.length) {
            firstPointer = 0;
        }

        nextFirst++;
        if (nextFirst == items.length) {
            nextFirst = 0;
        }

        if (items[firstPointer] == null) {
            return null;
        }

        T returnItem = items[firstPointer];
        items[firstPointer] = null;

        size = size - 1;

//        If usage is less than 0.25, resize the array.
        if (items.length == 8) {
            return returnItem;
        } else {
            if (4 * size < items.length) {
                resizeShrink();
                return returnItem;
            }
        }
        return returnItem;
    }

//    Removes and returns the item at the back of the deque.
// If no such item exists, returns null.
    public T removeLast() {
        int lastPointer = nextLast - 1;
        if (lastPointer < 0) {
            lastPointer = items.length - 1;
        }

        nextLast--;
        if (nextLast < 0) {
            nextLast = items.length - 1;
        }

        if (items[lastPointer] == null) {
            return null;
        }

        T returnItem = items[lastPointer];
        items[lastPointer] = null;

        size = size - 1;


        if (items.length < 16) {
            return returnItem;
        } else {
            if (size * 4 < items.length) {
                resizeShrink();
                return returnItem;
            }
        }
        return returnItem;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }

        if (nextFirst > nextLast) {
            if ((nextFirst + index + 1) <= items.length - 1) {
                return items[nextFirst + index + 1];
            } else {
                return items[index - (items.length - 1 - nextFirst)];
            }
        } else {
            return items[index + 1];
        }
    }
}
