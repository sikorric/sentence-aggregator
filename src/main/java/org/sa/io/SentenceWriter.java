package org.sa.io;

import org.sa.model.Sentence;

/**
 * Defines Sentence serialization writer.
 */
public interface SentenceWriter extends AutoCloseable {
    /**
     * @param sentence to be written to the output
     */
    void write(Sentence sentence);
}
