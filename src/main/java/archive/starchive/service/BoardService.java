package archive.starchive.service;

import archive.starchive.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Page<Board> findAll(Pageable pageable);
}
