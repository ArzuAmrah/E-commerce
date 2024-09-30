package com.example.e_commerce.service.user;

import com.example.e_commerce.dto.SignupDto;
import com.example.e_commerce.dto.UserDto;
import com.example.e_commerce.entities.OrderEntity;
import com.example.e_commerce.entities.UserEntity;
import com.example.e_commerce.enums.OrderStatus;
import com.example.e_commerce.enums.UserRole;
import com.example.e_commerce.repository.OrderRepository;
import com.example.e_commerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private OrderRepository orderRepository;


    @PostConstruct
    public void createAdminAccount() {

        // TODO
//        UserEntity adminAccount = userRepository.findByUserRole(UserRole.ADMIN).orElseThrow(
//                RuntimeException::new
//        );
        UserEntity user = new UserEntity();
        user.setUserRole(UserRole.ADMIN);
        user.setEmail("admin@test.com");
        user.setName("admin");
        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
        userRepository.save(user);
    }

    @Override
    public UserDto createUser(SignupDto signupDto) {
        UserEntity user = new UserEntity();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setUserRole(UserRole.USER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupDto.getPassword()));
        UserEntity createdUser = userRepository.save(user);
        OrderEntity order = new OrderEntity();
        order.setPrice(0L);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUser(createdUser);
        orderRepository.save(order);
        UserDto userDto = new UserDto();
        user.setId(createdUser.getId());
        user.setName(createdUser.getName());
        user.setEmail(createdUser.getEmail());
        user.setUserRole(createdUser.getUserRole());
        return userDto;
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
