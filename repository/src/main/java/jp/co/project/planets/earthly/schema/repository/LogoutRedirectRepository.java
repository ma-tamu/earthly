package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.boot.Pageables;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.LogoutRedirectUrlDao;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;

/**
 * logout redirect repository
 */
@Repository
public class LogoutRedirectRepository {

    private final LogoutRedirectUrlDao logoutRedirectUrlDao;

    public LogoutRedirectRepository(final LogoutRedirectUrlDao logoutRedirectUrlDao) {
        this.logoutRedirectUrlDao = logoutRedirectUrlDao;
    }

    /**
     * 対象OAuthクライアントのログアウトリダイレクトURLを取得
     *
     * @param clientId
     *            OAuthクライアントID
     * @param logoutRedirectUrl
     *            ログアウトリダイレクトURL
     * @param hasViewAllClient
     *            view_all_clientを保持しているか
     * @param operatorUserId
     *            操作ユーザーID
     * @param pageable
     *            ページャー
     * @return OAuthクライアントリダイレクトURL
     */
    public List<LogoutRedirectUrl> findByClientRedirectUrl(final String clientId, final String logoutRedirectUrl,
        final boolean hasViewAllClient, final String operatorUserId, final Pageable pageable) {
        final var selectOptions = Pageables.toSelectOptions(pageable);
        return logoutRedirectUrlDao.selectByClientLogoutRedirectUrl(clientId, logoutRedirectUrl, hasViewAllClient,
                operatorUserId, selectOptions);
    }
}
