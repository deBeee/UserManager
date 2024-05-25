package com.usermanager.domain.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User, Long> {
    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsById(Long id);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findById(Long id);

    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.username = :#{#user.username}, u.password = :#{#user.password},
            u.email = :#{#user.email}, u.isEnabled = :#{#user.enabled}
            WHERE u.id = :id
            """)
    void updateById(Long id, User user);
}
