package archive.starchive.repository;

import archive.starchive.config.JpaAuditingConfig;
import archive.starchive.domain.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(JpaAuditingConfig.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        for(int i = 1; i < 11; i++) {
            Board board = Board.builder().name("board" + Integer.toString(i)).build();
            boardRepository.save(board);
        }
    }

    @Test
    @DisplayName("Board 조회 테스트 : 성공")
    void testFindAll() {
        Page<Board> foundPage = boardRepository.findAll(PageRequest.of(0, 10));

        assertEquals("board8", foundPage.getContent().get(7).getName());
        assertEquals(3, foundPage.getContent().get(2).getId());
    }
}