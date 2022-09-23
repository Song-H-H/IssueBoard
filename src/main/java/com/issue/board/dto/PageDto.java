package com.issue.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageDto {

    private int pageNumber;
    private int size = 10;
    private int totalPages;

    private Boolean showFirst;

    private Boolean showLast;

    private List<Integer> pages = new ArrayList<>();

    public Pageable of() {
        return PageRequest.of(this.pageNumber, size, Sort.by("id").descending());
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPages = totalPageCount < size ? 0 :(int) Math.ceil((double) totalPageCount / this.size);

        this.showFirst = this.pageNumber == 0 ? true : false;
        this.showLast = this.totalPages-1 == this.pageNumber ? true : false;

        this.pages.clear();
        int endIndex = this.pageNumber+3;
        int startIndex = this.pageNumber -2;
        startIndex = this.totalPages - this.pageNumber == 2 ? startIndex - 1 : startIndex;
        startIndex = this.totalPages - this.pageNumber == 1 ? startIndex - 2 : startIndex;
        for(int i = startIndex; i < endIndex; i++){
            if(i >= 0 && i < this.totalPages){
                this.pages.add(i);
            }else if(i < this.totalPages){
                endIndex++;
            }
        }
    }
}
