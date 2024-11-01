import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CountryLab
{
    private final List<String> countries;

    public CountryLab()
            throws IOException
    {
        final Path source;

        directoryCreation();
        dataFileCreation();

        source    = getSourcePath();
        countries = createList(source);
    }

    private static Path getSourcePath()
    {
        final Path source;

        source = Paths.get("week8countries.txt");

        return source;
    }

    public static void main(final String[] args)
    {
        try
        {
            final CountryLab lab;

            lab = new CountryLab();

            lab.countries.forEach(System.out::println);
        }
        catch (IOException e)
        {
            System.err.println("Uh oh! Something went wrong creating CountryLab: " + e.getMessage());
        }
    }

    public static void directoryCreation()
            throws IOException
    {
        final Path matchesDir;

        matchesDir = Paths.get("matches");

        if (Files.notExists(matchesDir))
        {
            Files.createDirectories(matchesDir);
            System.out.println("Folder created");
        }
        else
        {
            System.out.println("Directory already exists");
        }
    }

    public static void dataFileCreation()
            throws IOException
    {
        final Path dataFile;

        dataFile = Paths.get("matches", "data.txt");

        if (Files.notExists(dataFile))
        {
            Files.createFile(dataFile);
            System.out.println("File created successfully");
        }
        else
        {
            System.out.println("File already exists");
        }
    }

    public static List<String> createList(final Path sourceFile)
            throws IOException
    {
        return Files.readAllLines(sourceFile);
    }
}
