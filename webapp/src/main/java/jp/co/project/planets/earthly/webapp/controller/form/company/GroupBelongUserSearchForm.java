package jp.co.project.planets.earthly.webapp.controller.form.company;

import org.jilt.Builder;

import jp.co.project.planets.earthly.webapp.model.dto.GroupBelongUserSearchDto;

@Builder(factoryMethod = "builder")
public record GroupBelongUserSearchForm(String loginId, String name, Boolean isRemoveMode) {

    public GroupBelongUserSearchDto toDto() {
        return new GroupBelongUserSearchDto(loginId, name);
    }
}
