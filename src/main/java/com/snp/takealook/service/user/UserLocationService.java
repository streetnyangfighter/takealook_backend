package com.snp.takealook.service.user;

import com.snp.takealook.domain.user.User;
import com.snp.takealook.domain.user.UserLocation;
import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.repository.user.UserLocationRepository;
import com.snp.takealook.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserLocationService {

    private final UserLocationRepository userLocationRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<ResponseDTO.UserLocationListResponse> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        return userLocationRepository.findUserLocationsByUser(user).stream()
                .map(ResponseDTO.UserLocationListResponse::new)
                .collect(Collectors.toList());
    }

}
