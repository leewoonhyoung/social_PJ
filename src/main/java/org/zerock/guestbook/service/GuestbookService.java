package org.zerock.guestbook.service;

import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDto;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookService {

    Long register(GuestbookDto dto);

    default Guestbook dtoToEntity(GuestbookDto dto){

        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }
}
