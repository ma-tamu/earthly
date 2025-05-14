package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serial;
import java.io.Serializable;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class Country implements Serializable {

    @Serial
    private static final long serialVersionUID = 2294584076902700825L;
    private String id;
    private String name;

    public Country() {
    }

    public Country(final String id, final String name) {
        this.id = id;
        this.name = name;
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
}
