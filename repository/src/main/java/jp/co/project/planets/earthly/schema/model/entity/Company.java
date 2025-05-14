package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serial;
import java.io.Serializable;

import org.seasar.doma.Association;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public final class Company implements Serializable {

    @Serial
    private static final long serialVersionUID = -1728358959423259025L;
    private String id;
    private String name;
    @Association
    private Country country;

    public Company() {
    }

    public Company(final String id, final String name, final Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }
}
