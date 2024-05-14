package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.User;
import ru.nsjbag.diary.repositories.UserRepository;
import ru.nsjbag.diary.specifications.UserSpecification;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUserName(String username) {
        User user = userRepository.findByusername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return user;
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

    public List<User> getAllUsersWithUserRole() {
        return userRepository.getUsersByAuthorityAuthority("ROLE_USER");
    }
    public List<User> getAllUsersWithUserRole(String lastName, String firstName, String date, String insurance) {

        Specification<User> specification = Specification
                .where(UserSpecification.hasLastName(lastName))
                .and(UserSpecification.hasFirstName(firstName))
                .and(UserSpecification.hasDateOfBirth(date))
                .and(UserSpecification.hasInsuranceNumber(insurance));
        return userRepository.findAll(specification);
    }


}
