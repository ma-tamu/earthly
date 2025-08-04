package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.dao.LogoutRedirectUrlDao;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl_;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
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
     * @param pageable
     *            ページャー
     * @param account
     *            操作ユーザーID
     * @return OAuthクライアントリダイレクトURL
     */
    public OAuthClientLogoutRedirectUriSearchResultDto findByClientRedirectUrl(final String clientId,
        final String logoutRedirectUrl, final Pageable pageable, final Account account) {
        final var selectOptions = Pageables.toSelectOptions(pageable);
        final boolean hasViewAllClient = account.permissions().contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        final var logoutRedirectUrlList = logoutRedirectUrlDao.selectByClientLogoutRedirectUrl(clientId,
                logoutRedirectUrl, hasViewAllClient, account.id(), selectOptions);
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
     * @param account
     *            操作ユーザー
     * @return OAuthクライアントログアウトURIリスト
     */
    public List<LogoutRedirectUrl> findByClientIdAndRedirectUris(final String clientId,
        final List<String> redirectUriIdList, final Account account) {
        final boolean hasViewAllOAuthClient = account.permissions().contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        return logoutRedirectUrlDao.selectByClientIdAndRedirectUris(clientId, redirectUriIdList, hasViewAllOAuthClient,
                account.id());
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
