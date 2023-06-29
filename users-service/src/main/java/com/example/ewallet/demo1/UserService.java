package com.example.ewallet.demo1;


import com.example.ewallet.demo.CommonConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate<String,String>KafkaTemplate;

    @Override
    public User loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public void create(UserCreateRequest userCreateRequest) throws JsonProcessingException {
        User user =userCreateRequest.to();
        user.setPassword(encryptPwd(user.getPassword()));
        user.setAuthorities(UserConstants.USER_AUTHORITY);
        userRepository.save(user);

        // TODO: Publish th event post user creation which can can be listened by customers

        JSONObject jsonObject =new JSONObject();
        jsonObject.put("userId",user.getId());
        jsonObject.put("phoneNumber",user.getPhoneNumber());
        jsonObject.put("identifierValue",user.getIdentifierValue());
        jsonObject.put("userIdentifier",user.getUserIdentifier());

        KafkaTemplate.send(CommonConstants.USER_CREATION_TOPIC,objectMapper.writeValueAsString(jsonObject));

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    private String encryptPwd(String rawPwd)
    {
         return passwordEncoder.encode(rawPwd);
    }
}
