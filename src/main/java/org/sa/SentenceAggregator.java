package org.sa;

import org.sa.io.CsvSentenceWriter;
import org.sa.io.SentenceScanner;
import org.sa.io.SentenceWriter;
import org.sa.io.XmlSentenceWriter;
import org.sa.model.Sentence;

import java.io.InputStream;

public class SentenceAggregator {
    private final SentenceScanner scanner;
    private final SentenceWriter writer;

    public static void main(String[] args) {
        if (args.length != 1 || (!"--xml".equals(args[0]) && !"--csv".equals(args[0]))) {
            System.err.println("""
                    usage: java -jar sentence-aggregator.jar --csv|--xml
                                
                    The application reads sentences delimited by [.?!] from the standard
                    input and writes ordered sentence words to the standard output.
                                
                    Options:
                      --xml  write output in XML format
                      --csv  write output in CSV format""");
            System.exit(1);
        }
        try (SentenceWriter writer = "--xml".equals(args[0])
                ? new XmlSentenceWriter(System.out)
                : new CsvSentenceWriter(System.out)) {
            new SentenceAggregator(System.in, writer).aggregate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SentenceAggregator(InputStream input, SentenceWriter writer) {
        this.scanner = new SentenceScanner(input);
        this.writer = writer;
    }

    public void aggregate() {
        scanner.stream()
                .map(Sentence::new)
                .forEach(writer::write);
    }
}
