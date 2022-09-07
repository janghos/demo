package com.example.demo.Service;

import com.example.demo.VO.UserDto;
import com.example.demo.Mapper.AuthprotyMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.entity.Authority;
import com.example.demo.entity.Authority2;
import com.example.demo.entity.User;
import com.example.demo.entity.User2;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private AuthprotyMapper authprotyMapper;
    @Autowired
    private UserMapper userMapper;

    public List<Authority2> getAll(){
        return authprotyMapper.getAll();
    }
    public List<User2> getAllUser() {
        return userMapper.getAllUser();
    }

    public User2 getById(String id) {
        return userMapper.getById(id);
    }
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // User ->  ROLE_USER 권한을 가지고 있음
    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
//            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }
    
    // 유저, 권한정보를 가져오는 메소드가 2개 있음
    // 1번 - 파라미터에 담아있는 username을 기준으로 정보를 가져옴
    //
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username){
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    //2번 -- SecurityContext에 저장된 username의 정보와 권한정보 가져옴
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(){
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
//    @Transactional(readOnly = true)
//    public UserDto getUserWithAuthorities(String username) {
//        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
//    }
//
//    @Transactional(readOnly = true)
//    public UserDto getMyUserWithAuthorities() {
//        return UserDto.from(
//                SecurityUtil.getCurrentUsername()
//                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
//                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
//        );
//    }


}
