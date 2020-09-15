package collections.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CollectionsTest {

    private static long ELEMENTS = 1000;

    @State(Scope.Benchmark)
    public static class MyStateAdd {
        private ArrayList<TestObject> arrayListTest;
        private LinkedList<TestObject> linkedListTest;
        private HashSet<TestObject> hashSetTest;
        private TreeSet<TestObject> treeSetTest;

        private long id = 1001;

        @Setup(Level.Iteration)
        public void setup() {
            arrayListTest = new ArrayList<>();
            linkedListTest = new LinkedList<>();
            hashSetTest = new HashSet<>();
            treeSetTest = new TreeSet<>();

            addElementsToCollections(arrayListTest, linkedListTest, hashSetTest, treeSetTest);
        }
    }

    @State(Scope.Benchmark)
    public static class MyStateOperations {
        private ArrayList<TestObject> arrayListTest = new ArrayList<>();
        private LinkedList<TestObject> linkedListTest = new LinkedList<>();
        private HashSet<TestObject> hashSetTest = new HashSet<>();
        private TreeSet<TestObject> treeSetTest = new TreeSet<>();

        private Random random = new Random();
        private Long id = (long) random.nextInt(1000);
        private TestObject favoriteObject = new TestObject(id, "This is object number " + id);


        @Setup()
        public void setup() {
            addElementsToCollections(arrayListTest, linkedListTest, hashSetTest, treeSetTest);
        }
    }

    private static void addElementsToCollections(ArrayList<TestObject> arrayListTest, LinkedList<TestObject> linkedListTest, HashSet<TestObject> hashSetTest, TreeSet<TestObject> treeSetTest) {
        for (long i = 0; i < ELEMENTS; i++) {
            TestObject testObject = new TestObject(i, "This is object number " + i);
            arrayListTest.add(testObject);
            linkedListTest.add(testObject);
            hashSetTest.add(testObject);
            treeSetTest.add(testObject);
        }
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void addToArrayList(MyStateAdd myState) {
        myState.linkedListTest.add(new TestObject(myState.id, "This is object number " + myState.id));
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void addToLinkedList(MyStateAdd myState) {
        myState.linkedListTest.add(new TestObject(myState.id, "This is object number " + myState.id));
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void addToMiddleOfArrayList(MyStateAdd myState) {
        myState.linkedListTest.add(500, new TestObject(myState.id, "This is object number " + myState.id));
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void addToMiddleOfLinkedList(MyStateAdd myState) {
        myState.linkedListTest.add(500, new TestObject(myState.id, "This is object number " + myState.id));
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void addToHashSet(MyStateAdd myState) {
        myState.linkedListTest.add(new TestObject(myState.id, "This is object number " + myState.id));
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void addToTreeSet(MyStateAdd myState) {
        myState.linkedListTest.add(new TestObject(myState.id, "This is object number " + myState.id));
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void findInArrayList(MyStateOperations myState) {
        myState.arrayListTest.indexOf(myState.favoriteObject);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void findInLinkedList(MyStateOperations myState) {
        myState.linkedListTest.indexOf(myState.favoriteObject);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public Boolean findInHashSet(MyStateOperations myState) {
        return myState.hashSetTest.contains(myState.favoriteObject);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public Boolean findInTreeSet(MyStateOperations myState) {
        return myState.treeSetTest.contains(myState.favoriteObject);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void deleteByElementFromArrayList(MyStateOperations myState) {
        myState.arrayListTest.remove(myState.favoriteObject);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void deleteByElementFromLinkedList(MyStateOperations myState) {
        myState.linkedListTest.remove(myState.favoriteObject);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public Boolean deleteByElementFromHashSet(MyStateOperations myState) {
        return myState.hashSetTest.remove(myState.favoriteObject);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public Boolean deleteByElementFromTreeSet(MyStateOperations myState) {
        return myState.treeSetTest.remove(myState.favoriteObject);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void deleteByIndexFromArrayList(MyStateOperations myState) {
        myState.arrayListTest.remove(myState.id.intValue());
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void deleteByIndexFromLinkedList(MyStateOperations myState) {
        myState.linkedListTest.remove(myState.id.intValue());
    }
}
