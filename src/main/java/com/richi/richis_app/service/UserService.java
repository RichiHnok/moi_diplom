package com.richi.richis_app.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.richi.richis_app.entity.Role;
import com.richi.richis_app.entity.User;
import com.richi.richis_app.repository.RoleRepository;
import com.richi.richis_app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public User getUserByLogin(String login) throws NoSuchElementException{
        Optional<User> user = userRepository.findByLogin(login);

        return user.get();
        
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(int id) throws Exception{
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            return user.get();
        }else{
            throw new Exception("Can't get user");
        }
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(int userId){
        userRepository.deleteById(userId);
    }

    public User registerUser(User user, String roleName) throws Exception{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> optRole = roleRepository.findByRoleName(roleName);
        if(optRole.isPresent()){
            Role selectedRole = optRole.get();
            user.addRoleToUser(selectedRole);
            return userRepository.save(user);
        }else{
            throw new Exception("Cannot find role " + roleName);
        }
    }
}
