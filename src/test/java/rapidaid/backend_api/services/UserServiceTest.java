package rapidaid.backend_api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.repositories.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
    public void shouldReturnAllUsers() {
        User user1 = new User("1", "user1", "password1", "", "");
        User user2 = new User("2", "user2", "password2", "", "");
        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        List<User> allUsers = userService.getAllUsers();

        assertEquals(2, allUsers.size());
        assertEquals(user1, allUsers.get(0));
        assertEquals(user2, allUsers.get(1));
    }
}
