package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // IOC 등록
public class UserRepository {

    // DI
    private final EntityManager em; // 스프링이 알아서 IOC에 넣어둔 것이므로 DI 가능

    public User save(User user) {
        // Persistence Context에 영속화 시키기 -> 자동 flush (트랜잭션 종료 시)
        System.out.println("ccc : 영속화 전 id는" + user.getId());
        em.persist(user);
        System.out.println("ccc : 영속화 후 id는" + user.getId());
        return user;
    }

    public User findByUsername(String username) {
        return em.createQuery("select u from User u where u.username=:username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

}
