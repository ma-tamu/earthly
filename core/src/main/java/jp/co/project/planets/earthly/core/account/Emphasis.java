package jp.co.project.planets.earthly.core.account;

import java.time.LocalDateTime;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record Emphasis(boolean showEmphasisNotice, LocalDateTime lastEmphasisAt) {
}
