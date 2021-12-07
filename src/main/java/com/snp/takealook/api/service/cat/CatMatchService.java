package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatGroup;
import com.snp.takealook.api.domain.cat.CatMatch;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatMatchDTO;
import com.snp.takealook.api.repository.cat.CatGroupRepository;
import com.snp.takealook.api.repository.cat.CatMatchRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatMatchService {

    private final CatMatchRepository catMatchRepository;
    private final CatRepository catRepository;
    private final CatGroupRepository catGroupRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long match(CatMatchDTO.Match dto) {
        Cat proposer = catRepository.findById(dto.getProposerCatId()).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + dto.getProposerCatId() + " is not valid"));
        Cat accepter = catRepository.findById(dto.getAccepterCatId()).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + dto.getAccepterCatId() + " is not valid"));

        return catMatchRepository.save(dto.toEntity(proposer, accepter)).getId();
    }

    @Transactional
    public Long accept(Long catmatchId) {
        CatMatch catMatch = catMatchRepository.findById(catmatchId).orElseThrow(() -> new IllegalArgumentException("CatMatch with id: " + catmatchId + " is not valid"));
        Cat proposer = catMatch.getProposer();
        Cat accepter = catMatch.getAccepter();

        if(proposer.getCatGroup() == null) { // 일대일 혹은 일대다 매칭
            if(accepter.getCatGroup() == null) { // 일대일 매칭 -> CatGroup 생성 필요
                CatGroup newCatGroup = catGroupRepository.save(new CatGroup());
                proposer.updateCatGroup(newCatGroup);
                accepter.updateCatGroup(newCatGroup);
            } else { // 일대다 매칭 -> Proposer 를 Accepter 의 그룹으로 updateCatGroup
                proposer.updateCatGroup(accepter.getCatGroup());
            }
        } else { // 다대일 혹은 다대다 매칭
            if (accepter.getCatGroup() == null) { // 다대일 매칭 -> Accepter 를 Proposer 의 그룹으로 updateGroup
                accepter.updateCatGroup(proposer.getCatGroup());
            } else { // 다대다 매칭 -> size 가 더 작은 그룹을 큰 그룹으로 updateCatGroup
                List<Cat> proposerCatList = proposer.getCatGroup().getCatList();
                List<Cat> accepterCatList = accepter.getCatGroup().getCatList();
                if(proposerCatList.size() < accepterCatList.size()) { // Proposer 를 Accepter 의 그룹으로 updateGroup
                    CatGroup changeCatGroup = accepter.getCatGroup();
                    CatGroup pastCatGroup = proposer.getCatGroup();
                    for (Cat cat : proposerCatList) {
                        cat.updateCatGroup(changeCatGroup);
                    }
                    catGroupRepository.delete(pastCatGroup);
                } else { // Accepter 를 Proposer 의 그룹으로 updateGroup
                    CatGroup changeCatGroup = proposer.getCatGroup();
                    CatGroup pastCatGroup = accepter.getCatGroup();
                    for (Cat cat : accepterCatList) {
                        cat.updateCatGroup(changeCatGroup);
                    }
                    catGroupRepository.delete(pastCatGroup);
                }
            }
        }

        return catMatch.accept().getId();
    }

    @Transactional
    public Long reject(Long catmatchId) {
        CatMatch catMatch = catMatchRepository.findById(catmatchId).orElseThrow(() -> new IllegalArgumentException("CatMatch with id: " + catmatchId + " is not valid"));

        return catMatch.reject().getId();
    }

    @Transactional
    public void delete(Long catmatchId) {
        CatMatch catMatch = catMatchRepository.findById(catmatchId).orElseThrow(() -> new IllegalArgumentException("CatMatch with id: " + catmatchId + " is not valid"));
        if (catMatch.getStatus() != 2) {
            throw new IllegalStateException("catMatch already accepted/rejected");
        }
        catMatchRepository.delete(catMatch);
    }

    @Transactional
    public List<ResponseDTO.CatMatchListResponse> findAllSendByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        List<CatMatch> sendList = new ArrayList<>();

        try {
            List<Cat> userCatList = user.getCatList();
            for (Cat cat : userCatList) {
                sendList.addAll(catMatchRepository.findCatMatchesByProposer_Id(cat.getId()));
            }
        } catch (NullPointerException e) {
            return null;
        }

        sendList.sort(Comparator.comparing(BaseTimeEntity::getModifiedAt));

        return sendList.stream()
                .map(ResponseDTO.CatMatchListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ResponseDTO.CatMatchListResponse> findAllReceiveByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        List<CatMatch> receiveList = new ArrayList<>();

        try {
            List<Cat> userCatList = user.getCatList();
            for (Cat cat : userCatList) {
                receiveList.addAll(catMatchRepository.findCatMatchesByAccepter_Id(cat.getId()));
            }
        } catch (NullPointerException e) {
            return null;
        }

        receiveList.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt));

        return receiveList.stream()
                .map(ResponseDTO.CatMatchListResponse::new)
                .collect(Collectors.toList());
    }

}
