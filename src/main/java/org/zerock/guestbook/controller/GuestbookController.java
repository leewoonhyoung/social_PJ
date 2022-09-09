package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDto;
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
    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDto dto, RedirectAttributes redirectAttributes){
        log.info("dto..." + dto);

        Long gno = guestbookService.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping("/read")
    public void read(long gno, @ModelAttribute("requestDto") PageRequestDto pageRequestDto, Model model){
        log.info("gno:" + gno);

        GuestbookDto dto = guestbookService.read(gno);

        model.addAttribute("dto", dto);
    }
//    @GetMapping({"/read", "/modify"})
//    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDto requestDto, Model model){
//        log.info("gno :" + gno);
//
//        GuestbookDto dto = guestbookService.read(gno);
//
//        model.addAttribute("dto" , dto);
//    }

    @PostMapping("/modify")
    public String modify(GuestbookDto guestbookDto, @ModelAttribute("requestDTO") PageRequestDto requestDto, RedirectAttributes redirectAttributes){
        log.info("post modify------------------------");
        log.info("dit :" + guestbookDto);

        guestbookService.modify(guestbookDto);

        redirectAttributes.addAttribute("page", requestDto.getPage());
        redirectAttributes.addAttribute("type", requestDto.getType());
        redirectAttributes.addAttribute("keyword", requestDto.getKeyword());
        redirectAttributes.addAttribute("gno", guestbookDto.getGno());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        log.info("gno : " + gno);

        guestbookService.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
}
