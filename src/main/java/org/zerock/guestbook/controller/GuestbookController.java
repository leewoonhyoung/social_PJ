package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.guestbook.dto.PageRequestDto;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Slf4j
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    @GetMapping("/")
    public String index() {

        return "redirect:/guestbook/list";
    }


    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDTO, Model model){

        log.info("list............." + pageRequestDTO);

        model.addAttribute("result", guestbookService.getList(pageRequestDTO));

    }
}
