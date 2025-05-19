package jp.co.project.planets.earthly.core.account;

import org.jilt.Builder;

/**
 * 会社
 * 
 * @param id
 *            会社ID
 * @param name
 *            会社名
 */
@Builder(factoryMethod = "builder")
public record Company(String id, String name) {
}
