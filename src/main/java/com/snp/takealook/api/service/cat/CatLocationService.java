package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.cat.*;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.repository.cat.CatLocationRepository;
import com.snp.takealook.api.repository.cat.SelectionRepository;
import com.snp.takealook.api.service.GeometryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatLocationService {

    private final CatLocationRepository catLocationRepository;
    private final SelectionRepository selectionRepository;
    private final EntityManager em;

    @Transactional(rollbackFor = Exception.class)
    public Long saveAll(Long selectionId, CatDTO.LocationList[] dtoList) {
        Selection selection = selectionRepository.findById(selectionId).orElseThrow(() -> new IllegalArgumentException("Selection with id: " + selectionId + " is not valid"));

        List<CatLocation> list = Arrays.stream(dtoList)
                .map(v -> CatLocation.builder()
                        .selection(selection)
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .build())
                .collect(Collectors.toList());

        catLocationRepository.saveAll(list);

        return selectionId;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long update(Long userId, Long catId, CatDTO.LocationList[] dtoList) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        catLocationRepository.deleteAll(mySelection.getCatLocationList());

        List<CatLocation> list = Arrays.stream(dtoList)
                .map(v -> CatLocation.builder()
                        .selection(mySelection)
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .build())
                .collect(Collectors.toList());

        return mySelection.updateCatLocationList(list).getId();
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatLocationResponse> findLocationsByCatId(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<Selection> selectionList = mySelection.getCat().getSelectionList();
        List<CatLocation> catLocationList = new ArrayList<>();
        for (Selection selection : selectionList) {
            catLocationList.addAll(selection.getCatLocationList());
        }

        catLocationList.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt));

        return catLocationList.stream()
                .map(ResponseDTO.CatLocationResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatLocationResponse> getNearByCats(Double latitude, Double longitude, Double distance) {

        Location northEast = GeometryUtil.calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT c.id, c.created_at, c.modified_at, "
                        + "c.aflag, c.dflag, c.gender, c.image, c.name, c.neutered, c.pattern, c.status"
                        + "FROM cat AS c "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", c.point)", Cat.class)
                .setMaxResults(10);

        List<ResponseDTO.CatLocationResponse> cats = query.getResultList();
        return cats;
    }
}
