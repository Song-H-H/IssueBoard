package com.issue.board.repository;

import com.issue.board.domain.ItemInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemInfo, Long> {

    List<ItemInfo> findByItemNameContainingOrItemCodeContaining(String name, String code);

}

