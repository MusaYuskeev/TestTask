package services;

import exception.InvalidRequestException;
import exception.NotFoundException;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServiceDB implements UserService {
    private static final Logger log = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    public User getById(Long id) {
        log.log(Level.INFO, "Try get user by Id: " + id);
        Optional<User> isUser = userRepository.findById(id);
        if (isUser.isPresent()) {
            return isUser.get();
        } else {
            throw new NotFoundException("id");
        }
    }

    public User getByCredential(String login, String password) {
        log.log(Level.INFO, "Try find by credentials: " + login + "/" + password);
        checkCredentials(login, password);
        return userRepository.findByLoginAndPassword(login, password);
    }

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("No data found");
        }
        return users;
    }

    public User create(User newUser) {
        log.log(Level.INFO, "Try to create new user: " + newUser);
        checkCredentials(newUser.getLogin(), newUser.getPassword());
        if (getByCredential(newUser.getLogin(), newUser.getPassword()) != null) {
            throw new InvalidRequestException("User " + newUser.getLogin() + "/" + newUser.getPassword() + " already exist.");
        }
        return save(new User(newUser.getLogin(), newUser.getPassword()));
    }

    public User update(User newUser) {
        log.log(Level.INFO, "Try for update user to " + newUser);

        if (newUser == null || newUser.getId() == null) {
            throw new InvalidRequestException("No User data!");
        }
        User oldUser = getById(newUser.getId());
        if (oldUser == null) {
            throw new NotFoundException(newUser.getId());
        }
        oldUser.setLogin(newUser.getLogin());
        oldUser.setPassword(newUser.getPassword());
        return save(oldUser);
    }

    public User save(User user) {
        User savedUser = userRepository.save(user);
        if (savedUser == null) {
            throw new InvalidRequestException("Error save user with ID " + user.getId());
        }
        return savedUser;
    }

    public void delete(Long id) {
        log.log(Level.INFO, "Try to delete user with Id: " + id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(id);
        }
    }

    private void checkCredentials(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            throw new NotFoundException(login, password);
        }
    }
}
