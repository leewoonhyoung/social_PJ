package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
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
    public PageResultDto<GuestbookDto, Guestbook> getList(PageRequestDto requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder builder = getSearch(requestDTO);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); //Querydsl 사용

        Function<Guestbook, GuestbookDto> fn = (entity -> entityToDto(entity));

        return new PageResultDto<>(result, fn);
    }


    @Override
    public GuestbookDto read(Long gno){
        Optional<Guestbook> result = guestbookRepository.findById(gno);

        return result.isPresent()? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno);

    }

    @Override
    public void modify(GuestbookDto guestbookDto) {

        Optional<Guestbook> result = guestbookRepository.findById(guestbookDto.getGno());

        if (result.isPresent()){
            Guestbook entity = result.get();
            entity.changeTitle(guestbookDto.getTitle());
            entity.changeContent(guestbookDto.getContent());
            guestbookRepository.save(entity);

        }
    }
    private BooleanBuilder getSearch(PageRequestDto requestDto){

        String type = requestDto.getType();
        String keyword = requestDto.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook  qGuestbook = QGuestbook.guestbook;

        //조건 1 gno > 0
        BooleanExpression booleanExpression = qGuestbook.gno.gt(0L);
        booleanBuilder.and(booleanExpression);

        //검증 -> 검색 조건이 없는 경우
        if (type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        //t , c, w 를 where 조건에 넣어 확인한다.
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("w")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    }
}
