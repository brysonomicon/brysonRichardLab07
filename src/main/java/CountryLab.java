import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

class CountryLab
{
    private final List<String> countries;

    CountryLab()
            throws IOException
    {
        final Path source;

        directoryCreation();
        dataFileCreation();

        source    = getSourcePath();
        countries = createList(source);
    }

    public static void main(final String[] args)
    {
        final CountryLab lab;
        final Path dataFile;

        dataFile = Paths.get("matches", "data.txt");

        try
        {
            lab = new CountryLab();

            lab.countries.forEach(System.out::println);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Uh oh! Something went wrong creating CountryLab.", e);
        }

        System.out.println("-------------------------------------");

        // Countries with names longer than 10 characters

        final List<String> longNames;
        final Writeable    writeLongNames;

        longNames = lab.countries.stream()
                        .filter(Objects::nonNull)
                        .filter(c -> !c.isBlank())
                        .filter(c -> c.length() > 10)
                        .toList();

        writeLongNames = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country names longer than 10 characters:\n");
                for(final String country : longNames)
                {
                    writer.write(country + System.lineSeparator());
                }
            }
        };

        try
        {
            writeLongNames.write(dataFile);
        }
        catch(IOException e)
        {
            System.err.println("Something went wrong writing data file." + e.getMessage());
        }

        System.out.println("-------------------------------------");

        // Countries that start with the letter A

        final List<String> countriesWithA;
        final Writeable    writeACountries;

        countriesWithA = lab.getCountries().stream()
                        .filter(Objects::nonNull)
                        .filter(c -> !c.isBlank())
                        .filter(c -> c.toUpperCase().contains("A"))
                        .toList();

        writeACountries = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country names starting with 'A':\n");
                for(final String country : countriesWithA)
                {
                    writer.write(country + System.lineSeparator());
                }
            }
        };

        try
        {
            writeACountries.write(dataFile);
        }
        catch(IOException e)
        {
            System.err.println("Something went wrong writing data file." + e.getMessage());
        }

        System.out.println("-------------------------------------");

        // Countries with names shorter than 5 characters

        System.out.println("-------------------------------------");

        // Countries that end with "land"

        System.out.println("-------------------------------------");

        // Countries containing "United"

        System.out.println("-------------------------------------");

        // Countries sorted in ascending order

        System.out.println("-------------------------------------");

        // Countries sorted in descending order

        System.out.println("-------------------------------------");

        // Countries with unique first letters

        System.out.println("-------------------------------------");

        // Count of all countries

        System.out.println("-------------------------------------");

        // Longest country name

        System.out.println("-------------------------------------");

        // Shortest country name

        System.out.println("-------------------------------------");

        // Country names printed in UPPERCASE

        System.out.println("-------------------------------------");

        // Countries with more than one word in the name

        System.out.println("-------------------------------------");

        // Country names mapped to character counts - Country: N characters

        System.out.println("-------------------------------------");

        // Boolean outputs true if any country starts with "Z", false if not

        System.out.println("-------------------------------------");

        // Boolean output true if all country names have length > 3

        System.out.println("-------------------------------------");

    }

    private static Path getSourcePath()
    {
        final Path source;

        source = Paths.get("week8countries.txt");

        return source;
    }

    public List<String> getCountries()
    {
        return countries;
    }

    void directoryCreation()
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

    void dataFileCreation()
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

    public List<String> createList(final Path sourceFile)
            throws IOException
    {
        return Files.readAllLines(sourceFile);
    }
}
