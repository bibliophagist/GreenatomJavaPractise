package fisrt.tasks.stream;

import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Developer dev1 = new Developer("Наташа", Arrays.asList("Java", "C++"));
        Developer dev2 = new Developer("Эрнест", Arrays.asList("Java", "Python"));
        Developer dev3 = new Developer("Элла", Arrays.asList("С#", "Python", "JavaScript"));
        Stream<Developer> developerStream = Stream.of(dev1, dev2, dev3);

        System.out.println(DeveloperUtils.getDevelopersWithUniqueLanguages(developerStream).toString());

    }
}
