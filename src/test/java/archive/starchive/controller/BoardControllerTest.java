package archive.starchive.controller;

import archive.starchive.domain.Board;
import archive.starchive.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @Test
    @DisplayName("GET /boards 테스트 : 성공")
    void testGetBoards() throws Exception{
        List<Board> boards = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            boards.add(new Board(Long.valueOf(i), "board" + Integer.toString(i)));
        }
        Page<Board> pagedResponse = new PageImpl<>(boards);
        when(boardService.findAll(any(Pageable.class))).thenReturn(pagedResponse);

        this.mockMvc.perform(get("/boards"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("board0"))
                .andExpect(jsonPath("$.content[3].id").value(3));
    }
}