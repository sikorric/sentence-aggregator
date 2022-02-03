package org.sa.io;

import org.sa.model.Sentence;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * XML Sentence writer. Serialize sentences to XML data format.
 * <p>
 * UTF-8 encoding of the output text is assumed.
 */
public class XmlSentenceWriter implements SentenceWriter {
    private final XMLStreamWriter writer;

    public XmlSentenceWriter(OutputStream output) {
        try {
            writer = XMLOutputFactory.newDefaultFactory()
                    .createXMLStreamWriter(new BufferedOutputStream(output), StandardCharsets.UTF_8.name());
            writer.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");
            writer.writeCharacters("\n");
            writer.writeStartElement("text");
            writer.writeCharacters("\n");
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(Sentence sentence) {
        try {
            writer.writeStartElement("sentence");
            for (String word : sentence.words()) {
                writer.writeStartElement("word");
                writer.writeCharacters(word);
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeCharacters("\n");
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}
