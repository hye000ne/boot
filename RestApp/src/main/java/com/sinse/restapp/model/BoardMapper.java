package com.sinse.restapp.model;

import com.sinse.restapp.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    public List selectAll();
    public Board selectById(int board_id);
    public void insert(Board board);
    public int update(Board board);
    public void delete(int board_id);
}
