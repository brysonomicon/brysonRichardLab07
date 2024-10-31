import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Canada
{
    private static List<Province> provinces;
    public static final int MAX_PERCENTAGE;

    static
    {
        provinces = new ArrayList<>();
        MAX_PERCENTAGE = 100;
    }

    public static void main(final String[] args)
    {
        provinces.add(new Province("British Columbia", 5466646));
        provinces.add(new Province("Alberta", 4645229));
        provinces.add(new Province("Saskatchewan", 1200540));
        provinces.add(new Province("Manitoba", 1443875));
        provinces.add(new Province("  ", 2000000));
        provinces.add(new Province("zzz", 1000000));
        provinces.add(new Province("Ontario", 15457075));
        provinces.add(null);
        provinces.add(new Province("Best province ever", 1000000));
        provinces.add(new Province("Quebec", 8814007));
        provinces.add(new Province("defacb", 8814007));
        provinces.add(new Province("New Brunswick", 826622));
        provinces.add(new Province("abc def", 8814007));
        provinces.add(new Province("saame", 1000000));
        provinces.add(new Province("same letter", 2000000));
        provinces.add(new Province("Kayak", 1000000));
        provinces.add(new Province("Ra cecar", 1000000));
        provinces.add(new Province("Nova Scotia", 1047803));
        provinces.add(new Province("primey", 13));
        provinces.add(new Province(null, 5000000));
        provinces.add(new Province("Prince Edward Island", 171790));
        provinces.add(new Province("Newfoundland and Labrador", 536291));
        provinces.add(new Province("villejason", 536291));
        provinces.add(new Province("jason ville", 536291));
        provinces.add(new Province("jasville  on", 536291));
        provinces.add(new Province("primey too", 17));
        provinces.add(new Province("Yukon", 44596));
        provinces.add(new Province("Northwest Territories", 44678));
        provinces.add(new Province("Nunavut", 40481));

        System.out.println("-------------------------");
        System.out.println("all provinces with their populations in alphabetical order");
        final List<Province> allExceptNull = provinces.stream()
                .filter(p->p != null)
                .filter(p->p.getName() != null && !p.getName().isBlank())
                .sorted(Comparator.comparing((Province p)->p.getName()))
                .toList();
        allExceptNull.forEach(System.out::println);
        System.out.println("-------------------------");

        System.out.println("all provinces AGAIN with their populations in alphabetical order DESC");
        final List<Province> all = filteredStream(provinces)
                .filter(p->!p.getName().isBlank())
                .sorted(Comparator.comparing(Province::getName).reversed())
                .toList();
        all.forEach(System.out::println);
        System.out.println("-------------------------");

        System.out.println("same thing but this time names in UPPERCASE");
        final List<Province> upper = filteredStream(provinces)
                .filter(p->!p.getName().isBlank())
                .sorted(Comparator.comparing(Province::getName))
                .map(p->new Province(p.getName().toUpperCase(), p.getPopulation()))
                .toList();
        upper.forEach(System.out::println);

        System.out.println("-------------------------");
        System.out.println("combined populations of all provinces whose names betgins with vowel");
        final Optional<Integer> combinedPopulation;
        combinedPopulation = filteredStream(provinces)
                .filter(p->p.getName().matches("^[AEIOUaeiou].*"))
                .map(Province::getPopulation)
                .reduce(Integer::sum);
        combinedPopulation.ifPresent(total-> System.out.printf("Total pop is %d\n", total));


        System.out.println("-------------------------");
        System.out.println("total pop of canada");
        final int total;
        total = filteredStream(provinces)
                .mapToInt(Province::getPopulation)
                .sum();
        System.out.println(total);


        System.out.println("-------------------------");
        System.out.println("provinces, sorted by population, with fewer than 250000");
        final List<Province> small = filteredStream(provinces)
                .filter(p->p.getPopulation() < 250000)
                .sorted(Comparator.comparing(Province::getPopulation))
                .toList();
        System.out.println(small);
        System.out.println(small.size());


        System.out.println("-------------------------");
        System.out.println("provinces, sorted by name, with at least 250000");
        final List<Province> big = filteredStream(provinces)
                .filter(p->p.getPopulation() >= 250000)
                .sorted(Comparator.comparing(Province::getName))
                .toList();
        System.out.println(big);
        System.out.println(big.size());


        System.out.println("-------------------------");
        System.out.println("percentage of provinces that are big");
        final double percent;
        percent = 1.0 * big.size() / all.size() * MAX_PERCENTAGE;
        System.out.printf("%% of big provinces is %.1f%%\n", percent);


        System.out.println("-------------------------");
        System.out.println("provinces with 1M+ sorted by pop in reverse population order");
        final List<Province> millionPlus = provinces.stream()
                .filter(p->p != null)
                .filter(p->p.getName() != null)
                .filter(p->!p.getName().isBlank())
                .filter(p->p.getPopulation() >= 1000000)
                .sorted(Comparator.comparing(Province::getPopulation)) // or p->p.getPopulation()
                .toList()
                .reversed();
        millionPlus.forEach(System.out::println);


        System.out.println("-------------------------");
        System.out.println("largest pop");
        filteredStream(provinces)
                .max(Comparator.comparing(Province::getPopulation))
                .ifPresent(largestProv-> System.out.println("largest is " + largestProv));


        System.out.println("-------------------------");
        System.out.println("smallest pop");
        filteredStream(provinces)
                .min(Comparator.comparing(Province::getPopulation))
                .ifPresent(smallest-> System.out.println("largest is " + smallest.getName() + " with population " + smallest.getPopulation()));


        System.out.println("-------------------------");
        System.out.println("provinces with 3M-8M, reverse alpha order, in UPPERCASE");
        final List<Province> middle = filteredStream(provinces)
                .filter(p->(p.getPopulation() >= 3000000) && (p.getPopulation() <= 8000000))
                .sorted(Comparator.comparing(Province::getName).reversed())
                .toList();
        middle.forEach(pr-> System.out.println(pr.getName().toUpperCase() + " with popn " + pr.getPopulation()));


        System.out.println("-------------------------");
        System.out.println("group all provinces by their first letter");
        final Map<Character, List<Province>> provMap = filteredStream(provinces)
                .filter(Objects ::nonNull) // or p->p != null
                .filter(p->!p.getName().isBlank())
                .collect(Collectors.groupingBy(p->p.getName().toUpperCase().charAt(0))); // case insensitive
        provMap.forEach((firstLetter, groupOfProvinces) ->{
            System.out.println(firstLetter + ":");
            groupOfProvinces.forEach(prov-> System.out.println("\t" + prov.getName()));
        });

        System.out.println("-------------------------");
        System.out.println("provinces ordered from shortest to longest:");
        final List<Province> len = filteredStream(provinces)
                .filter(p->!p.getName().isBlank())
                // .sorted(Comparator.comparing(p->p.getName().length())) or method ref:
                .sorted(Comparator.comparingInt(Canada::getProvinceNameLength))
                .toList();
        len.forEach(System.out::println);

        System.out.println("-------------------------");
        System.out.println("longest name");
        final Optional<Province> longest = filteredStream(provinces)
                .max(Comparator.comparingInt(p->p.getName().length()));
        longest.ifPresent(p-> System.out.println("longest name is " + p.getName()));


        System.out.println("-------------------------");
        System.out.println("does canada have a province with 10M+?");
        final boolean has10M = provinces.stream().filter(p->p!=null).anyMatch(p->p.getPopulation() >= 10000000);
        System.out.println(has10M);

        System.out.println("-------------------------");
        System.out.println("does EVERY province have at least 50000?");
        System.out.println(filteredStream(provinces).allMatch(p->p.getPopulation()>= 50000));

        System.out.println("-------------------------");
        System.out.println("is there a province called Alberta?");
        System.out.println(filteredStream(provinces).anyMatch(p->p.getName().equalsIgnoreCase("alberta")));

        System.out.println("-------------------------");
        System.out.println("provinces that start with N:");
        final List<Province> n = filteredStream(provinces)
                .filter(p->p.getName().startsWith("N"))
                .toList();
        n.forEach(System.out::println);



        System.out.println("-------------------------");
        System.out.println("percentage of canada's pop for each province:");
        final int canadaPop = filteredStream(provinces).mapToInt(Province::getPopulation).sum();

        final List<Province> allProvinces = filteredStream(provinces).toList();

        allProvinces.forEach(province-> System.out.printf("%s makes up %.1f%% of canada\n",
                province.getName(),
                ((double) province.getPopulation()/canadaPop) * MAX_PERCENTAGE));

        System.out.println("-------------------------");
        System.out.println("the median province by pop (half the provinces have more, half have less than:");
        final Optional<Province> median;
        median = filteredStream(provinces)
                .sorted(Comparator.comparing(Province::getPopulation))
                .skip(provinces.size()/2) // ignore the first half of the sorted list
                .findFirst();
        median.ifPresent(m-> System.out.println(m));


        System.out.println("-------------------------");
        System.out.println("average population (if not available, make it 0)");
        final double avg;
        avg = filteredStream(provinces)
                .mapToInt(Province::getPopulation)
                .average()
                .orElse(0);
        System.out.println(avg);


        System.out.println("-------------------------");
        System.out.println("provinces with 10+ characters in their name");
        final List<Province> longNames;
        longNames = filteredStream(provinces)
                .filter(pro->pro.getName().length() >= 10)
                .collect(Collectors.toList());
        longNames.forEach(System.out::println);

        System.out.println("-------------------------");
        System.out.println("provinces with a single-word name:"); // i.e. no spaces
        final List<Province> singleWord;
        singleWord = filteredStream(provinces)
                .filter(p->!p.getName().contains(" "))
                .peek(p-> System.out.println("peeking at " + p)) // just for debugging
                .toList(); // terminal op is a LIST; that kicks off all operations; peek will show those


        System.out.println("-------------------------");
        System.out.println("how many have A or a in their name?");
        System.out.println(filteredStream(provinces).filter(p->p.getName().toLowerCase().contains("a")).count());



        System.out.println("-------------------------");
        System.out.println("total pop of provinces starting with B");
        System.out.println(filteredStream(provinces).filter(p->p.getName().startsWith("B")).mapToInt(p->p.getPopulation()).sum());


        System.out.println("-------------------------");
        System.out.println("provinces listed in DESC order of their last letter of their name");
        final List<Province> last;
        last = filteredStream(provinces)
                .sorted(Comparator.comparing((Province p)->p.getName().charAt(p.getName().length() - 1)).reversed())
                .toList();
        last.forEach(System.out::println);


        System.out.println("-------------------------");
        System.out.println("total pop of provinces whose name contains the same letter twice in a row:");
        System.out.println(
                filteredStream(provinces)
                        .filter(p-> Pattern.compile("(.)\\1").matcher(p.getName()).find())
                        .peek(p-> System.out.println("peeking at " + p)) // side effect: printing is for debugging only here; not production
                        .mapToInt(Province::getPopulation)
                        .sum()
        );

        System.out.println("-------------------------");
        System.out.println("names start with N, contain e, have 500000+, sort DESC by pop, and calculate their total pop");
        final int totalPop;
        totalPop = filteredStream(provinces)
                .filter(p->p.getName().startsWith("N"))
                .filter(p->p.getName().contains("e"))
                .filter(p->p.getPopulation() >= 500000)
                .sorted(Comparator.comparing(Province::getPopulation).reversed())
                .peek(p-> System.out.println("peekin at " + p))
                .mapToInt(Province::getPopulation)
                .sum();
        System.out.println(totalPop);


        System.out.println("-------------------------");
        System.out.println("total pop of provinces that do not contain a vowel:");
        System.out.println(filteredStream(provinces)
                .filter(p->!p.getName().matches(".*[AEIOUaeiou].*"))
                .peek(p-> System.out.println("no vowel: " + p))
                .mapToInt(Province::getPopulation)
                .sum());

        System.out.println("-------------------------");
        System.out.println("alt vowel and consonant");
        final List<Province> alt = filteredStream(provinces)
                .filter(p->p.getName().toUpperCase().matches("([AEIOU][^AEIOU])*|([^AEIOU][AEIOU])*"))
                .toList();
        alt.forEach(System.out::println);

        System.out.println("-------------------------");
        System.out.println("palindromes (ignore case, ignore whitespace");
        final List<Province> pals = filteredStream(provinces)
                .filter(p->new StringBuilder(p.getName().replaceAll("\\s+", "")).reverse().toString().equalsIgnoreCase(p.getName().replaceAll("\\s+", "")))
                .toList();
        pals.forEach(System.out::println);

        System.out.println("-------------------------");
        System.out.println("the FIRST province whose pop is prime");
        Optional<Province> prime = filteredStream(provinces)
                .filter(p->isPrime(p.getPopulation()))
                // if you try peek() it will only run the terminal operation so only prints ONE; the first
                .findFirst();
        prime.ifPresent(System.out::println);

        System.out.println("-------------------------");
        System.out.println("anagrams, grouped together");
        final Map<String, List<Province>> ans = filteredStream(provinces)
                .collect(Collectors.groupingBy(p-> sortedString(p.getName())));

        ans.values().stream().filter(group->group.size() > 1).forEach(group-> System.out.println("anagrams: " + group));


        System.out.println("-------------------------");
        System.out.println("3 vowels");
        final List<Province> three = filteredStream(provinces)
                .filter(p->p.getName().replaceAll("[^AEIOUaeiout]", "").length() == 3)
                .toList();
        three.forEach(System.out::println);

        System.out.println("-------------------------");
        System.out.println("provinces grouped by pop group: 0-1M, 1M-5M, 5M+");
        final Map<String, List<Province>> ranges = filteredStream(provinces)
                .collect(Collectors.groupingBy(p->{
                    if(p.getPopulation() <= 1000000)
                    {
                        return "0-1M";
                    }
                    else if(p.getPopulation() > 5000000)
                    {
                        return "5M+";
                    }
                    else

                    {
                        return "1M-5M";
                    }
                }));
        ranges.forEach((range, group)-> System.out.println(range + "..." + group));


        System.out.println("total number of times A or a appears in all province names");
        long totalA;
        totalA = filteredStream(provinces)
                .mapToLong(p->p.getName().chars()
                        .filter(ch->ch == 'a' || ch =='A')
                        .count())
                .sum();
        System.out.println("number of A: " + totalA);



    }

    // reusable method to get non-null province stream
    private static Stream<Province> filteredStream(final List<Province> provinces)
    {
        return provinces.stream().filter(p-> p != null && p.getName() != null);
    }

    private static int getProvinceNameLength(final Province p)
    {
        return p.getName().trim().length();
    }


    private static boolean isPrime(final int n)
    {
        if(n <= 1)
        {
            return false;
        }

        for(int i = 2; i < Math.sqrt(n); i++)
        {
            if(n % i == 0)
            {
                return false;
            }
        }
        return true;
    }

    private static String sortedString(final String input)
    {
        final char[] chars;
        chars = input.replaceAll("\\s+", "").toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
