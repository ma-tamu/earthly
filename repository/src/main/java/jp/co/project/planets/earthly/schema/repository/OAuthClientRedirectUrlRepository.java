package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.RedirectUriDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl_;
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
     * @param hasViewAllClient
     *            view_all_clientを保持しているか
     * @param operatorUserId
     *            操作ユーザーID
     * @param pageable
     *            ページャー
     * @return OAuthクライアントリダイレクトURL
     */
    public OAuthClientRedirectUriSearchResultDto findByClientRedirectUrl(final String clientId,
        final String redirectUrl, final boolean hasViewAllClient, final String operatorUserId,
        final Pageable pageable) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final var oauthClientRedirectUrlList = redirectUriDao.selectByClientRedirectUrl(clientId, redirectUrl,
                hasViewAllClient, operatorUserId,
                selectOptions);
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
     * @param hasViewAllClient
     *            view_all_clientを保持しているか
     * @param operatorUserId
     *            操作ユーザーID * @return
     */
    public List<OauthClientRedirectUrl> findByClientIdAndRedirectUris(final String clientId,
        final List<String> redirectUriIdList, final boolean hasViewAllClient, final String operatorUserId) {
        return redirectUriDao.selectByClientIdAndRedirectUris(clientId, redirectUriIdList, hasViewAllClient,
                operatorUserId, SelectOptions.get().forUpdate());
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
