package Logic.StackBooks;

import java.io.IOException;

public interface StackBooks {
    public void code(String data, String archivedFile) throws Exception;
    public void decode(String archiveStackBooks, String data) throws Exception;
}
