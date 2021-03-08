package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<Group, UUID> {

    Optional<Group> findByName(String name);

}
