package org.zerock.guestbook.service;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.zerock.guestbook.dto.GuestbookDto;
import org.zerock.guestbook.dto.PageRequestDto;
import org.zerock.guestbook.dto.PageResultDto;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest
class GuestbookServiceTest {

    @Autowired
    private GuestbookService guestbookService;

    @Autowired
    private GuestbookService service;


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

    @Test
    public void testList(){

        PageRequestDto pageRequestDTO = PageRequestDto.builder().page(1).size(10).build();

        PageResultDto<GuestbookDto, Guestbook> resultDTO = service.getList(pageRequestDTO);

        for (GuestbookDto guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }
    }

}