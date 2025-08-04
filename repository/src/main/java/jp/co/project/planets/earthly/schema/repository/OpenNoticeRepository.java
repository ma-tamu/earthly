package jp.co.project.planets.earthly.schema.repository;

import java.util.Optional;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OpenNoticeDao;
import jp.co.project.planets.earthly.schema.db.entity.OpenNotice;
import jp.co.project.planets.earthly.schema.db.entity.OpenNotice_;

/**
 * 既読管理リポジトリ
 */
@Repository
public class OpenNoticeRepository {

    private final OpenNoticeDao openNoticeDao;
    private final QueryDsl queryDsl;

    public OpenNoticeRepository(final OpenNoticeDao openNoticeDao, final QueryDsl queryDsl) {
        this.openNoticeDao = openNoticeDao;
        this.queryDsl = queryDsl;
    }

    public Optional<OpenNotice> findByUserId(final String userId) {
        final var criteria = new OpenNotice_();
        return queryDsl.from(criteria).where(where -> where.eq(criteria.userId, userId)).fetchOptional();
    }

    public int insert(OpenNotice openNotice) {
        return openNoticeDao.insert(openNotice);
    }

    public int update(OpenNotice openNotice) {
        return openNoticeDao.update(openNotice);
    }
}
