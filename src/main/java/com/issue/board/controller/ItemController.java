package com.issue.board.controller;

import com.issue.board.dto.ItemInfoDto;
import com.issue.board.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    public final ItemService itemService;


    @ResponseBody
    @PostMapping("/findByItemNameContainingOrItemCodeContaining")
    public List<ItemInfoDto> findByItemNameContainingOrItemCodeContaining(@RequestBody ItemInfoDto form){
        return itemService.findByItemNameContainingOrItemCodeContaining(form);
    }

    @ResponseBody
    @PostMapping("/save")
    public Long save(@RequestBody ItemInfoDto form){
        return itemService.save(form);
    }
}
