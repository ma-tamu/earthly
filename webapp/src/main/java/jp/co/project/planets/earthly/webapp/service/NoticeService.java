package jp.co.project.planets.earthly.webapp.service;

import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.common.model.dto.NoticeEntryDto;
import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.core.utils.DateUtils;
import jp.co.project.planets.earthly.schema.db.entity.Notice;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.repository.NoticeRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.NoticeEditDto;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MessageSource messageSource;

    public NoticeService(final NoticeRepository noticeRepository, final MessageSource messageSource) {
        this.noticeRepository = noticeRepository;
        this.messageSource = messageSource;
    }

    @Transactional
    public Page<Notice> search(final String title, final String startAt, final String endAt,
        final Pageable pageable, final Account account) {

        final var startDate = DateUtils.parseOrNull(startAt, "yyyy-MM-dd HH:mm:ss");
        final var endDate = DateUtils.parseOrNull(endAt, "yyyy-MM-dd HH:mm:ss");
        final var noticeSearchResultDto = noticeRepository.findByTitleAndPublicationDate(title, startDate, endDate,
                pageable);
        return new PageImpl<>(noticeSearchResultDto.noticeList(), pageable, noticeSearchResultDto.total());
    }

    @Transactional
    public void validateEntry(final Account account) {
        if (!account.permissions().contains(PermissionEnum.ADD_NOTICE)) {
            throw new ForbiddenException(ErrorCode.EWA4XX045);
        }
    }

    @Transactional
    public String create(final NoticeEntryDto noticeEntryDto, final Account account) {

        validateEntry(account);

        final var notice = new Notice(null, noticeEntryDto.title(), noticeEntryDto.body(), noticeEntryDto.startAt(),
                noticeEntryDto.endAt(), noticeEntryDto.emphasis(), null, account.id(), null, account.id(), false);
        noticeRepository.insert(notice);
        return noticeRepository.findByTitleAndBody(noticeEntryDto.title(), noticeEntryDto.body()).map(Notice::getId)
                .orElseThrow();
    }

    @Transactional
    public Notice get(final String id, final Account account) {
        return noticeRepository.findByPrimaryKey(id).orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX046));
    }

    @Transactional
    public String update(final String id, final NoticeEditDto dto, final Account account) {

        if (!account.permissions().contains(PermissionEnum.EDIT_NOTICE)) {
            throw new ForbiddenException(ErrorCode.EWA4XX045);
        }

        final var notice = new Notice(id, dto.title(), dto.body(), dto.startAt(), dto.endAt(), dto.emphasis(), null,
                null, null, account.id(), null);
        noticeRepository.update(notice);
        return messageSource.getMessage(MessageKey.UPDATE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    public void delete(final String id, final Account account) {

        if (!account.permissions().contains(PermissionEnum.EDIT_NOTICE)) {
            throw new ForbiddenException(ErrorCode.EWA4XX045);
        }

        final var notice = new Notice(id, null, null, null, null, null, null,
                null, null, account.id(), true);
        noticeRepository.update(notice);
    }
}
