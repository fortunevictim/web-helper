package ru.nsu.fit.pupynin.webhelper.JenaWork.utilities;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class PathUtility {

    public static Path resource(String first, String... parts) {
        try {
            String path = first + "/" + String.join("/", parts);
            URL resource = Objects.requireNonNull(PathUtility.class.getResource(path));
            System.out.println(Path.of(resource.toURI()));
            return Path.of(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path newResource(String first, String... parts) {
        try {
            URL resource = Objects.requireNonNull(PathUtility.class.getResource("/"));
            Path resources = Objects.requireNonNull(Path.of(resource.toURI()));
            Path child = Path.of(first, parts);
            return resources.resolve(child);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path newResourceDir(String first, String... parts) {
        try {
            Path resourceDir = newResource(first, parts);
            Files.createDirectories(resourceDir);
            return resourceDir;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

