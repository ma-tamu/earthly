package jp.co.project.planets.earthly.webapp.controller.form.system.notice;

import java.io.Serializable;

/**
 * お知らせ検索FORM
 * 
 * @param title
 *            件名
 */
public record NoticeSearchForm(String title) implements Serializable {
}
