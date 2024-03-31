package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.boot.Pageables;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.RedirectUriDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;

@Repository
public class OAuthClientRedirectUrlRepository {

    private final RedirectUriDao redirectUriDao;

    public OAuthClientRedirectUrlRepository(final RedirectUriDao redirectUriDao) {
        this.redirectUriDao = redirectUriDao;
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
    public List<OauthClientRedirectUrl> findByClientRedirectUrl(final String clientId, final String redirectUrl,
            final boolean hasViewAllClient, final String operatorUserId, final Pageable pageable) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        return redirectUriDao.selectByClientRedirectUrl(clientId, redirectUrl, hasViewAllClient, operatorUserId,
                selectOptions);
    }
}
