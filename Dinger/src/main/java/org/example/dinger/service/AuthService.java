package org.example.dinger.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.dinger.dao.RoleDao;
import org.example.dinger.dao.UserDao;
import org.example.dinger.dto.LoginDto;
import org.example.dinger.dto.RegisterDto;
import org.example.dinger.entity.Role;
import org.example.dinger.entity.User;
import org.example.dinger.exception.RoleNotFoundException;
import org.example.dinger.exception.UserNameAlreadyExistedException;
import org.example.dinger.exception.UserNotFoundException;
import org.example.dinger.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;
    private final AuthenticationManager authenticationManager;
    private final EntityManager entityManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao, AuthenticationManager authenticationManager, EntityManager entityManager, JwtTokenProvider jwtTokenProvider) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
        this.authenticationManager = authenticationManager;
        this.entityManager = entityManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginDto loginDto) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                        loginDto.getUserNameOrEmail(),
                        loginDto.getPassword()
        );
        Authentication authenticated = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    public String register(RegisterDto registerDto) {
        if (userDao.existsByUserName(registerDto.getUserName())) {
            throw new UserNameAlreadyExistedException();
        }

        Role roleUser = roleDao.findByRoleName("ROLE_REGISTERED_USER")
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        User user = new User(
                registerDto.getUserName(),
                registerDto.getPhonenumber(),
                registerDto.getNrc(),
                passwordEncoder.encode(registerDto.getPassword()),
                registerDto.isApproved(),
                registerDto.isBlocked()
        );

        user.addRole(roleUser);
        User savedUser = userDao.save(user);
        return savedUser.getId() + " registered successfully!";
    }

    public String approveUser(Integer userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        user.setApproved(true);
        userDao.save(user);
        return "User with ID " + userId + " has been approved successfully!";
    }

    public String blockUser(Integer userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        user.setBlocked(true);
        userDao.save(user);
        return "User with ID " + userId + " has been blocked successfully!";
    }

    public  List<User> filterUsers(String userName, String phonenumber, String nrc, String password ,Boolean approved,Boolean blocked) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);

        Predicate predicate = cb.conjunction();
        if (userName != null && !userName.isEmpty()) {
            predicate = cb.and(predicate, cb.equal(user.get("userName"), userName));
        }

        if (phonenumber != null && !phonenumber.isEmpty()) {
            predicate = cb.and(predicate, cb.equal(user.get("phonenumber"), phonenumber));
        }

        if (nrc != null && !nrc.isEmpty()) {
            predicate = cb.and(predicate, cb.equal(user.get("nrc"), nrc));
        }

        if (password != null && !password.isEmpty()) {
            predicate = cb.and(predicate, cb.equal(user.get("password"),password));
        }

        if (approved != null) {
            predicate = cb.and(predicate, cb.equal(user.get("approved"), approved));
        }
        if (blocked != null) {
            predicate = cb.and(predicate, cb.equal(user.get("blocked"), blocked));
        }

        cq.where(predicate);
        return entityManager.createQuery(cq).getResultList();
    }

}
