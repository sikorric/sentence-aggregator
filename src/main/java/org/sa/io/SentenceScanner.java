package org.sa.io;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Sentence scanner reads input text and splits it into sentences by standard
 * sentence delimiters [.!?]. Standard abbreviations as /(Mr.|Ms.|Mrs.)/ are honored,
 * the full stop is not considered as a delimiter in this case.
 *
 * Implementation relies on java.util.Scanner.
 *
 * UTF-8 encoding of the input text is assumed.
 */
public final class SentenceScanner implements AutoCloseable {
    private static final Pattern SENTENCE_DELIMITER = Pattern.compile("(?<!Mr|Ms|Mrs)[.!?]");
    private final Scanner scanner;

    public SentenceScanner(InputStream stream) {
        Objects.requireNonNull(stream, "input stream must not be null");
        scanner = new Scanner(stream, StandardCharsets.UTF_8.name()).useDelimiter(SENTENCE_DELIMITER);
    }

    @Override
    public void close() {
        scanner.close();
    }

    /**
     * @return sequential Stream of sentence strings
     */
    public Stream<String> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(scanner, Spliterator.ORDERED), false)
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty));
    }
}
