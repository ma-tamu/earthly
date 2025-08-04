package jp.co.project.planets.earthly.schema.strategy;

import java.util.function.BiFunction;

import org.seasar.doma.AggregateStrategy;
import org.seasar.doma.AssociationLinker;

import jp.co.project.planets.earthly.schema.model.entity.Company;
import jp.co.project.planets.earthly.schema.model.entity.Country;
import jp.co.project.planets.earthly.schema.model.entity.Organization;

@AggregateStrategy(root = Organization.class, tableAlias = "organization")
public interface OrganizationAggregateStrategy {

    @AssociationLinker(propertyPath = "company", tableAlias = "company")
    BiFunction<Organization, Company, Organization> company = (organization, company) -> {
        organization.setCompany(company);
        return organization;
    };

    @AssociationLinker(propertyPath = "company.country", tableAlias = "country")
    BiFunction<Company, Country, Company> country = (company, country) -> {
        company.setCountry(country);
        return company;
    };
}
