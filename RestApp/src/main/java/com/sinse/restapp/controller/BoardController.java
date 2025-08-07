package com.sinse.restapp.controller;

import com.sinse.restapp.domain.Board;
import com.sinse.restapp.model.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * 게시판 목록 요청 처리
     */
    @GetMapping("/boards")
    public List selectAll (){
        return boardService.selectAll();
    }

    @PostMapping("/boards")
    public ResponseEntity<String> insertBoard(@RequestBody Board board){
        boardService.insert(board);
        return ResponseEntity.ok().body("inserted successfully");
    }

    @GetMapping("/boards/{board_id}")
    public Board selectBoard(@PathVariable("board_id") Integer border_id){
        return boardService.selectById(border_id);
    }

    @PutMapping("/boards/{board_id}")
    public ResponseEntity<String> updateBoard(@PathVariable("board_id") Integer board_id, @RequestBody Board board){
        board.setBoard_id(board_id); // 경로로 전송된 파라미터를 다시 한번 확인 차 모델에 대입
        boardService.update(board);
        return ResponseEntity.ok().body("updated successfully");
    }

    @DeleteMapping("/boards/{board_id}")
    public ResponseEntity<String> deleteBoard(@PathVariable("board_id") Integer board_id){
        boardService.delete(board_id);
        return ResponseEntity.ok().body("deleted successfully");
    }
}
