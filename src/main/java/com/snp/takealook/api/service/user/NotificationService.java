package com.snp.takealook.api.service.user;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.Selection;
import com.snp.takealook.api.domain.user.Notification;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.repository.community.CommentRepository;
import com.snp.takealook.api.repository.community.PostRepository;
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
    private final CatRepository catRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(rollbackFor = Exception.class)
    public void catSave(Long userId, Long catId, Byte type) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        String message = null;

        if (type == 0) {
            message = user.getNickname() + " 님이 " + cat.getName() + "(이)의 돌봄 내역을 추가했습니다.";
        } else if (type == 1) {
            message = user.getNickname() + " 님이 " + cat.getName() + "(이)의 정보를 수정했습니다.";
        } else if (type == 2) {
            message = user.getNickname() + " 님이 " + cat.getName() + "(이)를 함께 돌보게 되었습니다.";
        } else if (type == 3) {
            message = user.getNickname() + " 님이 " + cat.getName() + "(이)와 같은 고양이를 추천받아 그룹을 이동했습니다.";
        } else if (type == 4) {
            message = user.getNickname() + " 님이 다른 고양이를 찾아 떠났습니다.";
        } else if (type == 5) {
            message = cat.getName() + "(이)를 돌보는 집사가 줄었습니다.";
        } else if (type == 6) {
            message = user.getNickname() + " 님이 " + cat.getName() + "(이)의 고양이 별 여행 소식을 전했습니다. 작별 메세지를 확인하세요.";
        } else if (type == 7) {
            message = user.getNickname() + " 님이 " + cat.getName() + "(이)를 집으로 모셔갔습니다. 인사 메세지를 확인하세요.";
        }

        List<Selection> selectionList = cat.getSelectionList();
        for (Selection selection : selectionList) {
            if (!Objects.equals(selection.getUser(), user)) {
                notificationRepository.save(new Notification(selection.getUser(), message, type, catId));
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void postSave(Long id, Long userId, Byte type) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post with id: " + id + " is not valid"));
        String message = null;

        if (type == 8) {
            message = user.getNickname() + "님이 '" + post.getTitle()+ "'에 댓글을 남겼습니다.";
        } else if (type == 9) {
            message = user.getNickname() + "님이 '" + post.getTitle()+ "'을 추천했습니다.";
        }

        if(!Objects.equals(user.getNickname(), post.getWriter().getNickname())) {
            notificationRepository.save(new Notification(post.getWriter(), message, type, id));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void commentSave(Long id, Long userId, Byte type) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post with id: " + id + " is not valid"));
        String message = null;

        if (type == 10) {
            message = user.getNickname() + "님이 '" +  comment.getContent()+ "' 댓글을 추천했습니다.";
        }

        if(!Objects.equals(user.getNickname(), comment.getWriter().getNickname())) {
            notificationRepository.save(new Notification(comment.getWriter(), message, type, comment.getPost().getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ResponseDTO.NotificationListResponse> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        return user.getNotificationList().stream()
                .map(ResponseDTO.NotificationListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean checkNotification(Long notiId) {
        Notification notification = notificationRepository.findById(notiId)
                .orElseThrow(() -> new IllegalArgumentException("Notification with id: " + notiId + " is not valid"));

        notification.check();

        return notification.getChecked();
    }

    @Transactional
    public Long getUncheckedNotifications(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        return user.getNotificationList().stream().map(n -> n.getChecked()==true).count();
    }
}
