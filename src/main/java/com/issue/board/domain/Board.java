package com.issue.board.domain;

import com.issue.board.dto.ItemInfoDto;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(name = "board.findBy", attributeNodes = @NamedAttributeNode("itemInfo"))
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @BatchSize(size = 100)
    private ItemInfo itemInfo;

    @Size(max = 4000)
    private String issue;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
