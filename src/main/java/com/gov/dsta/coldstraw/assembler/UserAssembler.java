package com.gov.dsta.coldstraw.assembler;

import com.gov.dsta.coldstraw.controller.UserController;
import com.gov.dsta.coldstraw.model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getGroups(user.getId())).withRel("groups")
        );
    }

    public CollectionModel<EntityModel<User>> toCollectionModel(List<EntityModel<User>> roles) {
        return CollectionModel.of(roles, linkTo(methodOn(UserController.class).getUsers()).withSelfRel());
    }
}