package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.LogoutRedirectUrlDao;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl_;
import jp.co.project.planets.earthly.schema.model.dto.OAuthClientLogoutRedirectUriSearchResultDto;

/**
 * logout redirect repository
 */
@Repository
public class LogoutRedirectRepository {

    private final LogoutRedirectUrlDao logoutRedirectUrlDao;
    private final QueryDsl queryDsl;

    public LogoutRedirectRepository(final LogoutRedirectUrlDao logoutRedirectUrlDao, final QueryDsl queryDsl) {
        this.logoutRedirectUrlDao = logoutRedirectUrlDao;
        this.queryDsl = queryDsl;
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
    public OAuthClientLogoutRedirectUriSearchResultDto findByClientRedirectUrl(final String clientId,
        final String logoutRedirectUrl, final boolean hasViewAllClient, final String operatorUserId,
        final Pageable pageable) {
        final var selectOptions = Pageables.toSelectOptions(pageable);
        final var logoutRedirectUrlList = logoutRedirectUrlDao.selectByClientLogoutRedirectUrl(clientId,
                logoutRedirectUrl, hasViewAllClient, operatorUserId, selectOptions);
        return new OAuthClientLogoutRedirectUriSearchResultDto(logoutRedirectUrlList, pageable.getOffset(),
                selectOptions.getCount());
    }

    /**
     * 対象OAuthクライアントに紐づくログアウトリダイレクトURIを取得
     *
     * @param clientId
     *            OAuthクライアントID
     * @param redirectUriIdList
     *            ログアウトリダイレクトURL IDリスト
     * @param hasViewAllOAuthClient
     *            view_all_clientを保持しているか
     * @param operatorUserId
     *            操作ユーザー
     * @return OAuthクライアントログアウトURIリスト
     */
    public List<LogoutRedirectUrl> findByClientIdAndRedirectUris(final String clientId,
        final List<String> redirectUriIdList, final boolean hasViewAllOAuthClient, final String operatorUserId) {
        return logoutRedirectUrlDao.selectByClientIdAndRedirectUris(clientId, redirectUriIdList, hasViewAllOAuthClient,
                operatorUserId);
    }

    /**
     * insert logout redirect uri
     * 
     * @param logoutRedirectUrl
     *            logout redirect uri
     */
    public void insert(final LogoutRedirectUrl logoutRedirectUrl) {
        logoutRedirectUrlDao.insert(logoutRedirectUrl);
    }

    public void delete(final String id) {
        final var criteria = new LogoutRedirectUrl_();
        queryDsl.delete(criteria).where(declaration -> declaration.eq(criteria.id, id)).execute();

    }
}
