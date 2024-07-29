package com.todo.services;
import com.todo.config.AuthenticationRequest;
import com.todo.model.RegistrationResponse;
import com.todo.model.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    public RegistrationResponse createUser(Users user) ;
    UserDetailsService userDetailsService();
    public String login(AuthenticationRequest request);
    public boolean isUsernameAvailable(String username);

}
