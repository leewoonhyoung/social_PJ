package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDto;

@SpringBootTest
class GuestbookServiceTest {

    @Autowired
    private GuestbookService guestbookService;


    @Test
    public void testRegister(){

        GuestbookDto guestbookDto = GuestbookDto.builder()
                .title("Sample Title")
                .content("Sample Content")
                .writer("user0")
                .build();
        System.out.println("start");
        System.out.println(guestbookService.register(guestbookDto));
        System.out.println("end");
    }

}