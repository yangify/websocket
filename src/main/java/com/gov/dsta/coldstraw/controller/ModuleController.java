package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Module;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.service.ModuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping()
    public List<Module> getModules() {
        return moduleService.getModules();
    }

    @GetMapping("/{moduleId}")
    public Module getModule(@PathVariable Long moduleId) {
        return moduleService.getModule(moduleId);
    }

    @GetMapping("/{moduleId}/notifications")
    public List<Notification> getModuleNotifications(@PathVariable Long moduleId) {
        return moduleService.getNotifications(moduleId);
    }

    @PostMapping()
    public Module createModule(@RequestBody Module module) {
        return moduleService.addModule(module);
    }

    @PutMapping("/{moduleId}")
    public Module updateModule(@PathVariable Long moduleId) {
        // update details of a module
        return null;
    }

    @DeleteMapping("/{moduleId}")
    public void deleteModule(@PathVariable Long moduleId) {
        moduleService.deleteModule(moduleId);
    }
}
