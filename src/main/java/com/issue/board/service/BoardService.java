package com.issue.board.service;

import com.issue.board.core.annotation.ExcelColumn;
import com.issue.board.domain.Board;
import com.issue.board.dto.BoardDto;
import com.issue.board.dto.BoardExportDto;
import com.issue.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.issue.board.util.ReflectionUtil.getAllFields;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardDto> findByItemInfoOrIssueContaining(BoardDto boardDto) {
        String searchItemInfoOrIssue = boardDto.getSearchItemInfoOrIssue();

        List<Board> boardList = boardRepository.findByItemInfoItemNameContainingOrItemInfoItemCodeContainingOrIssueContaining(searchItemInfoOrIssue, searchItemInfoOrIssue, searchItemInfoOrIssue, boardDto.getPageDto().of());
        int selectedCount = boardRepository.countByItemInfoItemNameContainingOrItemInfoItemCodeContainingOrIssueContaining(searchItemInfoOrIssue, searchItemInfoOrIssue, searchItemInfoOrIssue);
        boardDto.getPageDto().setTotalPageCount(selectedCount);
        List<BoardDto> BoardDtoList = BoardDto.of(boardList, boardDto.getPageDto());
        return BoardDtoList;
    }

    public Long save(BoardDto boardDto) {
        Board board = boardRepository.save(boardDto.toEntity());
        return board.getId();
    }

    public BoardDto getById(Long id) {
        Board board = boardRepository.findById(id).orElse(null);

        if(board == null){
            return new BoardDto();
        }

        return BoardDto.of(board);
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public XSSFWorkbook exportExcel(BoardDto boardDto) throws IllegalAccessException {
        String searchItemInfoOrIssue = boardDto.getSearchItemInfoOrIssue();
        List<Board> boardList = boardRepository.findByItemInfoItemNameContainingOrItemInfoItemCodeContainingOrIssueContaining(searchItemInfoOrIssue, searchItemInfoOrIssue, searchItemInfoOrIssue);
        List<BoardExportDto> BoardDtoList = BoardExportDto.of(boardList.stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId())).collect(Collectors.toList()));

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("DeleteDuplicateExitByMaxTime");
        sheet.setDefaultColumnWidth(50);

        List<Field> BoardExportDtoFields = getAllFields(BoardExportDto.class);


        int rownum = 0;
        int cellIndex = 0;
        for (BoardExportDto data : BoardDtoList) {
                Row row = sheet.createRow(rownum++);
                cellIndex = 0;
                for (Field field : BoardExportDtoFields) {
                    if (field.isAnnotationPresent(ExcelColumn.class)) {
                        field.setAccessible(true);
                        Object object = field.get(data);
                        Cell cell = row.createCell(cellIndex++);
                        if(object == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(object.toString());
                        }
                    }
                }
            }

        return workbook;
    }
}
