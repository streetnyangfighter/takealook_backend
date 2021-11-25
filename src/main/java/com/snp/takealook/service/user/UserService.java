package com.snp.takealook.service.user;

import com.snp.takealook.domain.user.User;
import com.snp.takealook.dto.user.UserDTO;
import com.snp.takealook.repository.user.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(UserDTO.Get dto) throws NotFoundException {
        return userRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("User with id: " + dto.getId() + " is not valid"));
    }
}
