package org.zerock.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDto<Dto, EN> {

    private List<Dto> dtoList;

    public PageResultDto(Page<EN> result, Function<EN, Dto> fn){

        dtoList = result.stream().map(fn).collect(Collectors.toList());

    }
}
