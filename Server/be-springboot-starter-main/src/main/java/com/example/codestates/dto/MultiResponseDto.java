package com.example.codestates.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiResponseDto<T> {

    private List<T>  data;
    private PageInfo pageinfo;


    public MultiResponseDto(List<T> data, Page page){
        this.data =data;
        this.pageinfo = new PageInfo(page.getNumber() + 1, page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

}
