package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.exception.Group.GroupNotFoundException;
import com.gov.dsta.coldstraw.exception.user.UserNotFoundException;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    public Group getGroup(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    public Set<User> getUsers(Long groupId) {
        Group group = getGroup(groupId);
        return group.getUsers();
    }

    public User getUser(Long groupId, Long userId) {
        Set<User> users = getUsers(groupId);
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public Group createGroup(Group group) {
        return null;
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    public void deleteUser(Long groupId, Long userId) {
        Group group = getGroup(groupId);
        Set<User> users = group.getUsers()
                .stream()
                .filter(user -> user.getId().equals(userId))
                .collect(Collectors.toSet());
        group.setUsers(users);
        groupRepository.save(group);
    }
}
