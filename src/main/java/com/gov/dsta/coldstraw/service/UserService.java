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

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public List<Group> getGroups(Long userId) {
        User user = getUser(userId);
        return user.getGroups();
    }

    public Group getGroup(Long userId, Long groupId) {
        List<Group> groups = getGroups(userId);
        return groups.stream()
                .filter(group -> group.getId().equals(groupId))
                .findFirst()
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

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

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private void checkForDuplicate(User user) {
        Optional<User> roleFromDB = userRepository.findByName(user.getName());
        roleFromDB.ifPresent(duplicateRole -> {
            throw new DuplicateUserException(user);
        });
    }
}
