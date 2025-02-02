package me.jko.javatokotlin.service;

import me.jko.javatokotlin.entity.User;
import me.jko.javatokotlin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        // given
        String name = "jko";
        String email = "jjaayy.dev@gmail.com";
        User user = new User(1L, "jko", "jjaayy.dev@gmail.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        User createdUser = userService.createUser(name, email);

        // then
        assertNotNull(createdUser);
        assertEquals(name, createdUser.getName());
        assertEquals(email, createdUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById() {
        // given
        Long userId = 1L;
        User user = new User(1L, "jko", "jjaayy.dev@gmail.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        Optional<User> result = userService.getUserById(userId);

        // then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testUpdateUsername_UserExists() {
        // given
        Long userId = 1L;
        String newName = "jko2";
        User user = new User(1L, "jko", "jjaayy.dev@gmail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // when
        User updatedUser = userService.updateUsername(userId, newName);

        // then
        assertEquals(newName, updatedUser.getName());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUsername_UserNotFound() {
        // given
        Long userId = 1L;
        String newName = "jko2";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUsername(userId, newName));
        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testDeleteUser() {
        // given
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository, times(1)).deleteById(userId);
    }
}
