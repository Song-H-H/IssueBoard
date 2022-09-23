package com.issue.board.service;


import com.issue.board.domain.ItemInfo;
import com.issue.board.dto.ItemInfoDto;
import com.issue.board.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    public final ItemRepository repository;

    public List<ItemInfoDto> findByItemNameContainingOrItemCodeContaining(ItemInfoDto itemInfoDto) {
        List<ItemInfo> itemInfoList = repository.findByItemNameContainingOrItemCodeContaining(itemInfoDto.getSearchItemNameAndCode(), itemInfoDto.getSearchItemNameAndCode());
        return ItemInfoDto.of(itemInfoList);
    }

    public Long save(ItemInfoDto itemInfoDto) {
        ItemInfo itemInfo = repository.save(itemInfoDto.toEntity());
        return itemInfo.getId();
    }
}
