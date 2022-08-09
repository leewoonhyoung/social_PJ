package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDto;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

@Service
@Slf4j
@RequiredArgsConstructor // final 생성자 생성
public class GuestbookServiceImpl implements  GuestbookService{

    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDto dto) {

        log.info("DTO----------");
        log.info(String.valueOf(dto));

        Guestbook entity = dtoToEntity(dto);

        log.info(String.valueOf(entity));

        guestbookRepository.save(entity);

        return entity.getGno();
    }


}
