package archive.starchive.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 50)
    private String title;

    private String subject;

    private String source;

    @Column(length = 1000)
    private String content;

    private LocalDate occurTime;

    private Boolean isDeleted;

    private int viewCnt;

    private int blameCnt;

    private int commentCnt;

    private LocalDateTime createdTime;

    private LocalDateTime lastModifiedTime;
}
