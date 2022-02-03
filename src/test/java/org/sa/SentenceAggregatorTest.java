package org.sa;

import org.junit.jupiter.api.Test;
import org.sa.io.CsvSentenceWriter;
import org.sa.io.SentenceWriter;
import org.sa.io.XmlSentenceWriter;
import org.sa.model.Sentence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceAggregatorTest {

    @Test
    void shouldWriteSentencesToCsv() throws Exception {
        final var csvSentences = new ByteArrayOutputStream();
        aggregateSentences("""
                Mary had a little lamb. Peter called for the wolf, and Aesop came.
                Cinderella likes shoes.""", new CsvSentenceWriter(csvSentences));
        assertEquals("""
                        , Word 1, Word 2, Word 3, Word 4, Word 5, Word 6, Word 7, Word 8, Word 9, Word 10, Word 11, Word 12, Word 13, Word 14, Word 15, Word 16, Word 17, Word 18, Word 19, Word 20, Word 21, Word 22, Word 23, Word 24, Word 25, Word 26, Word 27, Word 28, Word 29, Word 30, Word 31, Word 32, Word 33
                        Sentence 1, a, had, lamb, little, Mary
                        Sentence 2, Aesop, and, called, came, for, Peter, the, wolf
                        Sentence 3, Cinderella, likes, shoes
                        """.replaceAll("\n", "\r\n"),
                csvSentences.toString(StandardCharsets.UTF_8)
        );
    }

    @Test
    void shouldWriteSentencesToXml() throws Exception {
        final var xmlSentences = new ByteArrayOutputStream();
        aggregateSentences("""
                Mary had a little lamb. Peter called for the wolf, and Aesop came.
                Cinderella likes shoes.""", new XmlSentenceWriter(xmlSentences));
        assertEquals("""
                        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                        <text>
                        <sentence><word>a</word><word>had</word><word>lamb</word><word>little</word><word>Mary</word></sentence>
                        <sentence><word>Aesop</word><word>and</word><word>called</word><word>came</word><word>for</word><word>Peter</word><word>the</word><word>wolf</word></sentence>
                        <sentence><word>Cinderella</word><word>likes</word><word>shoes</word></sentence>
                        </text>
                        """.replaceAll("\n", "\r\n"),
                xmlSentences.toString(StandardCharsets.UTF_8)
        );
    }

    @Test
    void shouldIgnoreWhitespace() throws Exception {
        assertEquals(
                sentencesOf("""
                          Mary   had a little  lamb  .
                                               
                                               
                        Peter   called for the wolf   ,  and Aesop came .
                        Cinderella  likes shoes.."""),
                sentencesOf("""
                        Mary had a little lamb. Peter called for the wolf, and Aesop came.
                        Cinderella likes shoes.""")
        );
    }

    private static List<Sentence> sentencesOf(String text) throws Exception {
        final var writer = new CollectingSentenceWriter();
        aggregateSentences(text, writer);
        return writer.sentences();
    }

    private static void aggregateSentences(String text, SentenceWriter writer) throws Exception {
        new SentenceAggregator(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), writer)
                .aggregate();
        writer.close();
    }

    private static class CollectingSentenceWriter implements SentenceWriter {
        private final List<Sentence> sentences = new ArrayList<>();

        @Override
        public void write(Sentence sentence) {
            sentences.add(sentence);
        }

        @Override
        public void close() throws Exception {
        }

        public List<Sentence> sentences() {
            return Collections.unmodifiableList(sentences);
        }
    }
}