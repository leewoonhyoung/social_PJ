package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDto;
import org.zerock.guestbook.dto.PageRequestDto;
import org.zerock.guestbook.dto.PageResultDto;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.function.Function;

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

    @Override
    public PageResultDto<GuestbookDto, Guestbook> getList(PageRequestDto requestDto) {

        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = guestbookRepository.findAll(pageable);

        Function<Guestbook, GuestbookDto> fn = (entity -> entityToDto(entity));

        return new PageResultDto<>(result, fn);
    }

    //PageResultDto<GuestbookDto, Guestbook> getList(PageRequestDto requestDto);


}
