package site.metacoding.white.domain;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

    public Board save(Board board) {
        em.persist(board); // insert 됨. persist : 영속화
        return board;
    }

    // 게시글 상세보기
    public Optional<Board> findById(Long id) {
        Optional<Board> boardOP = Optional
                .ofNullable(em.createQuery("select b from Board b where b.id = :id", Board.class)
                        .setParameter("id", id)
                        .getSingleResult());
        return boardOP;
    }

    public List<Board> findAll() {
        List<Board> boardList = em.createQuery("select b from Board b", Board.class)
                .getResultList();
        return boardList;
    }

    public void deleteById(Long id) {
        em.createQuery("delete from Board b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
