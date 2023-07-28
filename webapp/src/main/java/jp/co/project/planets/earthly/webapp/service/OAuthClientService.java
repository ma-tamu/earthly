package jp.co.project.planets.earthly.webapp.service;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.annotations.VisibleForTesting;

import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * OAuthクライアントサービス
 */
@Service
public class OAuthClientService {

    private final OAuthClientRepository oauthClientRepository;

    public OAuthClientService(final OAuthClientRepository oauthClientRepository) {
        this.oauthClientRepository = oauthClientRepository;
    }

    /**
     * OAuthクライアント検索
     *
     * @param name
     *            OAuthクライアント名
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアントリスト
     */
    @Transactional
    public PageImpl<OauthClient> search(final String name, final Pageable pageable,
            final EarthlyUserInfoDto userInfoDto) {

        validateAccessibleClient(userInfoDto);

        final var oauthClientSearchResultDto = oauthClientRepository.findByName(name, pageable,
                userInfoDto.permissionEnumList(), userInfoDto.id());
        return new PageImpl<>(oauthClientSearchResultDto.oauthClientList(), pageable,
                oauthClientSearchResultDto.total());
    }

    /**
     * 閲覧できるOAuthクライアントの検証
     *
     * @param userInfoDto
     *            ユーザー情報
     */
    @VisibleForTesting
    void validateAccessibleClient(final EarthlyUserInfoDto userInfoDto) {

        final var oauthClientList = oauthClientRepository.findByAccessible(userInfoDto.permissionEnumList(),
                userInfoDto.id());
        if (CollectionUtils.isEmpty(oauthClientList)) {
            throw new ForbiddenException(ErrorCode.EWA4XX016);
        }
    }
}
