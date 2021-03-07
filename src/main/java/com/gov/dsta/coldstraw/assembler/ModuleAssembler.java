package com.gov.dsta.coldstraw.assembler;

import com.gov.dsta.coldstraw.controller.ModuleController;
import com.gov.dsta.coldstraw.controller.UserController;
import com.gov.dsta.coldstraw.model.Module;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ModuleAssembler implements RepresentationModelAssembler<Module, EntityModel<Module>> {

    @Override
    public EntityModel<Module> toModel(Module module) {
        return EntityModel.of(module,
                linkTo(methodOn(ModuleController.class).getModule(module.getId())).withSelfRel(),
                linkTo(methodOn(ModuleController.class).getModules()).withRel("modules")
        );
    }

    public CollectionModel<EntityModel<Module>> toCollectionModel(List<EntityModel<Module>> modules) {
        return CollectionModel.of(modules, linkTo(methodOn(ModuleController.class).getModules()).withSelfRel());
    }
}