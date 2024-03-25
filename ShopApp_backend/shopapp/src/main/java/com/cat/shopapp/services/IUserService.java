package com.cat.shopapp.services;

import com.cat.shopapp.dtos.UserDTO;
import com.cat.shopapp.exceptions.DataNotFoundException;
import com.cat.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String phoneNumber, String password);
}
