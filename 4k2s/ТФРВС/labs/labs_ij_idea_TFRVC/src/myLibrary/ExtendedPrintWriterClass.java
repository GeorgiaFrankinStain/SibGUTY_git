package myLibrary;

import java.io.PrintWriter;

public class ExtendedPrintWriterClass implements ExtendedPrintWriter {
    private PrintWriter output;

    public ExtendedPrintWriterClass(PrintWriter output, String titleString) {
        output.println(titleString);
        this.output = output;
    }

    public ExtendedPrintWriterClass(PrintWriter output) {
        this.output = output;
    }

    @Override
    public void println(Print bodyPrint) {
        bodyPrint.print();
        output.println();
    }
}
