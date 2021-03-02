package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);

    @Transactional
    void deleteUserById(Long id);
}
