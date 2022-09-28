package jp.co.project.planets.earthly.test.emuns;

public enum LanguageEnum {

    /** 日本語 */
    JAPANESE("19154223c579d44a0fe8c9e5476d8f5e", "japanese");


    private final String id;
    private final String name;

    LanguageEnum(final String id, final String name) {
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
