package org.sa.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class SentenceScanner implements AutoCloseable {
    private static final Pattern SENTENCE_DELIMITER = Pattern.compile("(?<!Mr|Ms|Mrs)[.!?]");
    private final Scanner scanner;

    public SentenceScanner(InputStream stream) {
        Objects.requireNonNull(stream, "input stream must not be null");
        scanner = new Scanner(stream, StandardCharsets.UTF_8.name()).useDelimiter(SENTENCE_DELIMITER);
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }

    public Stream<String> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(scanner, Spliterator.ORDERED), false)
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty));
    }
}
