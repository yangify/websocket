package com.gov.dsta.coldstraw.intializer;

import com.github.javafaker.Faker;
import com.gov.dsta.coldstraw.model.*;
import com.gov.dsta.coldstraw.model.Module;
import com.gov.dsta.coldstraw.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final Faker faker;
    private final GroupRepository groupRepository;
    private final ModuleRepository moduleRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationReceiverRepository notificationReceiverRepository;
    private final UserRepository userRepository;

    public Initializer(GroupRepository groupRepository,
                       ModuleRepository moduleRepository,
                       NotificationRepository notificationRepository,
                       NotificationReceiverRepository notificationReceiverRepository, UserRepository userRepository) {
        this.notificationReceiverRepository = notificationReceiverRepository;

        this.faker = new Faker();
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
        for (int i = 0; i< 30; i++) {
            Date date = new Date();

            Module savedModule = moduleRepository.findByName("Facebook").get();

            String senderName = faker.backToTheFuture().character();
            Optional<User> optionalUser = userRepository.findByName(senderName);
            User savedSender = optionalUser.orElseGet(() -> userRepository.save(new User(senderName)));

            User receiver = userRepository.findByName("Tom").get();
            String message = faker.backToTheFuture().quote();

            Notification notification = new Notification();
            notification.setModule(savedModule);
            notification.setSender(savedSender);
            notification.setMessage(message);
            notification.setDate(date);

            Notification savedNotification = notificationRepository.save(notification);
            NotificationReceiver nr = new NotificationReceiver();
            nr.setReceiver(receiver);
            nr.setNotification(savedNotification);
            NotificationReceiver savedNr = notificationReceiverRepository.save(nr);

            Set<NotificationReceiver> nrs = new HashSet<>();
            nrs.add(savedNr);

            savedNotification.setReceivers(nrs);
            notificationRepository.save(notification);
        }
    }
}
