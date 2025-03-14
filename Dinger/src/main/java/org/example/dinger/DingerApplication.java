package org.example.dinger;

import jakarta.transaction.Transactional;
import org.example.dinger.dao.ProjectDao;
import org.example.dinger.dao.RoleDao;
import org.example.dinger.dao.UserDao;
import org.example.dinger.entity.Project;
import org.example.dinger.entity.Role;
import org.example.dinger.entity.User;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class DingerApplication {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final ProjectDao projectDao;

    public DingerApplication(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder, ProjectDao projectDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.projectDao = projectDao;
    }

    @Bean
    @Transactional @Profile("dev")
    public ApplicationRunner init() {
        return args -> {
            User user1=new User("John","09987654321", "NRC-001244", passwordEncoder.encode("12345"), false, false);
            User user2=new User("Mary", "09987754321", "NRC-001234", passwordEncoder.encode("12346"), false, false);
            Role userRole=new Role();
            userRole.setRoleName("ROLE_REGISTERED_USER");
            Role adminRole=new Role();
            adminRole.setRoleName("ROLE_SUPER_ADMIN");
            user1.addRole(adminRole);
            user2.addRole(userRole);
            userDao.save(user1);
            userDao.save(user2);
            roleDao.save(userRole);
            roleDao.save(adminRole);

            Project project1=new Project("Web Design", "Design company site", LocalDateTime.now(), LocalDateTime.now());
            Project project2=new Project("App Development", "Develop a mobile app", LocalDateTime.now(), LocalDateTime.now());

            project1.setUser(user2);
            project2.setUser(user2);
            projectDao.save(project1);
            projectDao.save(project2);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DingerApplication.class, args);
    }
}
