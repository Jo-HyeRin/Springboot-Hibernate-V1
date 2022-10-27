package site.metacoding.white.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    // extends JpaRepository<User, Long> 이게 붙어있으면 알아서 !

    // @Query(value = "select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);
    // findByEmail, findByGender 처럼 뒤에 붙은 이름을 where절에 자동으로 걸어준다.
}
