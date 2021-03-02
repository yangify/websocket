package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    public Group createGroup(Group group) {
        return null;
    }
}
