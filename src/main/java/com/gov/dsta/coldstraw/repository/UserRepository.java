package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);

}
