package jp.co.project.planets.earthly.schema.db.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.schema.db.dao.base.NoticeBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.Notice;

@Dao
@ConfigAutowireable
public interface NoticeDao extends NoticeBaseDao {

    @Select
    List<Notice> selectByTitleAndPublicationDate(String title, LocalDateTime startDate, LocalDateTime endDate,
        SelectOptions options);
}
