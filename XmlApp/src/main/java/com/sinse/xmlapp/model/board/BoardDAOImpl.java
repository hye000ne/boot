package com.sinse.xmlapp.model.board;

import com.sinse.xmlapp.domain.Board;
import com.sinse.xmlapp.exception.BoardException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDAOImpl implements BoardDAO {
    private BoardMapper boardMapper;

    public  BoardDAOImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }
    @Override
    public List<Board> selectAll() {
        return boardMapper.selectAll();
    }

    @Override
    public Board selectById(int board_id) {
        return boardMapper.selectById(board_id);
    }

    @Override
    public void insert(Board board) throws BoardException {
        try {
            boardMapper.insert(board);
        } catch (DataAccessException e) {
            throw new BoardException(e);
        }
    }

    @Override
    public void update(Board board) throws BoardException {
        try {
            boardMapper.update(board);
        } catch (DataAccessException e) {
            throw new BoardException(e);
        }
    }

    @Override
    public void delete(int board_id) throws BoardException {
        try {
            boardMapper.delete(board_id);
        } catch (DataAccessException e) {
            throw new BoardException(e);
        }
    }
}