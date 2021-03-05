package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
}
