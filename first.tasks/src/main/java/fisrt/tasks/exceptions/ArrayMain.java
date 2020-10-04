package fisrt.tasks.exceptions;

import java.util.ArrayList;

public class ArrayMain {
    public static void main(String[] args) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            try {
                addToArray(integerArrayList, i);
            } catch (ArrayIsFull arrayIsFull) {
                arrayIsFull.printStackTrace();
            }
        }
    }

    private static <V> void addToArray(ArrayList<V> arrayList, V element) throws ArrayIsFull {
        if (arrayList.size() >= 10) {
            throw new ArrayIsFull("Trying to add element number " + (arrayList.size() + 1));
        } else {
            arrayList.add(element);
        }
    }
}
