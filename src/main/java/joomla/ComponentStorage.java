package joomla;

import lombok.Getter;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ComponentStorage {

    @Getter
    private List<String> components = new ArrayList<>();
    @Getter
    private int count;

    @SneakyThrows
    public void feedComponents() {
        Path path = Paths.get("src/main/resources/joomla-components.txt");

        Stream<String> lines = Files.lines(path);
        lines.forEach(str -> components.add(str));
        lines.close();

        count = components.size();
    }

}
