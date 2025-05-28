package jp.co.project.planets.earthly.webapp.controller.form.role;

import java.io.Serializable;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record RoleGrantedUserSearchForm(String loginId, String name, String companyName, Boolean isRemoveMode)
        implements Serializable {

}
