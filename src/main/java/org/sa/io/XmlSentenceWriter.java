package org.sa.io;

import org.sa.model.Sentence;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class XmlSentenceWriter implements SentenceWriter {
    private final PrintWriter writer;

    public XmlSentenceWriter(OutputStream output) {
        writer = new PrintWriter(new BufferedOutputStream(output));
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        writer.println("<text>");
    }

    @Override
    public void write(Sentence sentence) {
        writer.print("<sentence>");
        for (String word : sentence.words()) {
            // TODO sanitize string for xml if needed
            writer.print("<word>" + word + "</word>");
        }
        writer.println("</sentence>");
    }

    @Override
    public void close() throws Exception {
        writer.println("</text>");
    }
}
