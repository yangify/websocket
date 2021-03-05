package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);

}
