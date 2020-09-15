package stream;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeveloperUtils {

    public static Set<String> getDevelopersWithUniqueLanguages(Stream<Developer> developerStream) {
        return developerStream.flatMap(developer -> developer.getLanguages().stream().
                collect(Collectors.toMap(Function.identity(), e -> developer.getName())).entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .values().stream().filter(strings -> strings.size() == 1).map(strings -> strings.get(0)).collect(Collectors.toSet());
    }
}
