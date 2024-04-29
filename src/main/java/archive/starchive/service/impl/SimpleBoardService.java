package archive.starchive.service.impl;

import archive.starchive.domain.Board;
import archive.starchive.repository.BoardRepository;
import archive.starchive.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SimpleBoardService implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
}
