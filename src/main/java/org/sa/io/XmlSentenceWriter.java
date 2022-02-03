package org.sa.io;

import org.sa.model.Sentence;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static com.google.common.xml.XmlEscapers.xmlContentEscaper;

/**
 * XML Sentence writer. Serialize sentences to XML data format.
 *
 * UTF-8 encoding of the output text is assumed.
 */
public class XmlSentenceWriter implements SentenceWriter {
    private final PrintWriter writer;

    public XmlSentenceWriter(OutputStream output) {
        writer = new PrintWriter(new BufferedOutputStream(output), true, StandardCharsets.UTF_8);
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        writer.println("<text>");
    }

    @Override
    public void write(Sentence sentence) {
        writer.print("<sentence>");
        for (String word : sentence.words()) {
            writer.print("<word>" + xmlContentEscaper().escape(word) + "</word>");
        }
        writer.println("</sentence>");
    }

    @Override
    public void close() {
        writer.println("</text>");
        writer.close();
    }
}
