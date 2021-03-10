package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.exception.module.ModuleNotFoundException;
import com.gov.dsta.coldstraw.model.Module;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getModules() {
        return (List<Module>) moduleRepository.findAll();
    }

    public Module getModule(UUID moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(() -> new ModuleNotFoundException(moduleId));
    }

    public Module getModule(String moduleName) {
        return moduleRepository.findByName(moduleName).orElseThrow(() -> new ModuleNotFoundException(moduleName));
    }

    public Set<Notification> getNotifications(UUID moduleId) {
        Module module = getModule(moduleId);
        return module.getNotifications();
    }

    public Module addModule(Module module) {
        return moduleRepository.save(module);
    }

    public void deleteModule(UUID moduleId) {
        moduleRepository.deleteById(moduleId);
    }
}
