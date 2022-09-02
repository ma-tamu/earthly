package jp.co.project.planets.earthly.webapp.view;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;

/**
 * ページネーション
 */
public class Pagination {

    public static final String DIALECT_NAME = "pagination";
    private static final int PAGE_RANGE = 3;

    private static final int PAGE_DISPLAY_RANGE = 2;
    private static final int MAX_PAGE_RANGE = 5;

    /**
     * 最初のページ
     *
     * @param page
     *         ページ
     * @return true:最初のページ false:最初のページでない
     */
    public boolean isFirst(final Page page) {
        return page.isFirst();
    }

    /**
     * 最終ページか
     *
     * @param page
     *         ページ
     * @return true:最終ページ false:最終ページでない
     */
    public boolean isLast(final Page page) {
        return page.isLast();
    }

    /**
     * ページネーションの有無
     *
     * @param page
     *         ページ
     * @return true:ページネーションあり false:ページネーションなし
     */
    public boolean isPageable(final Page page) {
        return CollectionUtils.isNotEmpty(page.getContent());
    }


    /**
     * ページネーション開始ページ番号を取得
     *
     * @param page
     *         ページ
     * @return 開始ページ番号
     */
    public long getStartPage(final Page page) {
        if (page.isFirst()) {
            return 0;
        }

        final long pageNumber = page.getNumber();
        if (page.isLast()) {
            return page.getTotalPages() < PAGE_RANGE ? pageNumber - 1 : page.getTotalPages() - PAGE_DISPLAY_RANGE;
        }

        return pageNumber;
    }

    /**
     * ページネーション終了ページ番号を取得
     *
     * @param page
     *         ページ
     * @return 終了ページ番号
     */
    public long getEndPage(final Page page) {
        final long pageNumber = page.getNumber();
        if (page.isLast()) {
            return pageNumber;
        }

        if (pageNumber < MAX_PAGE_RANGE) {
            return page.getTotalPages() - 1;
        }

        if ((page.getTotalPages() - pageNumber) < MAX_PAGE_RANGE) {
            return page.getTotalPages();
        }

        return pageNumber + PAGE_DISPLAY_RANGE;
    }


}
