package com.victoruk.StarHotel.service.imp;

import com.victoruk.StarHotel.dto.LoginRequest;
import com.victoruk.StarHotel.dto.Response;
import com.victoruk.StarHotel.dto.UserDTO;
import com.victoruk.StarHotel.entity.User;
import com.victoruk.StarHotel.exception.OurException;
import com.victoruk.StarHotel.repo.UserRepository;
import com.victoruk.StarHotel.service.interfac.IUserService;
import com.victoruk.StarHotel.util.JWTUtils;
import com.victoruk.StarHotel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public Response register(User user) {
        Response response = new Response();

        try {

            if (user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }

            if (userRepository.existsByEmail(user.getEmail())){
                throw new OurException(user.getEmail() + "Already Exists");
            }
            user.setPassWord(passwordEncoder.encode(user.getPassword()));
            User saveUser = userRepository.save(user);
            response.setStatusCode(200);
            response.setUser(new UserDTO());

        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occured During Registration" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {

        Response response = new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassWord()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new OurException("user not found"));
            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("successful");
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occured During Login" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllUsers() {

        Response response = new Response();

        try {
          List<User> userList = userRepository.findAll();
          List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
          response.setStatusCode(200);
          response.setMessage("Succes");

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occured During Login" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response = new Response();

        try {
           User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("User not found"));
           UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingAndRoom(user);
           response.setStatusCode(200);
            response.setMessage("successFul");
            response.setUser(userDTO);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting All users" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();

        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("User not found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("successFul");

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting All users" + e.getMessage());
        }

        return response;      }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("successFul");
            response.setUser(userDTO);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting All users" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getMyInfo(String email) {

        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(()-> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("successFul");
            response.setUser(userDTO);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting Info" + e.getMessage());
        }

        return response;
    }
}
