package com.snp.takealook.api.service.user;

import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.user.Notification;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.user.NotificationDTO;
import com.snp.takealook.api.repository.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.NotificationRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final SelectionRepository selectionRepository;

//    @Transactional
//    public Long save(Long userId, NotificationDTO.Create dto) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
//
//        return notificationRepository.save(dto.toEntity(user)).getId();
//    }

    @Transactional
    public void save(Long userId, Long catId, Byte type) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        String message = null;

        if (type == 0) {
            message = mySelection.getUser().getNickname() + " 님이 " + mySelection.getCat().getName() + "(이)의 돌봄 내역을 추가했습니다.";
        } else if (type == 1) {
            message = mySelection.getUser().getNickname() + " 님이 " + mySelection.getCat().getName() + "(이)의 상태를 변경했습니다.";
        } else if (type == 2) {
            message = mySelection.getUser().getNickname() + " 님이 " + mySelection.getCat().getName() + "(이)를 함께 돌보게 되었습니다.";
        } else if (type == 3) {
            message = mySelection.getCat().getName() + "(이)를 돌보는 집사가 줄었습니다.";
        }

        List<Selection> selectionList = mySelection.getCat().getSelectionList();
        for (Selection selection : selectionList) {
            // 자기 자신에게는 알림을 남길 필요가 없음
            if (!Objects.equals(selection.getUser(), mySelection.getUser())) {
                notificationRepository.save(new Notification(selection.getUser(), message, type));
            }
        }
    }

//    @Transactional
//    public void saveGroupNotification(Long userId, Long catId, Byte type) {
//        // 이벤트 생성 유저와 고양이
//        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
//        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
//
//        try {
//            List<Cat> sameGroupCatList = cat.getCatGroup().getCatList();
//            String message = null;
//            if (type == 1) {
//                message = user.getNickname() + " 님이 " + cat.getName() + "(이)의 돌봄이력을 추가했습니다.";
//            } else if (type == 2) {
//                message = user.getNickname() + " 님이 " + cat.getName() + "(이)의 상태를 변경했습니다.";
//            } else if (type == 6) {
//                message = user.getNickname() + " 님이 " + cat.getName() + "(이) 그룹에서 나갔습니다.";
//            }
//            for (Cat sameCat : sameGroupCatList) {
//                if (type == 5) {
//                    message = sameCat.getName() + "(이)와 같은 고양이가 발견되어 매칭되었습니다.";
//                }
//                save(sameCat.getUser().getId(), new NotificationDTO.Create(message, type));
//            }
//        } catch (NullPointerException e) {
//            // 혼자일 때는 알림이 필요하지 않음 !
//            System.out.println("*** 혼자인가??");
//        }
//    }
//
//    @Transactional
//    public void saveMatchNotification(Long catmatchId, Byte status, Byte type) {
//        CatMatch catMatch = catMatchRepository.findById(catmatchId).orElseThrow(() -> new IllegalArgumentException("CatMatch with id: " + catmatchId + " is not valid"));
//        Cat proposer = catMatch.getProposer();
//        Cat accepter = catMatch.getAccepter();
//
//        String message = null;
//        if (type == 3) {
//            message = accepter.getName() + "(이)에게 매칭 신청이 도착했습니다.";
//            save(accepter.getUser().getId(), new NotificationDTO.Create(message, type));
//        } else if (type == 4) {
//            String msg = status == 1 ? "수락" : "거절";
//            message = proposer.getName() + "(이)가 " + accepter.getName() + "(이)에게 보낸 매칭이 " + msg + " 되었습니다.";
//            save(proposer.getUser().getId(), new NotificationDTO.Create(message, type));
//        }
//    }

    @Transactional
    public Long check(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new IllegalArgumentException("Notification with id: " + notificationId + " is not valid"));

        notification.check();

        return notificationId;
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.NotificationListResponse> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        return user.getNotificationList().stream()
                .map(ResponseDTO.NotificationListResponse::new)
                .collect(Collectors.toList());
    }
}
