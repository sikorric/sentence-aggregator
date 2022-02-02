package org.sa.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public final class Sentence {
    private final String[] words;

    public Sentence(final String sentence) {
        Objects.requireNonNull(sentence, "sentence can not be null");
        words =  Arrays.stream(sentence.split("\\s+"))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .sorted(String::compareToIgnoreCase)
                .toArray(String[]::new);
    }

    public String[] words() {
        return Arrays.copyOf(words, words.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Arrays.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(words);
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "words=" + Arrays.toString(words) +
                '}';
    }
}
