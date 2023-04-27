package com.github.zjor.repository;

import com.github.zjor.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface JPAUserRepository extends CrudRepository<User, Long> {
}
