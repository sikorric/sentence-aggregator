package org.sa.io;

import org.sa.model.Sentence;

public class XmlSentenceWriter implements SentenceWriter {
    public XmlSentenceWriter() {
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        System.out.println("<text>");
    }

    @Override
    public void write(Sentence sentence) {
        System.out.print("  <sentence>");
        for (String word : sentence.words()) {
            // TODO sanitize string for xml if needed
            System.out.print("    <word>" + word + "</word>");
        }
        System.out.println("  </sentence>");
    }

    @Override
    public void close() throws Exception {
        System.out.println("</text>");
    }
}
