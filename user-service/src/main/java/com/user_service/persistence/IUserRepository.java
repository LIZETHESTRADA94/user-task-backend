package com.user_service.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.user_service.model.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    
    @Query("""
        SELECT u FROM User u
        WHERE CAST(u.id AS string) = :query
        OR LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<User> searchByAny(@Param("query") String query);
}
