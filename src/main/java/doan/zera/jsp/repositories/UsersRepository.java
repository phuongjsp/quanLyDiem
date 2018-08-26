package doan.zera.jsp.repositories;

import doan.zera.jsp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<User, Integer> {
    User findUsersByUsername(String username);

    @Query(nativeQuery = true, value = "select u.password from users u where u.username=?1")
    String getPasswordByUsername(String username);

}
