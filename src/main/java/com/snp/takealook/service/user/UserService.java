package com.snp.takealook.service.user;

import com.snp.takealook.domain.user.User;
import com.snp.takealook.dto.RequestDTO;
import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.user.UserDTO;
import com.snp.takealook.repository.user.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseDTO.UserResponse findById(UserDTO.Get dto) {
        User entity = userRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("User with id: " + dto.getId() + " is not valid"));

        return new ResponseDTO.UserResponse(entity);
    }

    @Transactional
    public Long updateLoginDetail(Long id, RequestDTO.UserUpdate userUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        user.updateDetail(userUpdate.getNickname(), userUpdate.getPhone(), userUpdate.getImage());

        return id;
    }
}
