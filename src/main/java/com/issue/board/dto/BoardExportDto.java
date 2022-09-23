package com.issue.board.dto;

import com.issue.board.core.annotation.ExcelColumn;
import com.issue.board.domain.Board;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardExportDto {

    static final DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExcelColumn(headerName = "ITEM NAME")
    private String itemName;

    @ExcelColumn(headerName = "ITEM CDOE")
    private String itemCode;

    @ExcelColumn(headerName = "ISSUE")
    private String issue;

    @ExcelColumn(headerName = "CreateAt")
    private String createdAtString;

    public static BoardExportDto of(Board board){
        return BoardExportDto.builder()
                .itemName(board.getItemInfo().getItemName())
                .itemCode(board.getItemInfo().getItemCode())
                .issue(board.getIssue())
                .createdAtString(board.getCreatedAt().format(ofPattern))
                .build();
    }

    public static List<BoardExportDto> of(List<Board> boardList) {
        if (boardList == null) return Collections.emptyList();
        return boardList.stream().map(BoardExportDto::of).collect(Collectors.toList());
    }
}
