package com.sinse.restapp.model;

import com.sinse.restapp.domain.Board;

import java.util.List;

public interface BoardDAO {
    public List selectAll();
    public Board  selectById(int board_id);
    public void insert(Board board);
    public int update(Board board);
    public void delete(int board_id);
}
