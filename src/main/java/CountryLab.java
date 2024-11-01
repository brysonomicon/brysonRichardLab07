import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

/**
 * The CountryLab class satisfies all the requirements of the week 8 lab for
 * comp 2522.
 *
 * @author Bryson Lindy
 * @author Richard Ho
 * @author Maksim Sadreev
 */
class CountryLab
{
    private final List<String> countries;

    /**
     * Constructs a new CountryLab instance, initializing the list of countries
     * and setting up the necessary directories and data files.
     *
     * @throws IOException if an I/O error occurs during file or directory creation.
     */
    CountryLab()
            throws IOException
    {
        final Path source;

        directoryCreation();
        dataFileCreation();

        source    = getSourcePath();
        countries = createList(source);
    }

    /**
     * Main method to run the CountryLab program. Initializes the CountryLab instance,
     * filters the countries based on various criteria, and writes the results to a file.
     *
     * @param args Command-line arguments (not used in this program).
     */
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

    /**
     * Gets the list of countries loaded from the source file.
     *
     * @return A List of country names.
     */
    public List<String> getCountries()
    {
        return countries;
    }

    /**
     * Creates the "matches" directory if it does not already exist.
     *
     * @throws IOException if an I/O error occurs while creating the directory.
     */
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

    /**
     * Creates the "data.txt" file within the "matches" directory if it does not already exist.
     *
     * @throws IOException if an I/O error occurs while creating the file.
     */
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

    /**
     * Loads the list of country names from the specified source file.
     *
     * @param sourceFile The Path to the file containing the country names.
     * @return A List of country names read from the file.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    List<String> createList(final Path sourceFile)
            throws IOException
    {
        return Files.readAllLines(sourceFile);
    }
}
