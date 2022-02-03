package org.sa.io;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SentenceScannerTest {
    @Test
    void shouldReturnSentences() {
        assertEquals(List.of("a"), listOf(scannerFor("a")));
        assertEquals(List.of("a", "b", "c", "d"), listOf(scannerFor("a.b?c!d.")));
    }

    @Test
    void shouldSkipEmptySentences() {
        assertTrue(listOf(scannerFor("")).isEmpty());
        assertTrue(listOf(scannerFor(".")).isEmpty());
        assertTrue(listOf(scannerFor("..")).isEmpty());
    }

    @Test
    void shouldRecognizeCommonAbbreviations() {
        assertEquals(1, scannerFor("Mr. Ms. Mrs.").stream().count());
    }

    @Test
    void shouldScanSampleFile() throws FileNotFoundException {
        final var scanner = new SentenceScanner(new FileInputStream(Path.of("src/test/resources/sample-files/small.in").toFile()));
        assertEquals(13, scanner.stream().count());
    }

    @Test
    void shouldFailOnNullInputStream() {
        assertThrows(NullPointerException.class, () -> new SentenceScanner(null));
    }

    private static SentenceScanner scannerFor(String text) {
        return new SentenceScanner(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
    }

    private static List<String> listOf(SentenceScanner scanner) {
        return scanner.stream().collect(Collectors.toList());
    }
}
