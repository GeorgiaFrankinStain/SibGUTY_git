package myLibrary;

import java.io.PrintWriter;

public interface ExtendedPrintWriter {
    public void println(Print bodyPrint);

    public interface Print {
        public void print();
    }
}
