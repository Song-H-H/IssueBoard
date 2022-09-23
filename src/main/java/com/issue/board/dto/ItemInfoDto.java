package com.issue.board.dto;


import com.issue.board.core.annotation.ExcelColumn;
import com.issue.board.domain.ItemInfo;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoDto {

    private Long id;
    @ExcelColumn(headerName = "ITEM CDOE")
    private String itemCode;
    @ExcelColumn(headerName = "ITEM NAME")
    private String itemName;

    private String searchItemNameAndCode;

    public static ItemInfoDto of(ItemInfo itemInfo) {
        return ItemInfoDto.builder()
                .id(itemInfo.getId())
                .itemCode(itemInfo.getItemCode())
                .itemName(itemInfo.getItemName())
                .build();
    }

    public static List<ItemInfoDto> of(List<ItemInfo> itemInfoList) {
        if (itemInfoList == null) return Collections.emptyList();
        return itemInfoList.stream().map(ItemInfoDto::of).collect(Collectors.toList());
    }

    public ItemInfo toEntity(){
        return ItemInfo.builder()
                .id(this.id)
                .itemCode(this.itemCode)
                .itemName(this.itemName)
                .build();
    }
}
