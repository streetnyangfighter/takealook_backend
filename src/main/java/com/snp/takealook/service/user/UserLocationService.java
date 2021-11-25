package com.snp.takealook.service.user;

import com.snp.takealook.domain.user.User;
import com.snp.takealook.domain.user.UserLocation;
import com.snp.takealook.dto.user.UserLocationDTO;
import com.snp.takealook.repository.user.UserLocationRepository;
import com.snp.takealook.repository.user.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLocationService {

    @Autowired
    private UserLocationRepository userLocationRepository;
    @Autowired
    private UserRepository userRepository;

    public long saveUserLocation(UserLocationDTO.Create dto) throws NotFoundException {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("User with id: " + dto.getUserId() + " is not valid"));
        UserLocation save = userLocationRepository.save(UserLocation.builder()
                .user(user)
                .sido(dto.getSido())
                .gugun(dto.getGugun())
                .dong(dto.getDong())
                .build()
        );

        return save.getId();
    }

    public void deleteUserLocation(UserLocationDTO.Delete dto) throws NotFoundException {
        UserLocation userLocation = userLocationRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("UserLocation with id: " + dto.getId() + " is not valid"));
        userLocationRepository.deleteById(userLocation.getId());
    }

    public List<UserLocation> getUserLocationListByUserId(UserLocationDTO.Get dto) throws NotFoundException {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("User with id: " + dto.getUserId() + " is not valid"));

        return userLocationRepository.findUserLocationsByUser(user);
    }
}
