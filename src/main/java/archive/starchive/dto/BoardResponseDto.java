package archive.starchive.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private String name;

    @Builder
    public BoardResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
