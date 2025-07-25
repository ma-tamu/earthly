package jp.co.project.planets.earthly.schema.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.NoticeDao;
import jp.co.project.planets.earthly.schema.db.entity.Notice;
import jp.co.project.planets.earthly.schema.db.entity.Notice_;
import jp.co.project.planets.earthly.schema.model.dto.NoticeSearchResultDto;

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

    public NoticeSearchResultDto findByTitleAndPublicationDate(final String title, final LocalDateTime startDate,
        final LocalDateTime endDate, final Pageable pageable) {
        final var options = Pageables.toSelectOptions(pageable).count();
        final var notices = noticeDao.selectByTitleAndPublicationDate(title, startDate, endDate, options);
        return new NoticeSearchResultDto(notices, pageable.getOffset(), pageable.getPageSize());
    }

    public List<Notice> findUnreadEmphasisNoticeByPublicationDate(final LocalDateTime lastEmphasisAt) {
        final var currentLocalDateTime = LocalDateTime.now();
        final var criteria = new Notice_();
        return queryDsl.from(criteria).where(where -> {
            where.gt(criteria.startAt, lastEmphasisAt);
            where.le(criteria.startAt, currentLocalDateTime);
            where.or(() -> {
                where.isNull(criteria.endAt);
                where.ge(criteria.endAt, currentLocalDateTime);
            });
            where.eq(criteria.emphasis, Boolean.TRUE);
            where.eq(criteria.isDeleted, Boolean.FALSE);
        }).orderBy(order -> order.desc(criteria.startAt)).fetch();
    }

    public int insert(final Notice notice) {
        return noticeDao.insert(notice);
    }

    public int update(final Notice notice) {
        return noticeDao.update(notice);
    }
}
