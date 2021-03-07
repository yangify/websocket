package com.gov.dsta.coldstraw.assembler;

import com.gov.dsta.coldstraw.controller.GroupController;
import com.gov.dsta.coldstraw.controller.UserController;
import com.gov.dsta.coldstraw.model.Group;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GroupAssembler implements RepresentationModelAssembler<Group, EntityModel<Group>> {

    @Override
    public EntityModel<Group> toModel(Group group) {
        return EntityModel.of(group,
                linkTo(methodOn(GroupController.class).getGroup(group.getId())).withSelfRel(),
                linkTo(methodOn(GroupController.class).getGroups()).withRel("groups")
        );
    }

    public CollectionModel<EntityModel<Group>> toCollectionModel(List<EntityModel<Group>> groups) {
        return CollectionModel.of(groups, linkTo(methodOn(UserController.class).getUsers()).withSelfRel());
    }
}
