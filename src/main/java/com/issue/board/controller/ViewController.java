package com.issue.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {

    @GetMapping("/")
    public String mainPage(){
        return "board/boardList";
    }

    @GetMapping("/board/list")
    public String boardList(){
        return "board/boardList";
    }

    @GetMapping("/board/regist")
    public String boardRegister(){
        return "board/boardDetail";
    }

    @GetMapping("/board/detail/{id}")
    public String boardDetail(@PathVariable Long id, Model model){
        model.addAttribute("id", id);
        return "board/boardDetail";
    }

    @GetMapping("/item/itemInfo")
    public String itemInfo(){
        return "item/itemInfo";
    }

}
