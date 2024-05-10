package archive.starchive.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    private String password;

    @Column(nullable = false, length = 15)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role role;
}
