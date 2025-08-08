package com.sinse.restapp.model;

import com.sinse.restapp.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("jpaBoardDAO")
public class JpaBoardDAO  implements BoardDAO{
    // JPA 쿼리 수행 객체 EntityManager
    @PersistenceContext  // JPA의 EntityManager를 자동으로 주입받음
    private EntityManager em;

    public JpaBoardDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public List selectAll() {
        return em.createQuery("select b from Board b").getResultList();
    }

    @Override
    public Board selectById(int board_id) {
        return em.find(Board.class, board_id);
    }

    @Override
    @Transactional
    public void insert(Board board) {
        log.debug("board: " + board);
        em.persist(board);
    }

    @Override
    @Transactional
    public int update(Board board) {
          return 0;
    }

//    @Override
//    @Transactional
//    public Board update(Board board) {
//          return em.merge(board);
//    }

    @Override
    @Transactional
    public void delete(int board_id) {
        Board board = em.find(Board.class, board_id);
        if(board!=null) em.remove(board);
    }
}