package com.snp.takealook.service.user;

import com.snp.takealook.domain.user.User;
import com.snp.takealook.domain.user.UserLocation;
import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.user.UserLocationDTO;
import com.snp.takealook.repository.user.UserLocationRepository;
import com.snp.takealook.repository.user.UserRepository;
import javassist.NotFoundException;
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
    public Long save(UserLocationDTO.Create dto) {
        return userLocationRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public void delete(UserLocationDTO.Delete dto) {
        UserLocation userLocation = userLocationRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("UserLocation with id: " + dto.getId() + " is not valid"));
        userLocationRepository.delete(userLocation);
    }

    @Transactional
    public List<ResponseDTO.UserLocationListResponse> findAllByUserId(UserLocationDTO.Get dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User with id: " + dto.getUserId() + " is not valid"));

        return userLocationRepository.findUserLocationsByUser(user).stream()
                .map(ResponseDTO.UserLocationListResponse::new)
                .collect(Collectors.toList());
    }

}
