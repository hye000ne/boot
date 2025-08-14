package com.sinse.xmlapp.model.board;

import com.sinse.xmlapp.domain.Board;
import com.sinse.xmlapp.exception.BoardException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardDAO boardDAO;

    public BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    public List<Board> selectAll(){
        return boardDAO.selectAll();
    }

    public Board selectById(int board_id){
        return boardDAO.selectById(board_id);
    }

    public void insert(Board board) throws BoardException {
        boardDAO.insert(board);
    }

    public void update(Board board) throws BoardException {
        boardDAO.update(board);
    }

    public void delete(int board_id) throws BoardException {
        boardDAO.delete(board_id);
    }
}
