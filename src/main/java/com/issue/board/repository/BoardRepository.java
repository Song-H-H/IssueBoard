package com.issue.board.repository;

import com.issue.board.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph("board.findBy")
    List<Board> findByItemInfoItemNameContainingOrItemInfoItemCodeContainingOrIssueContaining(String name, String code, String issue, Pageable of);

    @EntityGraph("board.findBy")
    List<Board> findByItemInfoItemNameContainingOrItemInfoItemCodeContainingOrIssueContaining(String name, String code, String issue);

    int countByItemInfoItemNameContainingOrItemInfoItemCodeContainingOrIssueContaining(String name, String code, String issue);
}
