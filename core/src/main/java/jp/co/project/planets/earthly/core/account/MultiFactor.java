package jp.co.project.planets.earthly.core.account;

import org.jilt.Builder;

/**
 * 多要素認証
 * 
 * @param enabled
 *            多要素認証有効性
 * @param secret
 *            シークレット
 */
@Builder(factoryMethod = "builder")
public record MultiFactor(boolean enabled, String secret) {
}
