package com.victoruk.StarHotel.service.interfac;

import com.victoruk.StarHotel.dto.LoginRequest;
import com.victoruk.StarHotel.dto.Response;
import com.victoruk.StarHotel.entity.User;


public interface IUserService {

    Response register(User loginRequest);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserBookingHistory(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String email);
    
}
