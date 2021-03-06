package com.gov.dsta.coldstraw.intializer;

import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.repository.GroupRepository;
import com.gov.dsta.coldstraw.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public Initializer(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        initializeUsers();
        initializeGroup();
    }

    public void initializeUsers() {
        Stream.of("Tom", "Jerry").forEach(name -> {
            User user = new User();
            user.setName(name);
            userRepository.save(user);
        });
    }

    public void initializeGroup() {
        Group group = new Group();
        group.setName("Cartoon");

        Collection<User> users = (Collection<User>) userRepository.findAll();
        Set<User> userSet = new HashSet<>(users);
        group.setUsers(userSet);

        groupRepository.save(group);
    }
}
