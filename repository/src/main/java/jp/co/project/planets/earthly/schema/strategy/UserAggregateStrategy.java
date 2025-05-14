package jp.co.project.planets.earthly.schema.strategy;

import java.util.function.BiFunction;

import org.seasar.doma.AggregateStrategy;
import org.seasar.doma.AssociationLinker;

import jp.co.project.planets.earthly.schema.model.entity.Company;
import jp.co.project.planets.earthly.schema.model.entity.Country;
import jp.co.project.planets.earthly.schema.model.entity.User;

@AggregateStrategy(root = User.class, tableAlias = "user")
public interface UserAggregateStrategy {

    @AssociationLinker(propertyPath = "company", tableAlias = "company")
    BiFunction<User, Company, User> company = (user, company) -> {
        user.setCompany(company);
        return user;
    };

    @AssociationLinker(propertyPath = "company.country", tableAlias = "country")
    BiFunction<Company, Country, Company> country = (company, country) -> {
        company.setCountry(country);
        return company;
    };
}
