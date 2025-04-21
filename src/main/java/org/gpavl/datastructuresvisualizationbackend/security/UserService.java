package org.gpavl.datastructuresvisualizationbackend.security;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.entity.User;
import org.gpavl.datastructuresvisualizationbackend.model.DataStructureInfo;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.UserInfo;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataStructureRepository dataStructureRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User registerUser(String username, String password, Set<String> roles) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        if (roles == null || roles.isEmpty()) {
            Set<String> defaultRoles = new HashSet<>();
            defaultRoles.add("USER");
            user.setRoles(defaultRoles);
        } else {
            user.setRoles(roles);
        }

        return userRepository.save(user);
    }

    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public List<DataStructureInfo> getAllUserDataStructures() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = getCurrentUser(username);

        List<DataStructureState> dataStructureStates = dataStructureRepository.findAllByUser(currentUser);
        return dataStructureStates.stream().map(state -> {
            DataStructureInfo dataStructureInfo = new DataStructureInfo();
            dataStructureInfo.setName(state.getName());
            dataStructureInfo.setType(state.getType().getDataStructure());
            dataStructureInfo.setImplementation(state.getType().getImplementation());
            return dataStructureInfo;
        }).toList();
    }

    public UserInfo me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        return userInfo;
    }
}