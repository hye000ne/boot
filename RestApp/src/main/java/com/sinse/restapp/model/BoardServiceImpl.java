package com.sinse.restapp.model;

import com.sinse.restapp.domain.Board;
import com.sinse.restapp.exception.BoardException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements  BoardService {
    private BoardDAO boardDAO;

    public BoardServiceImpl(@Qualifier("mybatisBoardDAO") BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    @Override
    public List selectAll() {
        return boardDAO.selectAll();
    }

    @Override
    public Board selectById(int board_id) {
        return boardDAO.selectById(board_id);
    }

    @Override
    public void insert(Board board) throws DataAccessException {
        try {
            boardDAO.insert(board);
            //이 서비스 객체가 특정 DB 연동 기술에 국한된 것이 아니라
            // 모든 기술에 중립적이어야 하므로 예외 객체 조차도 상위의 중립적인 예외일수록
            // 서비스가 계층이 유연해 질 수 있다.
        } catch (DataAccessException e) {
            throw new BoardException("글 등록 실패", e);
        }
    }

    @Override
    public Board update(Board board) throws DataAccessException {
        try {
            return boardDAO.update(board);
        } catch (DataAccessException e) {
            throw new BoardException("글 수정 실패", e);
        }
    }

    @Override
    public void delete(int board_id) throws DataAccessException {
        try {
            boardDAO.delete(board_id);
        } catch (Exception e) {
            throw new RuntimeException("글 삭제 실패", e);
        }
    }
}