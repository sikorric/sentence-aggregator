package org.sa.io;

import org.sa.model.Sentence;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * CSV Sentence writer. Serialize sentences to CSV data format.
 *
 * UTF-8 encoding of the output text is assumed.
 */
public class CsvSentenceWriter implements SentenceWriter {
    private final PrintWriter writer;
    private long counter = 0;

    public CsvSentenceWriter(OutputStream output) {
        writer = new PrintWriter(new BufferedOutputStream(output), true, StandardCharsets.UTF_8);
        writer.println(", Word 1, Word 2, Word 3, Word 4, Word 5, Word 6, Word 7, Word 8, Word 9, Word 10, Word 11, Word 12, Word 13, Word 14, Word 15, Word 16, Word 17, Word 18, Word 19, Word 20, Word 21, Word 22, Word 23, Word 24, Word 25, Word 26, Word 27, Word 28, Word 29, Word 30, Word 31, Word 32, Word 33");
    }

    @Override
    public void write(Sentence sentence) {
        writer.println("Sentence " + ++counter + ", " + String.join(", ", sentence.words()));
    }

    @Override
    public void close() {
        writer.close();
    }
}
