class Province
{
    private final String name;
    private final int    population;

    public static final int MIN_POPULATION;

    static
    {
        MIN_POPULATION = 0;
    }

    Province(final String name,
             final int population)
    {
        this.name = name;
        this.population = population;
    }

    String getName()
    {
        return name;
    }

    int getPopulation()
    {
        return population;
    }

    @Override
    public String toString()
    {
        return String.format("%s with pop %d",
                name,
                population);
    }
}
