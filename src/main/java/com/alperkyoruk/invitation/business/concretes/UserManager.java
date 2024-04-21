package com.alperkyoruk.invitation.business.concretes;

import com.alperkyoruk.invitation.business.abstracts.UserService;
import com.alperkyoruk.invitation.business.constants.Messages;
import com.alperkyoruk.invitation.core.result.DataResult;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.core.result.SuccessDataResult;
import com.alperkyoruk.invitation.core.result.SuccessResult;
import com.alperkyoruk.invitation.dataAccess.UserDao;
import com.alperkyoruk.invitation.entities.Role;
import com.alperkyoruk.invitation.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserManager implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Result addAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Set.of(Role.ROLE_ADMIN));
        userDao.save(user);

        return new SuccessResult(Messages.AdminAdded);
    }

    @Override
    public Result addModerator(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Set.of(Role.ROLE_MODERATOR));
        userDao.save(user);

        return new SuccessResult(Messages.ModeratorAdded);
    }

    @Override
    public DataResult<List<User>> getUsers() {
        var result = userDao.findAll();
        if(result.isEmpty()){
            return new SuccessDataResult<>(Messages.UsersNotFound);
        }

        return new SuccessDataResult<>(result, Messages.UsersListed);
    }

    @Override
    public DataResult<User> getUserById(int id) {
        var result = userDao.findById(id);
        if(result == null){
            return new SuccessDataResult<>(Messages.UserNotFound);
        }

        return new SuccessDataResult<>(result, Messages.UserListed);
    }

    @Override
    public DataResult<User> getByUsername(String username) {
        var result = userDao.findByUsername(username);
        if(result == null){
            return new SuccessDataResult<>(Messages.UserNotFound);
        }

        return new SuccessDataResult<>(result, Messages.UserListed);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = getByUsername(username).getData();
        return user;
    }
}
