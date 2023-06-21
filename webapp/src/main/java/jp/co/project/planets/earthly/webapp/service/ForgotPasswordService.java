package jp.co.project.planets.earthly.webapp.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.common.logic.MailLogic;
import jp.co.project.planets.earthly.db.entity.PasswordToken;
import jp.co.project.planets.earthly.repository.PasswordTokenRepository;
import jp.co.project.planets.earthly.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;

@Service
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final MailLogic mailLogic;

    public ForgotPasswordService(final UserRepository userRepository,
            final PasswordTokenRepository passwordTokenRepository, final MailLogic mailLogic) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.mailLogic = mailLogic;
    }

    @Transactional
    public void send(final String loginId, final String mail) {
        final var user = userRepository.findByMail(loginId, mail)
                .orElseThrow(() -> new BadRequestException(ErrorCode.EWA4XX015));
        final var expire = LocalDateTime.now(Clock.systemUTC()).plusDays(1);
        final var passwordToken = new PasswordToken(null, user.getId(),
                UUID.randomUUID().toString().replace("-", StringUtils.EMPTY), expire);
        passwordTokenRepository.insert(passwordToken);

        mailLogic.postPasswordRestNotification(null, passwordToken);
    }
}
