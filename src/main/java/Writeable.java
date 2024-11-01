import java.io.IOException;
import java.nio.file.Path;

public interface Writeable
{
    void write(final Path finalPath)
            throws IOException;
}
