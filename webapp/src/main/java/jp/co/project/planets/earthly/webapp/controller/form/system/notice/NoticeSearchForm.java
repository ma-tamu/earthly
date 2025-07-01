package jp.co.project.planets.earthly.webapp.controller.form.system.notice;

import java.io.Serializable;

import org.jilt.Builder;

/**
 * お知らせ検索FORM
 * 
 * @param title
 *            件名
 */
@Builder(factoryMethod = "builder")
public record NoticeSearchForm(String title, String startDate, String endDate) implements Serializable {
}
