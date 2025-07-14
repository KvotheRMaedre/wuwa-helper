package com.wuwa.helper.services;

import com.wuwa.helper.dto.UserDTO;
import com.wuwa.helper.entity.User;
import com.wuwa.helper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser{

        @Test
        @DisplayName("Should create user with success")
        void shouldCreateUserWithSuccess(){
            var userDTO = new UserDTO(
                    "Kvothe",
                    "kvothe@gmail.com",
                    "kvothe@123"
            );

            var user = new User(
                    UUID.randomUUID(),
                    userDTO.name(),
                    userDTO.email(),
                    userDTO.password(),
                    Instant.now(),
                    null
            );

            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            var savedUser = userService.createUser(userDTO);
            var userCaptured = userArgumentCaptor.getValue();

            assertNotNull(savedUser);
            assertInstanceOf(UUID.class, savedUser);
            assertEquals(userDTO.name(), userCaptured.getName());
            assertEquals(userDTO.email(), userCaptured.getEmail());
            assertEquals(userDTO.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs(){
            var userDTO = new UserDTO(
                    "user",
                    "email@email.com",
                    "password"
            );
            doThrow(new RuntimeException())
                    .when(userRepository)
                    .save(any());

            assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));
        }

    }

    @Nested
    class getUserById{

        @Test
        @DisplayName("Should return user if they exist")
        void ShouldReturnUserIfTheyExist(){
            var user = new User(
                    UUID.randomUUID(),
                    "Kvothe",
                    "kvothe@email.com",
                    "senha@123",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            var response = userService.getUserByID(user.getId().toString());
            var uuidCaptured = uuidArgumentCaptor.getValue();

            assertTrue(response.isPresent());
            assertEquals(user.getId(), uuidCaptured);
        }

        @Test
        @DisplayName("Should return user if optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty(){
            var userId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            var response = userService.getUserByID(userId.toString());
            var uuidCaptured = uuidArgumentCaptor.getValue();

            assertFalse(response.isPresent());
            assertEquals(userId, uuidCaptured);
        }

    }

    @Nested
    class getAllUsers{

        @Test
        @DisplayName("Should return one user if theres only one registered")
        void ShouldReturnOneUserIfTheresOnlyOneRegistered(){
            var user = new User(
                    UUID.randomUUID(),
                    "Kvothe",
                    "kvothe@email.com",
                    "senha@123",
                    Instant.now(),
                    null
            );
            doReturn(List.of(user))
                    .when(userRepository)
                    .findAll();

            var listUsers = userService.getAllUsers();

            assertNotNull(listUsers);
            assertEquals(1, listUsers.size());
        }
        @Test
        @DisplayName("Should return all users with success when theres more than 1 user")
        void shouldReturnAllUsersWithSuccessWhenTheresOnlyMoreThanOneUser(){
            var user = new User(
                    UUID.randomUUID(),
                    "user",
                    "email@email.com",
                    "password", Instant.now(),
                    null
            );
            var user2 = new User(
                    UUID.randomUUID(),
                    "user",
                    "email@email.com",
                    "password", Instant.now(),
                    null
            );
            var userList = List.of(user, user2);
            doReturn(userList)
                    .when(userRepository)
                    .findAll();

            var response = userService.getAllUsers();

            assertNotNull(response);
            assertEquals(userList.size(), response.size());
        }

        @Test
        @DisplayName("Should return all users with success when theres none user")
        void shouldReturnAllUsersWithSuccessWhenTheresNoneUser(){
            doReturn(List.of())
                    .when(userRepository)
                    .findAll();

            var response = userService.getAllUsers();

            assertNotNull(response);
            assertEquals(0, response.size());
        }
    }

    @Nested
    class userExists{

        @Test
        @DisplayName("Should return true if the user exist")
        void shouldReturnTrueIfTheUserExist(){
            var userId = UUID.randomUUID();
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var response = userService.userExists(userId.toString());

            assertTrue(response);
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should return false if the user don't exist")
        void shouldReturnTrueIfTheUserDontExist(){
            var userId = UUID.randomUUID();
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var response = userService.userExists(userId.toString());

            assertFalse(response);
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class deleteUserById{

        @Test
        @DisplayName("Should delete user with success")
        void shouldDeleteUserWithSuccess(){
            var userId = UUID.randomUUID();
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            userService.deleteUserById(userId.toString());

            var listId = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, listId.getFirst());
            assertEquals(userId, listId.getLast());
            verify(userRepository, times(1)).existsById(listId.getFirst());
            verify(userRepository, times(1)).deleteById(listId.getLast());
        }

        @Test
        @DisplayName("Should do nothing if the user doesn't exists")
        void shouldNotDeleteUserIfDoesntExist() {
            var user = new User(
                    UUID.randomUUID(),
                    "user",
                    "email@email.com",
                    "password", Instant.now(),
                    null
            );

            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            userService.deleteUserById(user.getId().toString());

            assertEquals(user.getId(), uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());
        }
    }

    @Nested
    class updateUserById{

        @Test
        @DisplayName("Should update user by id when user exists")
        void shouldUpdateUserByIdWhenUserExists(){
            var user = new User(
                    UUID.randomUUID(),
                    "user",
                    "email@email.com",
                    "password", Instant.now(),
                    null
            );
            var userDTO = new UserDTO(
                    "updated",
                    "updated@email.com",
                    "updated"
            );
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());
            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            var response = userService.updateUserById(user.getId().toString(), userDTO);
            var userCaptured = userArgumentCaptor.getValue();

            assertNotNull(response);
            assertEquals(userDTO.name(), userCaptured.getName());
            assertEquals(userDTO.email(), userCaptured.getEmail());
            assertEquals(userDTO.password(), userCaptured.getPassword());
            assertEquals(userDTO.name(), response.getName());
            assertEquals(userDTO.email(), response.getEmail());
            assertEquals(userDTO.password(), response.getPassword());
            verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        }
    }
}