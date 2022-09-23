package com.issue.board.dto;

import com.issue.board.core.annotation.ExcelColumn;
import com.issue.board.domain.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto{

    static final DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Long id;

    private ItemInfoDto itemInfoDto;

    @ExcelColumn(headerName = "ISSUE")
    private String issue;

    private LocalDateTime createdAt;

    @ExcelColumn(headerName = "CreateAt")
    private String createdAtString;

    private String searchItemInfoOrIssue;

    private PageDto pageDto;

    public static BoardDto of(Board board){
        return ofBuilder(board, null);
    }

    public static BoardDto of(Board board, PageDto pageDto){
        return ofBuilder(board, pageDto);
    }

    private static BoardDto ofBuilder(Board board, PageDto pageDto){
        if(pageDto == null){
            pageDto = new PageDto();
        }

        return BoardDto.builder()
                .id(board.getId())
                .itemInfoDto(new ItemInfoDto().of(board.getItemInfo()))
                .issue(board.getIssue())
                .createdAt(board.getCreatedAt())
                .createdAtString(board.getCreatedAt().format(ofPattern))
                .pageDto(pageDto)
                .build();
    }


    public static List<BoardDto> of(List<Board> boardList, PageDto pageDto) {
        if (boardList == null) return Collections.emptyList();
        return boardList.stream().map(x -> BoardDto.of(x,pageDto)).collect(Collectors.toList());
    }

    public Board toEntity() {
        return Board.builder()
                .id(this.id)
                .itemInfo(this.itemInfoDto.toEntity())
                .issue(this.issue)
                .createdAt(this.createdAt)
                .build();
    }
}
