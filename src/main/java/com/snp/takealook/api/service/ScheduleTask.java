package com.snp.takealook.api.service;

import com.snp.takealook.api.repository.cat.CatCareRepository;
import com.snp.takealook.api.repository.cat.CatLocationRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.cat.CatStatusRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleTask {

    private final UserRepository userRepository;
    private final CatRepository catRepository;
    private final CatLocationRepository catLocationRepository;
    private final CatCareRepository catCareRepository;
    private final CatStatusRepository catStatusRepository;

    @Scheduled(fixedRate = 1000 * 60L * 60L * 24L)
    public void deleteUser() {
        System.out.println("삭제 처리 후, 3개월이 지난 회원 정보가 삭제됩니다.");
        LocalDateTime dateTime = LocalDateTime.now().minusMonths(3);
        System.out.println("*** 삭제될 데이터 수 >>" + userRepository.findUsersByModifiedAtBeforeAndDflagTrue(dateTime).size());
        userRepository.deleteAll(userRepository.findUsersByModifiedAtBeforeAndDflagTrue(dateTime));
    }

    @Scheduled(fixedRate = 1000 * 60L * 60L * 24L)
    public void deleteCat() {
        System.out.println("*** 삭제 처리 후, 3개월이 지난 고양이가 삭제됩니다.");
        LocalDateTime dateTime = LocalDateTime.now().minusMonths(3);
        System.out.println("*** 삭제될 데이터 수 >>" + catRepository.findCatsByModifiedAtBeforeAndDflagTrue(dateTime).size());
        catRepository.deleteAll(catRepository.findCatsByModifiedAtBeforeAndDflagTrue(dateTime));
    }

    @Scheduled(fixedRate = 1000 * 60L * 60L * 24L)
    public void deleteCatLocation() {
        System.out.println("*** 저장된 지 1년이 지난 고양이 위치 정보가 삭제됩니다.");
        LocalDateTime dateTime = LocalDateTime.now().minusYears(1);
        System.out.println("*** 삭제될 데이터 수 >>" + catLocationRepository.findCatLocationsByModifiedAtBefore(dateTime).size());
        catLocationRepository.deleteAll(catLocationRepository.findCatLocationsByModifiedAtBefore(dateTime));
    }

    @Scheduled(fixedRate = 1000 * 60L * 60L * 24L)
    public void deleteCatCare() {
        System.out.println("*** 저장된 지 1년이 지난 고양이 돌봄 정보가 삭제됩니다.");
        LocalDateTime dateTime = LocalDateTime.now().minusYears(1);
        System.out.println("*** 삭제될 데이터 수 >>" + catCareRepository.findCatCaresByModifiedAtBefore(dateTime).size());
        catCareRepository.deleteAll(catCareRepository.findCatCaresByModifiedAtBefore(dateTime));
    }

    @Scheduled(fixedRate = 1000 * 60L * 60L * 24L)
    public void deleteCatStatus() {
        System.out.println("*** 저장된 지 1년이 지난 고양이 상태 정보가 삭제됩니다.");
        LocalDateTime dateTime = LocalDateTime.now().minusYears(1);
        System.out.println("*** 삭제될 데이터 수 >>" + catStatusRepository.findCatStatusByModifiedAtBefore(dateTime).size());
        catStatusRepository.deleteAll(catStatusRepository.findCatStatusByModifiedAtBefore(dateTime));
    }
}
