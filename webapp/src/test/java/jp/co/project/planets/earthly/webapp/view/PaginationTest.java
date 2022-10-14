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
    void ページオブジェクトが開始ページの場合に開始ページとして0が返されること() {
        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia");
        final var pageRequest = PageRequest.of(0, countryList.size());
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getStartPage(page);

        // verify
        assertThat(actual).isZero();
    }

    @Test
    void ページオブジェクトが終了ページで総ページ数3未満の時に開始ページとしてページ数マイナス1された値が返されること() {

        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia");
        final var pageRequest = PageRequest.of(3, countryList.size());
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getStartPage(page);

        // verify
        assertThat(actual).isEqualTo(2);
    }

    @Test
    void ページオブジェクトが終了ページで総ページ数3以上の時に開始ページとして総ページ数マイナス2された値が返されること() {

        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia", "Argentina", "Georgia");
        final var pageRequest = PageRequest.of(5, countryList.size());
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getStartPage(page);

        // verify
        assertThat(actual).isEqualTo(4);
    }

    @Test
    void ページオブジェクトが開始ページでも終了ページでない場合に開始ページとしてページ番号が返されること() {
        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia", "Argentina", "Georgia",
                "Antigua And Barbuda", "Bangladesh", "Cook Islands", "The Bahamas", "Switzerland");
        final var pageRequest = PageRequest.of(3, 5);
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getStartPage(page);

        // verify
        assertThat(actual).isEqualTo(3);
    }

    @Test
    void ページオブジェクトが終了ページの場合に終了ページとしてページ番号が返されること() {

        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia", "Argentina", "Georgia",
                "Antigua And Barbuda", "Bangladesh", "Cook Islands", "The Bahamas", "Switzerland");
        final var pageRequest = PageRequest.of(countryList.size(), countryList.size());
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getEndPage(page);

        // verify
        assertThat(actual).isEqualTo(10);
    }

    @Test
    void ページオブジェクトが終了ページでない場合に終了ページとしてページ番号マイナス1された値が返されること() {

        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia", "Argentina", "Georgia",
                "Antigua And Barbuda", "Bangladesh", "Cook Islands", "The Bahamas", "Switzerland");
        final var pageRequest = PageRequest.of(1, 2);
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getEndPage(page);

        // verify
        assertThat(actual).isEqualTo(4);
    }

    @Test
    void ページオブジェクトが終了ページでない総ページ数からページ番号を減算した時に最大ページ範囲未満の場合は総ページ数が返されること() {
        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia", "Argentina", "Georgia",
                "Antigua And Barbuda", "Bangladesh", "Cook Islands", "The Bahamas", "Switzerland");
        final var pageRequest = PageRequest.of(8, 2);
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getEndPage(page);

        // verify
        assertThat(actual).isEqualTo(10);
    }

    @Test
    void ページオブジェクトが終了ページでない総ページ数からページ番号を減算した時に最大ページ範囲以上はページ番号が返されること() {
        final var countryList = List.of("Sierra Leone", "Germany", "French Polynesia", "Argentina", "Georgia",
                "Antigua And Barbuda", "Bangladesh", "Cook Islands", "The Bahamas", "Switzerland");
        final var pageRequest = PageRequest.of(8, 8);
        final var page = new PageImpl<String>(countryList, pageRequest, countryList.size());

        // test
        final long actual = pagination.getEndPage(page);

        // verify
        assertThat(actual).isEqualTo(10);
    }

    @InjectMocks
    Pagination pagination;
}