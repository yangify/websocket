package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.Module;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModuleRepository extends CrudRepository<Module, UUID> {
}
