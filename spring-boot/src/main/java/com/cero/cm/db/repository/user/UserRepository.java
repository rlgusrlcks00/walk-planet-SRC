package com.cero.cm.db.repository.user;

import com.cero.cm.db.entity.User;
import com.cero.cm.db.repository.user.dsl.UserRepositoryDsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryDsl {
    User findAllByUserName(String username);

    boolean existsByUserEmail(String email);

    User findByUserEmail(String userEmail);
}
