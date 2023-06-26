package jp.co.project.planets.earthly.schema.test.emuns;

public enum CountryEnum {
    JAPAN("d92bf311652a77227d2725c204a5396b", "Japan");

    private final String id;
    private final String name;

    CountryEnum(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
