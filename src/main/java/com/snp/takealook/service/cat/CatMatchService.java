package com.snp.takealook.service.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatGroup;
import com.snp.takealook.domain.cat.CatMatch;
import com.snp.takealook.dto.cat.CatMatchDTO;
import com.snp.takealook.repository.cat.CatGroupRepository;
import com.snp.takealook.repository.cat.CatMatchRepository;
import com.snp.takealook.repository.cat.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CatMatchService {

    private final CatMatchRepository catMatchRepository;
    private final CatRepository catRepository;
    private final CatGroupRepository catGroupRepository;

    @Transactional
    public Long match(CatMatchDTO.Match dto) {
        Cat proposer = catRepository.findById(dto.getProposerCatId()).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + dto.getProposerCatId() + " is not valid"));
        Cat accepter = catRepository.findById(dto.getAccepterCatId()).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + dto.getAccepterCatId() + " is not valid"));

        return catMatchRepository.save(dto.toEntity(proposer, accepter)).getId();
    }

    @Transactional
    public Long accept(Long id) {
        CatMatch catMatch = catMatchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("CatMatch with id: " + id + " is not valid"));
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
                    proposerCatList.stream().map(v -> v.updateCatGroup(changeCatGroup));
                } else {
                    CatGroup changeCatGroup = proposer.getCatGroup();
                    accepterCatList.stream().map(v -> v.updateCatGroup(changeCatGroup));
                }
            }
        }

        // 이전 그룹에 소속된 고양이가 없음에도 데이터가 남음 = 메모리 낭비
        // 배치 프로그램으로 List<Cat> catList 의 사이즈가 0이면 삭제되도록 처리?

        // 혹은, 초기 고양이 생성시 기본 그룹을 할당하지 말고, 매칭이 성사되면 그룹을 만들어서 두 고양이를 포함
        // 다대다 매칭시 위와 같은 문제가 동일하게 발생하지만, 일대일 매칭시 발생하는 메모리 낭비는 막을 수 있음
        // 고양이 "그룹"이기 때문에 매칭 후 만드는게 말이 되는 것 같기도 함

        return catMatch.accept().getId();
    }

    @Transactional
    public Long reject(Long id) {
        CatMatch catMatch = catMatchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("CatMatch with id: " + id + " is not valid"));

        return catMatch.reject().getId();
    }

    // 했던 매칭 취소는 보류 상태
    @Transactional
    public Long cancle(Long id) {
        // 다대다 매칭 후 캔슬을 하면, 처음 신청했던 고양이 하나만 빠져나가는 문제 발생
        // 매칭 후 캔슬은 안되고, 고양이를 빼고 싶으면 선택해서 뺄 수 있도록 하는 건 어떨까?
        CatMatch catMatch = catMatchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("CatMatch with id: " + id + " is not valid"));
        CatGroup catGroup = catGroupRepository.save(new CatGroup());

        catMatch.getProposer().updateCatGroup(catGroup);

        return catMatch.reject().getId();
    }
}
