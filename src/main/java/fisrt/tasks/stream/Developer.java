package fisrt.tasks.stream;

import java.util.List;

public class Developer {
    private final String name;
    private final List<String> languages;

    public Developer(String name, List<String> languages) {
        this.name = name;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public List<String> getLanguages() {
        return languages;
    }
}
