package site.metacoding.white.domain;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CommentRepository {

    private final EntityManager em;

    // 댓글 쓰기
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    // 댓글 삭제
    public void deleteById(Long id) {
        em.createQuery("delete from Comment c where c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    // 댓글 하나 보기
    public Optional<Comment> findById(Long id) {
        try {
            Optional<Comment> commentOP = Optional
                    .of(em.createQuery("select c from Comment c where c.id = :id", Comment.class)
                            .setParameter("id", id)
                            .getSingleResult());
            return commentOP;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
