package jp.co.project.planets.earthly.webapp.controller.form.company;

import org.jilt.Builder;

import jp.co.project.planets.earthly.webapp.model.dto.GroupBelongUserSearchDto;

@Builder(factoryMethod = "builder")
public record GroupNotBelongUserSearchForm(String loginId, String name) {

    public GroupBelongUserSearchDto toDto() {
        return new GroupBelongUserSearchDto(loginId, name);
    }
}
