package com.snp.takealook.api.service;

import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleTask {

    private final UserRepository userRepository;
    private final CatRepository catRepository;

    @Scheduled(fixedRate = 1000 * 60L * 60L * 24L)
    public void deleteUser() {
        System.out.println("탈퇴 처리 후, 3개월이 지난 회원 정보가 삭제됩니다.");
        LocalDateTime dateTime = LocalDateTime.now().minusMonths(3);
        List<User> userList = userRepository.findUsersByModifiedAtBeforeAndDflagTrue(dateTime);

        for (User user : userList) {
            List<Selection> selectionList = user.getSelectionList();
            for (Selection selection : selectionList) {
                // 돌보는 사람이 1명 밖에 없었을 경우 고양이도 삭제해야함
                // 회원이 탈퇴해서 고양이 돌봄하는 사람이 남지 않는 경우는 삭제하지 말고
                // 유일한 돌봄 회원이 명시적으로 '도감에서 고양이 삭제'를 할 때만 삭제되도록 할까?
                if (selection.getCat().getSelectionList().size() == 1) {
                    catRepository.delete(selection.getCat());
                }
                selection.setUserNull();
            }
        }

        System.out.println("*** 삭제될 데이터 수 >>" + userList.size());
        userRepository.deleteAll(userList);
    }

}
