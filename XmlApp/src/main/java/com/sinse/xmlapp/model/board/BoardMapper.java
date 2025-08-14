package com.sinse.xmlapp.model.board;

import com.sinse.xmlapp.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    public List<Board> selectAll();
    public Board selectById(int board_id);
    public int insert(Board board);
    public int update(Board board);
    public int delete(int board_id);
}
