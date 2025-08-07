package com.sinse.restapp.model;

import com.sinse.restapp.domain.Board;
import com.sinse.restapp.exception.BoardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("mybatisBoardDAO")
public class MyBatisBoardDAO implements BoardDAO{
    private BoardMapper boardMapper;

    MyBatisBoardDAO(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public List selectAll() {
        return boardMapper.selectAll();
    }

    @Override
    public Board selectById(int board_id) {
        return boardMapper.selectById(board_id);
    }

    @Override
    public void insert(Board board) throws DataAccessException {
        try {
            boardMapper.insert(board);
        } catch (DataAccessException e) {
            throw new BoardException("글 등록 실패", e);
        }
    }

    @Override
    public Board update(Board board) throws DataAccessException {
        try {
            boardMapper.update(board);
            return board;
        } catch (DataAccessException e) {
            throw new BoardException("글 수정 실패", e);
        }
    }

    @Override
    public void delete(int board_id) throws DataAccessException {
        try {
            boardMapper.delete(board_id);
        } catch (DataAccessException e) {
            throw new BoardException("글 삭제 실패", e);
        }
    }
}