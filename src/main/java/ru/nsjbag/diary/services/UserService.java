package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.User;
import ru.nsjbag.diary.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUserName(String username) {
        User user = userRepository.findByusername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return user;
    }
    public String getRealNameByUsername(String username) {
        User user = userRepository.findByusername(username);
        return (user != null) ? user.getFirstname() : "";
    }
    public void encodePassword(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public String getAuthorityByusername(String username) {
        User user = userRepository.findByusername(username);
        return (user != null && user.getAuthority() != null) ? user.getAuthority().getAuthority() : null;
    }
}
