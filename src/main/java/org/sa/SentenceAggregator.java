package org.sa;

import org.sa.io.CsvSentenceWriter;
import org.sa.io.SentenceScanner;
import org.sa.io.SentenceWriter;
import org.sa.io.XmlSentenceWriter;
import org.sa.model.Sentence;

public class SentenceAggregator {
    public static void main(String[] args) {
        if (args.length != 1 || (!"--xml".equals(args[0]) && !"--csv".equals(args[0]))) {
            System.out.println("usage: java -jar sentence-aggregator.jar --csv|--xml");
            System.out.println("\nThe application reads sentences delimited by [.?!] from the standard \n" +
                    "input and writes ordered sentence words to the standard output.");
            System.out.println("\nOptions:\n" +
                    "  --xml  write output in XML format\n" +
                    "  --csv  write output in CSV format");
            System.exit(1);
        }
        try (SentenceWriter writer = "--xml".equals(args[0])
                ? new XmlSentenceWriter()
                : new CsvSentenceWriter()) {

            new SentenceScanner(System.in).stream()
                    .map(Sentence::new)
                    .forEach(writer::write);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
