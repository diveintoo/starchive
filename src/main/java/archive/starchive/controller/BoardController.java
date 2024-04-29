package archive.starchive.controller;

import archive.starchive.dto.BoardResponseDto;
import archive.starchive.dto.PageResponseDto;
import archive.starchive.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping(value = "/boards")
    public ResponseEntity<PageResponseDto> getBoards(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardResponseDto> boardPage = boardService.findAll(pageable)
                .map(b -> BoardResponseDto.toDto(b));

        PageResponseDto<BoardResponseDto> responsePage = PageResponseDto.<BoardResponseDto>builder()
                .content(boardPage.getContent())
                .pageSize(boardPage.getSize())
                .totalElements(boardPage.getTotalElements())
                .totalPages(boardPage.getTotalPages())
                .isLast(boardPage.isLast())
                .build();

        return ResponseEntity.ok(responsePage);
    }
}
