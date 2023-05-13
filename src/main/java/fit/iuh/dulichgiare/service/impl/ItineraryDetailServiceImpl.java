package fit.iuh.dulichgiare.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.ItineraryDetailDTO;
import fit.iuh.dulichgiare.entity.Itinerary;
import fit.iuh.dulichgiare.entity.ItineraryDetail;
import fit.iuh.dulichgiare.repository.ItineraryDetailRepository;
import fit.iuh.dulichgiare.repository.ItineraryRepository;
import fit.iuh.dulichgiare.service.ItineraryDetailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItineraryDetailServiceImpl implements ItineraryDetailService {
    @Autowired
    private ItineraryDetailRepository itineraryDetailRepo;
    @Autowired
    private ItineraryRepository itineraryRepo;

    @Override
    public List<ItineraryDetailDTO> getAllItineraryDetails() throws InterruptedException, ExecutionException {

        return itineraryDetailRepo.findAll().stream().map(itineraryDetail -> {
            ItineraryDetailDTO dto = new ItineraryDetailDTO();
            dto.setId(itineraryDetail.getId());
            dto.setTitle(itineraryDetail.getTitile());
            dto.setDescription(itineraryDetail.getDescription());
            return dto;
        }).toList();
    }

    @Override
    public int saveItineraryDetail(ItineraryDetailDTO itineraryDetailDTO)
            throws InterruptedException, ExecutionException {
        if (itineraryDetailDTO != null) {
//            Itinerary itinerary = itineraryRepo.findItineraryByDescription(itineraryDetailDTO.getItineraryName());
//            log.info("Finished get object itinerary with itinerary name: " + itinerary.getDescription());
//            log.info("Start save intinerary Detail");
//            ItineraryDetail itineraryDetail = new ItineraryDetail(itinerary, itineraryDetailDTO.getTitle(),
//                    itineraryDetailDTO.getDescription());
//            itineraryDetailRepo.save(itineraryDetail);
//            log.info("Save Itinerary Success with Id: " + itineraryDetail.getId());
            return 0;
        }
        return 1;
    }

    @Override
    public int updateItineraryDetail(ItineraryDetailDTO itineraryDetailDTO)
            throws InterruptedException, ExecutionException {
        if (itineraryDetailDTO != null) {
//            Itinerary itinerary = itineraryRepo.findItineraryByDescription(itineraryDetailDTO.getItineraryName());
//            log.info("Finished get object itinerary with itinerary name: " + itinerary.getDescription());
//            log.info("Start update intinerary Detail");
//            ItineraryDetail itineraryDetail = new ItineraryDetail(itineraryDetailDTO.getId(), itinerary,
//                    itineraryDetailDTO.getTitle(), itineraryDetailDTO.getDescription());
//            itineraryDetailRepo.save(itineraryDetail);
//            log.info("Update itinerary success with Id: " + itineraryDetail.getId());
            return 0;
        }
        return 1;
    }

    @Override
    public String deleteItineraryDetail(long id) throws InterruptedException, ExecutionException {
        if (id > 0) {
            itineraryDetailRepo.deleteById(id);
            return "delete success id - " + id;
        }
        return "Delete failed";
    }

    @Override
    public ItineraryDetailDTO getItineraryDetailById(long id) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public List<ItineraryDetailDTO> findItineraryDetailByItineraryId(long id)
            throws InterruptedException, ExecutionException {
//        return itineraryDetailRepo.findItineraryDetailByItineraryId(id).stream().map(itineraryDetail -> {
//            ItineraryDetailDTO dto = new ItineraryDetailDTO();
//            dto.setId(itineraryDetail.getId());
//            dto.setTitle(itineraryDetail.getTitile());
//            dto.setDescription(itineraryDetail.getDescription());
//            dto.setItineraryName(itineraryDetail.getItinerary().getDescription());
//            return dto;
//        }).toList();
        return null;
    }

}
