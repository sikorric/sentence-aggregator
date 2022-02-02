package org.sa.io;

import org.sa.model.Sentence;

public interface SentenceWriter extends AutoCloseable {
    void write(Sentence sentence);
}
