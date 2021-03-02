package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
