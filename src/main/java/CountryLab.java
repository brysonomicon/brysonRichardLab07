import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

            System.out.println("CountryLab object created");
        }
        catch(final IOException e)
        {
            throw new RuntimeException("Uh oh! Something went wrong creating CountryLab.", e);
        }


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
                writer.write("Country names longer than 10 characters:" + System.lineSeparator());
                for(final String country : longNames)
                {
                    writer.write(country + System.lineSeparator());
                }
            }
        };

        try
        {
            writeLongNames.write(dataFile);
            System.out.println("Countries with names longer than 10 written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing data file." + e.getMessage());
        }


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
                writer.write("Country names starting with 'A':" + System.lineSeparator());
                for(final String country : countriesWithA)
                {
                    writer.write(country + System.lineSeparator());
                }
            }
        };

        try
        {
            writeACountries.write(dataFile);
            System.out.println("Countries that start with 'A' written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing data file." + e.getMessage());
        }


        // Countries with names shorter than 5 characters

        final List<String> shortCountries;
        final Writeable    writeShortCountries;

        shortCountries = lab.countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .filter(c -> c.length() < 5)
                .toList();

        writeShortCountries = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country names starting with 'A':" + System.lineSeparator());
                for(final String country : shortCountries)
                {
                    writer.write(country + System.lineSeparator());
                }
            }
        };

        try
        {
            writeShortCountries.write(dataFile);
            System.out.println("Countries with names shorter than 5 written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing data file." + e.getMessage());
        }


        // Countries that end with "land"

        final List<String> endsWithLand;
        final Writeable    writeEndsWithLand;

        endsWithLand = lab.countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .filter(c -> c.substring(c.length() - 4).toLowerCase().contains("land"))
                .toList();

        writeEndsWithLand = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country names ending with 'land':" + System.lineSeparator());
                for(final String country : endsWithLand)
                {
                    writer.write(country + System.lineSeparator());
                }
            }
        };

        try
        {
            writeEndsWithLand.write(dataFile);
            System.out.println("Countries that end with 'land' written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing data file." + e.getMessage());
        }


        // Countries containing "United"

        final List<String> containsUnited;
        final Writeable    writeUnited;

        containsUnited = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .filter(c -> c.contains("United"))
                .toList();

        writeUnited = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath,
                                                                      StandardOpenOption.CREATE,
                                                                      StandardOpenOption.APPEND))
            {
                writer.write("Country names starting with 'United':" + System.lineSeparator());
                for(final String country : containsUnited)
                {
                    writer.write(country + System.lineSeparator());
                }
            }
        };

        try
        {
            writeUnited.write(dataFile);
            System.out.println("Countries that start with 'United' written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing countries containing \"United\" to data file." + e.getMessage());
        }


        // Countries sorted in ascending order

        final List<String> ascCountries;
        final Writeable    writeAscCountries;

        ascCountries = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .sorted(Comparator.comparing(String::length))
                .toList();

        writeAscCountries = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country names in Ascending Order:" + System.lineSeparator());
                for(final String country : ascCountries)
                {
                    writer.write(country + System.lineSeparator());
                }
            }
        };

        try
        {
            writeAscCountries.write(dataFile);
            System.out.println("List of Countries in ascending order written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing ascending order into data file." + e.getMessage());
        }


        // Countries sorted in descending order

        final List<String> descCountries;
        final Writeable    writeDescCountries;

        descCountries = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .sorted(Comparator.comparing(String::length).reversed())
                .toList();

        writeDescCountries = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country names in Descending Order:" + System.lineSeparator());
                for(final String country : descCountries)
                    writer.write(country + System.lineSeparator());
            }
        };

        try
        {
            writeDescCountries.write(dataFile);
            System.out.println("List of Countries in descending order written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing descending order to data file." + e.getMessage());
        }


        // Countries with unique first letters

        final Map<Character, List<String>> uniqueFirstLetterCountries;
        final Writeable                    writeUniqueFirstLetterCountries;

        uniqueFirstLetterCountries = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .collect(Collectors.groupingBy(c -> Character.toUpperCase(c.charAt(0))));

       writeUniqueFirstLetterCountries = filePath -> {
           try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
           {
               writer.write("Country names by unique First Letter:" + System.lineSeparator());

               final List<Map.Entry<Character, List<String>>> entries;

               entries = new ArrayList<>(uniqueFirstLetterCountries.entrySet());

               for(final Map.Entry<Character, List<String>> entry: entries)
               {
                   final Character firstLetter;
                   final List<String> groupOfCountries;

                   firstLetter      = entry.getKey();
                   groupOfCountries = entry.getValue();

                   writer.write(firstLetter + System.lineSeparator());
                   groupOfCountries.sort(String::compareTo);

                   for(final String country : groupOfCountries)
                   {
                       writer.write(country + System.lineSeparator());
                   }
               }
           }
       };

       try
       {
           writeUniqueFirstLetterCountries.write(dataFile);
           System.out.println("Countries with unique first letters written to file");
       }
       catch(final IOException e)
       {
           System.err.println("Something went wrong writing unique first letter countries to data file." + e.getMessage());
       }


        // Count of all countries

        final long         countryCount;
        final Writeable    writeCountryCount;

        countryCount = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .count();

        writeCountryCount = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country count: " + countryCount + System.lineSeparator());
            }
        };

        try
        {
            writeCountryCount.write(dataFile);
            System.out.println("Count of all countries written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing country count to data file." + e.getMessage());
        }


        // Longest country name

        final Optional<String> longestCountry;
        final Writeable        writeLongestCountry;

        longestCountry = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .max(Comparator.comparingInt(String::length));

        writeLongestCountry = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Longest country name:" + System.lineSeparator());
                writer.write(longestCountry.orElse("No valid country names found.") + System.lineSeparator());
            }
        };

        try
        {
            writeLongestCountry.write(dataFile);
            System.out.println("Longest country written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing the longest country name: " + e.getMessage());
        }

        // Shortest country name

        final Optional<String> shortestCountry;
        final Writeable        writeShortestCountry;

        shortestCountry = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .min(Comparator.comparingInt(String::length));

        writeShortestCountry = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Shortest country name:" + System.lineSeparator());
                writer.write(shortestCountry.orElse("No valid country names found.") + System.lineSeparator());
            }
        };

        try
        {
            writeShortestCountry.write(dataFile);
            System.out.println("Shortest country written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing the shortest country name: " + e.getMessage());
        }

        // Country names printed in UPPERCASE

        final List<String> upperCaseCountries;
        final Writeable    writeUpperCaseCountries;

        upperCaseCountries = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .map(String::toUpperCase)
                .toList();

        writeUpperCaseCountries = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country names in uppercase:" + System.lineSeparator());
                for(final String country : upperCaseCountries)
                {
                    writer.write(country + System.lineSeparator());
                }
            }
        };

        try
        {
            writeUpperCaseCountries.write(dataFile);
            System.out.println("Uppercase countries written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing the uppercase country names: " + e.getMessage());
        }

        // Countries with more than one word in the name

        final List<String> multiWordCountries;
        final Writeable    writeMultiWordCountries;

        multiWordCountries = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .filter(c -> c.trim().contains(" "))
                .toList();

        writeMultiWordCountries = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country names with more than one word:" + System.lineSeparator());
                if(multiWordCountries.isEmpty())
                {
                    writer.write("No country names with more than one word found." + System.lineSeparator());
                }
                else
                {
                    for(final String country : multiWordCountries)
                    {
                        writer.write(country + System.lineSeparator());
                    }
                }
            }
        };

        try
        {
            writeMultiWordCountries.write(dataFile);
            System.out.println("Multi-word countries written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing the country names with more than one word: " + e.getMessage());
        }

        // Country names mapped to character counts - Country: N characters

        final List<String> countryCharacterCounts;
        final Writeable    writeCountryCharacterCounts;

        countryCharacterCounts = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .map(c -> c + ": " + c.length() + " characters")
                .toList();

        writeCountryCharacterCounts = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Country names with their character counts:" + System.lineSeparator());

                if(countryCharacterCounts.isEmpty())
                {
                    writer.write("No valid country names found." + System.lineSeparator());
                }
                else
                {
                    for(final String entry : countryCharacterCounts)
                    {
                        writer.write(entry + System.lineSeparator());
                    }
                }
            }
        };

        try
        {
            writeCountryCharacterCounts.write(dataFile);
            System.out.println("Countries mapped to character count written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing the country names with character counts: " + e.getMessage());
        }

        // Boolean outputs true if any country starts with "Z", false if not

        final boolean   anyStartsWithZ;
        final Writeable writeAnyStartsWithZ;

        anyStartsWithZ = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .anyMatch(c -> c.trim().startsWith("Z"));

        writeAnyStartsWithZ = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("Any country name starts with 'Z':" + System.lineSeparator());
                writer.write(anyStartsWithZ + System.lineSeparator());
            }
        };

        try
        {
            writeAnyStartsWithZ.write(dataFile);
            System.out.println("Boolean of whether a country starts with Z written to file");
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing the result for names starting with 'Z': " + e.getMessage());
        }

        // Boolean output true if all country names have length > 3

        final boolean   allNamesLongerThanThree;
        final Writeable writeAllNamesLongerThanThree;

        allNamesLongerThanThree = lab.getCountries().stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .allMatch(c -> c.trim().length() > 3);

        writeAllNamesLongerThanThree = filePath -> {
            try(final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            {
                writer.write("All country names longer than 3 characters:" + System.lineSeparator());
                writer.write(allNamesLongerThanThree + System.lineSeparator());
            }
        };

        try
        {
            writeAllNamesLongerThanThree.write(dataFile);
        }
        catch(final IOException e)
        {
            System.err.println("Something went wrong writing the result for all names longer than 3 characters: " + e.getMessage());
        }
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

        if(Files.notExists(matchesDir))
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

        if(Files.notExists(dataFile))
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
