package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.assembler.ModuleAssembler;
import com.gov.dsta.coldstraw.model.Module;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.service.ModuleService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/modules")
public class ModuleController {

    private final ModuleService moduleService;
    private final ModuleAssembler moduleAssembler;

    public ModuleController(ModuleService moduleService, ModuleAssembler moduleAssembler) {
        this.moduleService = moduleService;
        this.moduleAssembler = moduleAssembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<Module>> getModules() {
        List<EntityModel<Module>> modules = moduleService
                .getModules()
                .stream()
                .map(moduleAssembler::toModel)
                .collect(Collectors.toList());
        return moduleAssembler.toCollectionModel(modules);
    }

    @GetMapping("/{moduleId}")
    public EntityModel<Module> getModule(@PathVariable Long moduleId) {
        Module module = moduleService.getModule(moduleId);
        return moduleAssembler.toModel(module);
    }

    @GetMapping("/{moduleId}/notifications")
    public List<Notification> getModuleNotifications(@PathVariable Long moduleId) {
        return moduleService.getNotifications(moduleId);
    }

    @PostMapping()
    public ResponseEntity<Object> createModule(@RequestBody Module module) {
        Module createdModule = moduleService.addModule(module);
        EntityModel<Module> moduleModel = moduleAssembler.toModel(createdModule);
        return ResponseEntity
                .created(moduleModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .build();
    }

    @PutMapping("/{moduleId}")
    public Module updateModule(@PathVariable Long moduleId) {
        // update details of a module
        return null;
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long moduleId) {
        moduleService.deleteModule(moduleId);
        return ResponseEntity.noContent().build();
    }
}
