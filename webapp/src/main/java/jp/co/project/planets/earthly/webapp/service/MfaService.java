package jp.co.project.planets.earthly.webapp.service;

import org.springframework.stereotype.Service;

import jp.co.project.planets.earthly.common.logic.TotpLogic;
import jp.co.project.planets.earthly.core.account.Account;

/**
 * authentication service
 */
@Service
public class MfaService {

    private final TotpLogic totpLogic;

    public MfaService(final TotpLogic totpLogic) {
        this.totpLogic = totpLogic;
    }

    /**
     * 2要素認証コードの検証
     *
     * @param code
     *            mfa code
     * @param account
     *            ユーザー情報
     * @return true: 検証OK false: 検証失敗
     */
    public boolean verify(final String code, final Account account) {
        return totpLogic.verifyCode(code, account.multiFactor().secret());
    }
}
