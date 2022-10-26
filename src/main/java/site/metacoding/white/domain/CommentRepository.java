package site.metacoding.white.domain;

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

}
