package fisrt.tasks.collections;

import fisrt.tasks.collections.benchmark.TestObject;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ChangeKeysAndValues {

    public static void main(String[] args) {
        final HashMap<Long, TestObject> testHashMap = new HashMap<>();

        for (long i = 0; i < 100; i++) {
            TestObject testObject = new TestObject(i, "This is object number " + i);
            testHashMap.put(i, testObject);
        }
        System.out.println(testHashMap.toString());
        System.out.println();
        System.out.println(changeKeysAndValues(testHashMap).toString());
    }

    private static <K, V> Map<V, K> changeKeysAndValues(HashMap<K, V> hashMap) {
        return hashMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
