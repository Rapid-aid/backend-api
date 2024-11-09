//package rapidaid.backend_api.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import rapidaid.backend_api.models.DTOs.ChangePasswordDTO;
//import rapidaid.backend_api.models.DTOs.CreateUserDTO;
//import rapidaid.backend_api.models.DTOs.UpdateUserDTO;
//import rapidaid.backend_api.models.DTOs.UserDTO;
//import rapidaid.backend_api.models.User;
//import rapidaid.backend_api.models.enums.Role;
//import rapidaid.backend_api.repositories.UserRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class UserServiceTest {
//    @Mock
//    private UserRepository userRepository;
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void getAllUsers_whenUsersExist_thenShouldReturnUserList() {
//        User user1 = User.builder().id("1").username("testUser1").password("password").email("test1@exapmle.com").role(Role.RESPONDER).build();
//        User user2 = User.builder().id("2").username("testUser2").password("password").email("test2@exapmle.com").role(Role.RESPONDER).build();
//        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
//
//        List<User> allUsers = userService.getAllUsers();
//
//        assertEquals(2, allUsers.size());
//        assertEquals(user1, allUsers.get(0));
//        assertEquals(user2, allUsers.get(1));
//        verify(userRepository).findAll();
//    }
//    @Test
//    public void getAllUsers_whenUsersNotExist_thenShouldReturnEmptyList() {
//        when(userRepository.findAll()).thenReturn(List.of());
//
//        List<User> allUsers = userService.getAllUsers();
//
//        assertNotNull(allUsers);
//        assertEquals(0, allUsers.size());
//        verify(userRepository).findAll();
//    }
//
//    @Test
//    public void getUserById_whenUserExists_thenShouldReturnUser() {
//        User user = User.builder().id("1").build();
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//
//        User foundUser = userService.getUserById(user.getId());
//
//        assertEquals(user, foundUser);
//        verify(userRepository).findById(user.getId());
//    }
//
//    @Test
//    public void getUserById_whenUserDoesNotExist_thenShouldThrowIllegalArgumentException() {
//        String userId = "1";
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(userId));
//    }
//
//    @Test
//    public void registerUser_whenEmailOrUsernameExist_thenShouldThrowException(){
//        CreateUserDTO createUserDTO = CreateUserDTO.builder().username("testUser").email("test@example.com").password("password").build();
//        when(userRepository.existsByEmail(createUserDTO.getEmail())).thenReturn(true);
//
//        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(createUserDTO));
//    }
//
//    @Test
//    public void registerUser_whenEmailDoesNotExist_thenShouldReturnUser() {
//        CreateUserDTO createUserDTO = CreateUserDTO.builder().username("testUser").email("test@example.com").password("password").build();
//        when(userRepository.existsByEmail(createUserDTO.getEmail())).thenReturn(false);
//        when(userRepository.existsByUsername(createUserDTO.getUsername())).thenReturn(false);
//
//        UserDTO userCreated = userService.registerUser(createUserDTO);
//
//        assertNotNull(userCreated);
//        assertEquals(createUserDTO.getUsername(), userCreated.getUsername());
//        assertEquals(createUserDTO.getEmail(), userCreated.getEmail());
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    public void updateUser_whenUserExists_thenShouldReturnUser() {
//        String userId = "1";
//        UpdateUserDTO updateUserDTO = UpdateUserDTO.builder().username("testUpdateUserDTO").email("test@example.com").build();
//        UserDTO userDTO = UserDTO.builder().username("testUpdateUserDTO").email("test@example.com").build();
//        when(userRepository.findById(userId)).thenReturn(Optional.of(User.builder().id(userId).username("testUser").build()));
//        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        UserDTO actualUserDTO = userService.updateUser(userId, updateUserDTO);
//
//        assertEquals(userDTO, actualUserDTO);
//        verify(userRepository).findById(userId);
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    public void updateUser_whenUserDoesNotExist_thenShouldThrowIllegalArgumentException() {
//        String userId = "1";
//        UpdateUserDTO updateUserDTO = UpdateUserDTO.builder().username("testUpdateUserDTO").email("test@example.com").build();
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, updateUserDTO));
//        verify(userRepository, times(1)).findById(userId);
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    public void changePassword_whenUserExists_thenShouldChangePassword() {
//        String email = "test@example.com";
//        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(email, "newPassword");
//        User user = User.builder().email(email).password("oldPassword").build();
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//
//        userService.changePassword(changePasswordDTO);
//
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    public void changePassword_whenUserDoesNotExist_thenShouldThrowIllegalArgumentException() {
//        String email = "test@example.com";
//        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(email, "newPassword");
//        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
//
//        assertThrows(IllegalArgumentException.class, () -> userService.changePassword(changePasswordDTO));
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    public void deleteUser_whenUserExists_thenShouldReturnTrue() {
//        String userId = "1";
//        when(userRepository.existsById(userId)).thenReturn(true);
//
//        Boolean result = userService.deleteUser(userId);
//
//        assertTrue(result);
//        verify(userRepository).deleteById(userId);
//    }
//
//    @Test
//    public void deleteUser_whenUserDoesNotExist_thenShouldReturnFalse() {
//        String userId = "1";
//        when(userRepository.existsById(userId)).thenReturn(false);
//
//        Boolean result = userService.deleteUser(userId);
//
//        assertFalse(result);
//        verify(userRepository, never()).deleteById(userId);
//    }
//}
