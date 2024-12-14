package com.philately.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.philately.model.entity.Stamp;
import com.philately.model.entity.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query("select s.wishedStamps from User as s where s.id= :id")
    Set<Stamp> getWishedStamps(@Param("id") Long id);

    @Query("select s.purchasedStamps from User as s where s.id= :id")
    Set<Stamp> getBoughtStamps(@Param("id") Long id);

}
