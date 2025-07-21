package com.wuwa.helper.services;

import com.wuwa.helper.dto.UserDTO;
import com.wuwa.helper.dto.UserResonatorDTO;
import com.wuwa.helper.dto.UserResonatorResponseDTO;
import com.wuwa.helper.entity.User;
import com.wuwa.helper.entity.UserResonator;
import com.wuwa.helper.entity.UserResonatorId;
import com.wuwa.helper.repository.ResonatorRepository;
import com.wuwa.helper.repository.UserRepository;
import com.wuwa.helper.repository.UserResonatorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ResonatorRepository resonatorRepository;
    private final UserResonatorRepository userResonatorRepository;

    public UserService(UserRepository userRepository, ResonatorRepository resonatorRepository, UserResonatorRepository userResonatorRepository) {
        this.userRepository = userRepository;
        this.resonatorRepository = resonatorRepository;
        this.userResonatorRepository = userResonatorRepository;
    }

    public UUID createUser(UserDTO userDTO){
        var user = new User(
                null,
                userDTO.name(),
                userDTO.email(),
                userDTO.password(),
                Instant.now(),
                null,
                null);
        var savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public Optional<User> getUserByID(String userId){
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean userExists(String userId){
        return userRepository.existsById(UUID.fromString(userId));
    }

    public void deleteUserById(String userId){
        if(userExists(userId)){
            userRepository.deleteById(UUID.fromString(userId));
        }
    }

    public User updateUserById(String userId, UserDTO userDTO){
        var userUpdated = userRepository.findById(UUID.fromString(userId)).get();
        userUpdated.setName(userDTO.name());
        userUpdated.setEmail(userDTO.email());
        userUpdated.setPassword(userDTO.password());
        return userRepository.save(userUpdated);
    }

    public void associateResonator(String userId, UserResonatorDTO userResonatorDTO){
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var resonator = resonatorRepository.findById(userResonatorDTO.resonatorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new UserResonatorId(user.getId(), resonator.getResonatorId());
        var userResonator = new UserResonator(
                id,
                userResonatorDTO.acquisitionDate(),
                userResonatorDTO.currentLevel(),
                userResonatorDTO.rankAscension(),
                user,
                resonator
        );

        userResonatorRepository.save(userResonator);
    }

    public List<UserResonatorResponseDTO> getAllResonators(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getUserResonators()
                .stream()
                .map(userResonator ->
                        new UserResonatorResponseDTO(
                                userResonator.getResonator().getResonatorId(),
                                userResonator.getResonator().getName(),
                                userResonator.getAcquisitionDate(),
                                userResonator.getCurrentLevel(),
                                userResonator.getRankAscension()
                        )
                ).toList();
    }
}
