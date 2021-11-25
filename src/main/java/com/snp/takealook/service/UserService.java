package com.snp.takealook.service;

import com.snp.takealook.domain.user.User;
import com.snp.takealook.dto.RequestDTO;
import com.snp.takealook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long updateLoginDetail(Long id, RequestDTO.UserUpdate userUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        user.updateDetail(userUpdate.getNickname(), userUpdate.getPhone(), userUpdate.getImage());

        return id;
    }
}
