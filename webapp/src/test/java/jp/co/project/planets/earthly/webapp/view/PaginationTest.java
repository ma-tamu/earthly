package jp.co.project.planets.earthly.webapp.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PaginationTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void ページオブジェクトが開始ページの場合に0が返されること() {
        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia");
        final var pageRequest = PageRequest.of(0, countryList.size());
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getStartPage(page);

        // verify
        assertThat(actual).isZero();
    }

    @Test
    void ページオブジェクトが終了ページで総ページ数3未満の時にページ数マイナス1された値が返されること() {

        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia");
        final var pageRequest = PageRequest.of(3, countryList.size());
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getStartPage(page);

        // verify
        assertThat(actual).isEqualTo(2);
    }

    @Test
    void ページオブジェクトが終了ページで総ページ数3以上の時に総ページ数マイナス2された値が返されること() {

        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia", "Argentina", "Georgia");
        final var pageRequest = PageRequest.of(5, countryList.size());
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getStartPage(page);

        // verify
        assertThat(actual).isEqualTo(4);
    }

    @Test
    void ページオブジェクトが開始ページでも終了ページでない場合にページ番号が返されること() {
        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia", "Argentina", "Georgia",
                "Antigua And Barbuda", "Bangladesh", "Cook Islands", "The Bahamas", "Switzerland");
        final var pageRequest = PageRequest.of(3, 5);
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getStartPage(page);

        // verify
        assertThat(actual).isEqualTo(3);
    }


    @InjectMocks
    Pagination pagination;
}