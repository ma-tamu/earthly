package jp.co.project.planets.earthly.webapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.common.model.dto.NoticeEntryDto;
import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.Notice;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.repository.NoticeRepository;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(final NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Transactional
    public Page<Notice> search(final String title, final String startDate, final String endDate,
        final Pageable pageable, final Account account) {
        return null;
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

}
