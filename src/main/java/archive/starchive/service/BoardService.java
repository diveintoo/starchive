package archive.starchive.service;

import archive.starchive.dto.BoardResponseDto;
import archive.starchive.dto.PageResponseDto;
import archive.starchive.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public PageResponseDto findAll(Pageable pageable) {
         Page<BoardResponseDto> boardPage = boardRepository.findAll(pageable).map(
                 boardEntity -> new BoardResponseDto(boardEntity.getId(), boardEntity.getName()));
         PageResponseDto<BoardResponseDto> boardPageDto = new PageResponseDto<>(boardPage.getContent(), boardPage.getSize(), boardPage.getTotalElements(), boardPage.getTotalPages(), boardPage.isLast());
         return boardPageDto;
    }
}
