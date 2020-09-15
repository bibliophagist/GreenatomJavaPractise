package fisrt.tasks.serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeToFile {

    private final ArrayList<Human> humans = new ArrayList<>();

    public void serializeToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("humans serialized");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(humans);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Human> deserializeFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("humans serialized");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            List<Human> readHumans = (List<Human>) objectInputStream.readObject();
            for (Human readHuman : readHumans) {
                setOccupation(readHuman);
            }
            return readHumans;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setOccupation(Human human) {
        human.setOccupation(Occupation.getOccupation(human.getAge()));
    }

    public void addHuman(Human human) {
        humans.add(human);
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }
}
