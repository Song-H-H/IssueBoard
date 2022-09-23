package com.issue.board.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;



@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long id;

    private String itemCode;

    @Size(max = 4000)
    private String itemName;

}
