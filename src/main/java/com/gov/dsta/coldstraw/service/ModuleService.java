package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.exception.Module.ModuleNotFoundException;
import com.gov.dsta.coldstraw.model.Module;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getModules() {
        return (List<Module>) moduleRepository.findAll();
    }

    public Module getModule(Long moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(() -> new ModuleNotFoundException(moduleId));
    }

    public List<Notification> getNotifications(Long moduleId) {
        Module module = getModule(moduleId);
        return module.getNotifications();
    }

    public Module addModule(Module module) {
        return moduleRepository.save(module);
    }

    public void deleteModule(Long moduleId) {
        moduleRepository.deleteById(moduleId);
    }
}
