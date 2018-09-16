package doan.zera.jsp.repositories;

import doan.zera.jsp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    User findUsersByUsername(String username);
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "insert into users(username, password, enabled) values (?1,?2,true)")
    void snewuser(String username,String password);
    @Query(nativeQuery = true, value = "select u.password from users u where u.username=?1")
    String getPasswordByUsername(String username);

}
