package jp.co.project.planets.earthly.test.emuns;

public enum RegionEnum {
    /** アジア */
    ASIA("45bd917836a599faf2a30c54d677d9a6", "Asia");


    private final String id;
    private final String name;

    RegionEnum(final String id, final String name) {
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
