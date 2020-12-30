package com.example.user_crud_spring_boot.services;


import com.example.user_crud_spring_boot.dao.RoleRepository;
import com.example.user_crud_spring_boot.dao.UserRepository;
import com.example.user_crud_spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userServiceDetailsImpl")
public class UserServiceDetailsImpl implements UserService {


    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceDetailsImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }



    @Transactional
    @Override
    public void saveUser(User user) {
        //userDao.add(user);
        userRepository.save(user);
    }

    
    @Transactional(readOnly = true)
    @Override
    public List<User> listUser() {
        //return userDao.listUsers();
        return userRepository.findAll();
    }


    @Transactional
    @Override
    public void updateUser(User user) {
        User user1 = userRepository.findById(user.getId()).get();
        roleRepository.deleteAll(user1.getRoles());
        userRepository.save(user);

    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(new Long(id));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(s);
        return user;
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findUserByEmail(name);
    }
}
