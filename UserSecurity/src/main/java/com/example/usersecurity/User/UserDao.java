package com.example.usersecurity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> selectUserByUsername(String username);

}
