package com.alperkyoruk.invitation.business.abstracts;

import com.alperkyoruk.invitation.core.result.DataResult;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    Result addAdmin(User user);

    Result addModerator(User user);

    DataResult<List<User>> getUsers();

    DataResult<User> getUserById(int id);

    DataResult<User> getByUsername(String username);


}
