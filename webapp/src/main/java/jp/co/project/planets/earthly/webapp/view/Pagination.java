package jp.co.project.planets.earthly.webapp.view;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class Pagination {

    public static final String DIALECT_NAME = "Pagination";

    public boolean isFirst(final Page page) {
        return page.isFirst();
    }

    public boolean isLast(final Page page) {
        return page.isLast();
    }

    public long getStartPage(final Page page) {
        if (page.isFirst()) {
            return 0;
        }


        final long pageNumber = page.getNumber();
        if (page.isLast()) {
            return pageNumber - 2;
        }

        return pageNumber;
    }

    public long getEndPage(final Page page) {
        if (page.isLast()) {
            return page.getTotalPages();
        }

        final long pageNumber = page.getNumber();
        if (pageNumber < 5) {
            return 5;
        }

        if ((page.getTotalPages() - pageNumber) < 5) {
            return page.getTotalPages();
        }

        return pageNumber + 2;
    }



}
