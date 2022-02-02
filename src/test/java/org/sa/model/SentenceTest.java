package org.sa.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SentenceTest {
    @Test
    void shouldSplitWordsDelimitedWithComma() {
        assertArrayEquals(List.of("a", "b").toArray(), new Sentence("a, b").words());
    }

    @Test
    void shouldSortSentenceWordsIgnoreCase() {
        assertArrayEquals(List.of("a", "A", "b", "B").toArray(), new Sentence("a B A b").words());
    }

    @Test
    void shouldCreateOneWordSentence() {
        assertArrayEquals(List.of("a").toArray(), new Sentence("a").words());
    }

    @Test
    void shouldCreateEmptySentence() {
        assertEquals(0, new Sentence("").words().length);
        assertEquals(0, new Sentence(" \t\r\n").words().length);
    }

    @Test
    void shouldFailOnNullSentence() {
        assertThrows(NullPointerException.class, () -> new Sentence(null));
    }
}