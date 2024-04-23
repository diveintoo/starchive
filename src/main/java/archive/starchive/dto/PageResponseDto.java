package archive.starchive.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageResponseDto<T> {
    private List<T> content;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isLast;

    @Builder
    public PageResponseDto(List<T> content, int pageSize, long totalElements, int totalPages, boolean isLast) {
        this.content = content;
        this.size = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }
}
