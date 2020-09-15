package serialization;

public class Main {
    public static void main(String[] args) {
        SerializeToFile serializeToFile = new SerializeToFile();
        for (int i = 0; i < 10; i++) {
            serializeToFile.addHuman(new Human("human" + i, 2 + i * 7));
        }
        serializeToFile.serializeToFile();
        for (Human human : serializeToFile.deserializeFromFile()) {
            System.out.println(human);
        }
    }
}
