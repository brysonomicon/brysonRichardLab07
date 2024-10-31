import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;

public class CountryLab
{


    CountryLab()
    {

    }

    public static void main(final String[] args)
    {
        try
        {
            directoryCreation();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            dataFileCreation();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void directoryCreation()
            throws IOException
    {
        final Path matches;
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

    public static void dataFileCreation()
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
    }

    private void loadFiles()
            throws FileNotFoundException
    {
        final File file;
        final Scanner sc;

        file = new File("week8countries.txt");

        try(final Scanner scan = new Scanner(file))
        {
            while(scan.hasNextLine())
            {
                final String line;

                line = scan.nextLine();


            }
        }
        catch(final FileNotFoundException e)
        {
            throw new FileNotFoundException("File not found: " + e.getMessage());
        }
    }
}
