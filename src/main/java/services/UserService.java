package services;

import model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    //Looking for user with Id
    // id - ID user, return User object
    User getById(Long id);

    //Looking for user with specified login and pass
    // return User object
    User getByCredential(String login, String pass);

    //Create user
    // User for creation
    User create(User user);

    //Update user with Id
    // id - ID user for update, User - new object for update
    // return true if success, else false
    User update(User user);

    //Delete user with Id
    //return  true if deleted, false - otherwise
    void delete(Long user);

    List<User> getAll();
}
