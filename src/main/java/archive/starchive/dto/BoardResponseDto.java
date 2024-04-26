package archive.starchive.dto;

import archive.starchive.domain.Board;
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

    public static BoardResponseDto toDto(Board entity) {
        return BoardResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
