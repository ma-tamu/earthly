package jp.co.project.planets.earthly.schema.repository;

import java.util.Optional;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.NoticeDao;
import jp.co.project.planets.earthly.schema.db.entity.Notice;
import jp.co.project.planets.earthly.schema.db.entity.Notice_;

@Repository
public class NoticeRepository {

    private final NoticeDao noticeDao;
    private final QueryDsl queryDsl;

    public NoticeRepository(final NoticeDao noticeDao, final QueryDsl queryDsl) {
        this.noticeDao = noticeDao;
        this.queryDsl = queryDsl;
    }

    public Optional<Notice> findByPrimaryKey(final String id) {
        return Optional.ofNullable(noticeDao.selectById(id));
    }

    public Optional<Notice> findByTitleAndBody(final String title, final String body) {
        final var criteria = new Notice_();
        return queryDsl.from(criteria).where(where -> {
            where.eq(criteria.title, title);
            where.eq(criteria.body, body);
        }).fetchOptional();
    }

    public int insert(final Notice notice) {
        return noticeDao.insert(notice);
    }

}
