package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.exception.Group.GroupNotFoundException;
import com.gov.dsta.coldstraw.exception.user.DuplicateUserException;
import com.gov.dsta.coldstraw.exception.user.UserNotFoundException;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public Set<Group> getUserGroups(Long userId) {
        User user = getUser(userId);
        return user.getGroups();
    }

    public Group getUserGroup(Long userId, Long groupId) {
        Set<Group> groups = getUserGroups(userId);
        return groups.stream()
                .filter(group -> group.getId().equals(groupId))
                .findFirst()
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    // POST
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // PUT
    public User updateUser(Long userId, User newUser) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setName(newUser.getName());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    checkForDuplicate(newUser);
                    return userRepository.save(newUser);
                });
    }

    // DELETE
    public void deleteUser(Long userId) {
        deleteUserGroups(userId);
        userRepository.deleteById(userId);
    }

    public void deleteUserGroups(Long userId) {
        User user = getUser(userId);
        Set<Group> groups = user.getGroups();
        groups.forEach(group -> deleteUserGroup(userId, group.getId()));
        userRepository.save(user);
    }

    public void deleteUserGroup(Long userId, Long groupId) {
        User user = getUser(userId);
        Set<Group> groups = user
                .getGroups()
                .stream()
                .filter(group -> !group.getId().equals(groupId))
                .collect(Collectors.toSet());
        user.setGroups(groups);
        userRepository.save(user);
    }

    // UTILITY
    private void checkForDuplicate(User user) {
        Optional<User> roleFromDB = userRepository.findByName(user.getName());
        roleFromDB.ifPresent(duplicateRole -> {
            throw new DuplicateUserException(user);
        });
    }
}
