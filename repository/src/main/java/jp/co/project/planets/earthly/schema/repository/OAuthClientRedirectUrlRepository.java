package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.dao.RedirectUriDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl_;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.dto.OAuthClientRedirectUriSearchResultDto;

/**
 * OAuth client redirect url repository
 */
@Repository
public class OAuthClientRedirectUrlRepository {

    private final RedirectUriDao redirectUriDao;
    private final QueryDsl queryDsl;

    public OAuthClientRedirectUrlRepository(final RedirectUriDao redirectUriDao, final QueryDsl queryDsl) {
        this.redirectUriDao = redirectUriDao;
        this.queryDsl = queryDsl;
    }

    /**
     * 対象OAuthクライアントのリダイレクトURLを取得
     * 
     * @param clientId
     *            OAuthクライアントID
     * @param redirectUrl
     *            リダイレクトURL
     * @param account
     *            操作ユーザー
     * @param pageable
     *            ページャー
     * @return OAuthクライアントリダイレクトURL
     */
    public OAuthClientRedirectUriSearchResultDto findByClientRedirectUrl(final String clientId,
        final String redirectUrl, final Pageable pageable, final Account account) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllClient = account.permissions().contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        final var oauthClientRedirectUrlList = redirectUriDao.selectByClientRedirectUrl(clientId, redirectUrl,
                hasViewAllClient, account.id(), selectOptions);
        return new OAuthClientRedirectUriSearchResultDto(oauthClientRedirectUrlList, pageable.getOffset(),
                selectOptions.getCount());
    }

    /**
     * クライアントIDとリダイレクトURI IDに一致するリダイレクトURIを取得。
     * 
     * @param clientId
     *            OAuthクライアントID
     * @param redirectUriIdList
     *            リダイレクトURL IDリスト
     * @param account
     *            操作ユーザー
     * @return リダイレクトURIリスト
     */
    public List<OauthClientRedirectUrl> findByClientIdAndRedirectUris(final String clientId,
        final List<String> redirectUriIdList, final Account account) {
        final boolean hasViewAllClient = account.permissions().contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        return redirectUriDao.selectByClientIdAndRedirectUris(clientId, redirectUriIdList, hasViewAllClient,
                account.id(), SelectOptions.get().forUpdate());
    }

    /**
     * OAuthクライアントリダイレクトURLの登録
     * 
     * @param oauthClientRedirectUrl
     *            OAuthクライアントリダイレクトURL
     */
    public void insert(final OauthClientRedirectUrl oauthClientRedirectUrl) {
        redirectUriDao.insert(oauthClientRedirectUrl);
    }

    /**
     * OAuthクライアントリダイレクトURI削除
     * 
     * @param id
     *            OAuthクライアントリダイレクトURI ID
     */
    public void delete(final String id) {
        final var criteria = new OauthClientRedirectUrl_();
        queryDsl.delete(criteria).where(declaration -> declaration.eq(criteria.id, id)).execute();
    }
}
