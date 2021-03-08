package com.gov.dsta.coldstraw.intializer;

import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.Module;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.repository.GroupRepository;
import com.gov.dsta.coldstraw.repository.ModuleRepository;
import com.gov.dsta.coldstraw.repository.NotificationRepository;
import com.gov.dsta.coldstraw.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final GroupRepository groupRepository;
    private final ModuleRepository moduleRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public Initializer(GroupRepository groupRepository,
                       ModuleRepository moduleRepository,
                       NotificationRepository notificationRepository,
                       UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.moduleRepository = moduleRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        initializeUsers();
        initializeGroup();
        initializeModule();
        initializeNotification();
    }

    public void initializeUsers() {
        Stream.of("Tom", "Jerry").forEach(name -> {
            User user = new User();
            user.setName(name);
            userRepository.save(user);
        });
    }

    public void initializeGroup() {
        Collection<User> users = (Collection<User>) userRepository.findAll();
        Set<User> userSet = new HashSet<>(users);

        Group cartoonGroup = new Group();
        cartoonGroup.setName("Cartoon");
        cartoonGroup.setUsers(userSet);
        groupRepository.save(cartoonGroup);

        Group oldGroup = new Group();
        oldGroup.setName("Old");
        oldGroup.setUsers(userSet);
        groupRepository.save(oldGroup);
    }

    public void initializeModule() {
        Module module = new Module();
        module.setName("Facebook");
        moduleRepository.save(module);
    }

    public void initializeNotification() {
        Notification today = new Notification();
        today.setMessage("today");
        notificationRepository.save(today);

        Notification yesterday = new Notification();
        yesterday.setMessage("yesterday");
        yesterday.setDate(new Date(System.currentTimeMillis()-24*60*60*1000));
        notificationRepository.save(yesterday);

        Notification lastWeek = new Notification();
        lastWeek.setMessage("last week");
        lastWeek.setDate(new Date(System.currentTimeMillis()-5*24*60*60*1000));
        notificationRepository.save(lastWeek);

        Notification longLongTimeAgo = new Notification();
        longLongTimeAgo.setMessage("long long time ago");
        longLongTimeAgo.setDate(new Date(System.currentTimeMillis()-14*24*60*60*1000));
        notificationRepository.save(longLongTimeAgo);
    }
}
