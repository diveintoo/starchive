package archive.starchive.service;

import archive.starchive.domain.Board;
import archive.starchive.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith (MockitoExtension.class)
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @MockBean
    private BoardRepository boardRepository;

    @Test
    @DisplayName("Board 조회 테스트 : 성공")
    void testFindAll() {
        List<Board> boards = new ArrayList<>();
        for(int i = 1; i < 8; i++) {
            boards.add(Board.builder().id(Long.valueOf(i)).name("board" + Integer.toString(i)).build());
        }
        Page<Board> pagedResponse = new PageImpl<>(boards);

        when(boardRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);

        Page<Board> foundPage = boardService.findAll(PageRequest.of(0, 10));

        assertEquals(7, foundPage.getTotalElements());
        assertEquals("board4", foundPage.getContent().get(3).getName());
        assertEquals(6, foundPage.getContent().get(5).getId());
    }
}