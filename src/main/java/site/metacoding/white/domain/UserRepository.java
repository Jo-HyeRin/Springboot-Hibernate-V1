package site.metacoding.white.domain;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository // IOC 등록
public class UserRepository {

    // DI
    private final EntityManager em; // 스프링이 알아서 IOC에 넣어둔 것이므로 DI 가능

    public User save(User user) {
        // Persistence Context에 영속화 시키기 -> 자동 flush (트랜잭션 종료 시)
        em.persist(user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
        try {
            Optional<User> userOP = Optional.of(em
                    .createQuery(
                            "select u from User u where u.username=:username",
                            User.class)
                    .setParameter("username", username)
                    .getSingleResult());
            return userOP;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findById(Long id) {
        try {
            Optional<User> userOP = Optional.of(em
                    .createQuery(
                            "select u from User u where u.id=:id",
                            User.class)
                    .setParameter("id", id)
                    .getSingleResult());
            return userOP;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
