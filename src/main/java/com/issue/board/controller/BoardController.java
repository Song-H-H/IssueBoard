package com.issue.board.controller;

import com.issue.board.dto.BoardDto;
import com.issue.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @ResponseBody
    @PostMapping("/findByItemInfoOrIssueContaining")
    public List<BoardDto> findByItemInfoOrIssueContaining(@RequestBody BoardDto form){
        return boardService.findByItemInfoOrIssueContaining(form);
    }

    @ResponseBody
    @PostMapping("/save")
    public Long save(@RequestBody BoardDto form){
        return boardService.save(form);
    }

    @ResponseBody
    @GetMapping("/getById/{id}")
    public BoardDto getById(@PathVariable Long id){
        return boardService.getById(id);
    }

    @ResponseBody
    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteById(@RequestBody BoardDto form){
        boardService.deleteById(form.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted.");
    }

    @ResponseBody
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody BoardDto form, HttpServletResponse response) throws IOException, IllegalAccessException {
        XSSFWorkbook sheets = boardService.exportExcel(form);
        sheets.write(response.getOutputStream());
        sheets.close();
    }
}
