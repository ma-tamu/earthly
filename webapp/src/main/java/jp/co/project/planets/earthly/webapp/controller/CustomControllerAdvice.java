package jp.co.project.planets.earthly.webapp.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.project.planets.earthly.core.enums.Timezone;

@ControllerAdvice
public class CustomControllerAdvice {

    @ModelAttribute("timezone")
    public Timezone[] getTimezone() {
        return Timezone.values();
    }
}
