package org.zerock.guestbook.service;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zerock.guestbook.dto.GuestbookDto;
import org.zerock.guestbook.dto.PageRequestDto;
import org.zerock.guestbook.dto.PageResultDto;
import org.zerock.guestbook.entity.Guestbook;

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

    @Test
    public void testList(){

        PageRequestDto pageRequestDTO = PageRequestDto.builder().page(1).size(10).build();

        PageResultDto<GuestbookDto, Guestbook> resultDTO = guestbookService.getList(pageRequestDTO);

        for (GuestbookDto guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }
    }

    @Test
    @DisplayName("인덱스 테스트")
    public void testList2(){
        PageRequestDto pageRequestDto = PageRequestDto.builder().page(1).size(10).build();
        PageResultDto<GuestbookDto, Guestbook> resultDto = guestbookService.getList(pageRequestDto);

        System.out.println("resultDto.isPrev() = " + resultDto.isPrev());
        System.out.println("resultDto.isNext() = " + resultDto.isNext());
        System.out.println("resultDto.getTotalPage() = " + resultDto.getTotalPage());

        System.out.println("-----------------------------------------------------");
        for(GuestbookDto guestbookDto : resultDto.getDtoList()){
            System.out.println("guestbookDto = " + guestbookDto);
        }
        System.out.println("-----------------------------------------------------");
        resultDto.getPageList().forEach(i -> System.out.println(i));

    }
    @Test
    public void testSearch(){
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .type("tc") // 검색 조건
                .keyword("한글") // 검색 키워드
                .build();

        PageResultDto<GuestbookDto, Guestbook> resultDTO = guestbookService.getList(pageRequestDto);

        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("-------------------------------------");
        for (GuestbookDto guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("========================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }



}