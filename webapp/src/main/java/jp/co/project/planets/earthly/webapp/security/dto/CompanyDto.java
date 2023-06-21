package jp.co.project.planets.earthly.webapp.security.dto;

import java.io.Serializable;

/**
 * company dto
 * 
 * @param id
 *            company id
 * @param name
 *            company name
 */
public record CompanyDto(String id, String name) implements Serializable {
}
