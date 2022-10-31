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
        // Board boardPS = em.createQuery("select b from Board b where b.id = :id",
        // Board.class)
        // .setParameter("id", id)
        // .getSingleResult();

        // Board boardPS = (Board) em
        // .createNativeQuery("select * from board b inner join user u on b.user_id =
        // u.id where b.id = :id",
        // Board.class)
        // .setParameter("id", id)
        // .getSingleResult();

        // Board boardPS = em
        // .createQuery("select b from Board b join fetch b.user u where b.id = :id",
        // Board.class)
        // .setParameter("id", id)
        // .getSingleResult();

        try {
            Optional<Board> boardOP = Optional
                    .of(em.createQuery(
                            "select b from Board b join fetch b.user u where b.id = :id",
                            Board.class)
                            .setParameter("id", id)
                            .getSingleResult());
            return boardOP;
        } catch (Exception e) {
            return Optional.empty();
        }
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
