import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CountryLab
{
    private static final List<String> countries;

    static
    {
        try
        {
            final Path source;

            directoryCreation();
            dataFileCreation();
            source = Paths.get("week8countries.txt");
            countries = createList(source);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to initialize countries list", e);
        }
    }

    public static void main(final String[] args)
    {
        countries.forEach(System.out::println);
    }

    public static void directoryCreation()
            throws IOException
    {
        final Path   matches;
        final String folderName;

        folderName = "matches";

        matches = Paths.get(folderName);

        if(Files.exists(matches))
        {
            System.out.println("Directory already exists");
        }
        else
        {
            System.out.println("Folder created");
            Files.createDirectories(matches);
        }
    }

    public static Path dataFileCreation()
            throws IOException
    {
        final Path matchesDir;
        final Path dataFilePath;

        matchesDir   = Paths.get("matches");
        dataFilePath = matchesDir.resolve("data.txt");

        if(Files.notExists(dataFilePath))
        {
            Files.createFile(dataFilePath);
            System.out.println("File created successfully");
        }
        else
        {
            System.out.println("File already exists");
        }

        return dataFilePath;
    }

    static List<String> createList(final Path sourceFile) 
            throws IOException
    {
        final List<String> countryList;
        
        countryList = Files.readAllLines(sourceFile);
        
        return countryList;
    }
}
