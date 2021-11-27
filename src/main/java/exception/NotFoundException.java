package exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super("User with id=" + id + " not found");
    }

    public NotFoundException(String login, String password) {
        super("User with " + login + "/" + password + " not found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
