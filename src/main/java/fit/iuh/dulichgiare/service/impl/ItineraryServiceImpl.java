package fit.iuh.dulichgiare.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.ItineraryDTO;
import fit.iuh.dulichgiare.dto.ItineraryDetailDTO;
import fit.iuh.dulichgiare.entity.Itinerary;
import fit.iuh.dulichgiare.repository.ItineraryDetailRepository;
import fit.iuh.dulichgiare.repository.ItineraryRepository;
import fit.iuh.dulichgiare.service.ItineraryService;
import jakarta.transaction.Transactional;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepo;
    @Autowired
    private ItineraryDetailRepository itineraryDetailRepo;

    @Transactional
    @Override
    public List<ItineraryDTO> getAllItinerarys() throws InterruptedException, ExecutionException {
        return itineraryRepo.findAll().stream().map(itinerary -> {
            ItineraryDTO dto = new ItineraryDTO();
            dto.setId(itinerary.getId());
            dto.setTourId(itinerary.getTour().getId());
            dto.setDescription(itinerary.getDescription());
            return dto;
        }).toList();
    }

    @Transactional
    @Override
    public int saveItinerary(ItineraryDTO itineraryDTO) throws InterruptedException, ExecutionException {
//        if (itineraryDTO != null) {
//            Tour tour = tourRepository.findById(itineraryDTO.getTourId()).get();
//            Tour tour2 = new Tour(itineraryDTO.getTourId(), tour.getName(), tour.getImage(), tour.getDeparture(),
//                    tour.getDestination(), tour.getStartday(), tour.getNumberofday(), tour.getNumberofpeople(),
//                    tour.getSubcriber(), tour.getType(), tour.getPrice(), tour.getCreateat(), tour.getLiked());
//
//            Itinerary itinerary = new Itinerary(tour2, itineraryDTO.getDescription());
//            itineraryRepository.save(itinerary);
//            return 0;
//        }
        return 1;
    }

    @Transactional
    @Override
    public int updateItinerary(ItineraryDTO itineraryDTO) throws InterruptedException, ExecutionException {
//        if (itineraryDTO != null) {
//            Tour tour = tourRepository.findById(itineraryDTO.getTourId()).get();
//            Tour tour2 = new Tour(itineraryDTO.getTourId(), tour.getName(), tour.getImage(), tour.getDeparture(),
//                    tour.getDestination(), tour.getStartday(), tour.getNumberofday(), tour.getNumberofpeople(),
//                    tour.getSubcriber(), tour.getType(), tour.getPrice(), tour.getCreateat(), tour.getLiked());
//
//            Itinerary itinerary = new Itinerary(itineraryDTO.getId(), tour2, itineraryDTO.getDescription());
//            itineraryRepository.save(itinerary);
//            return 0;
//        }
        return 1;
    }

    @Transactional
    @Override
    public String deleteItinerary(long id) throws InterruptedException, ExecutionException {
        return null;
    }

    @Transactional
    @Override
    public ItineraryDTO getItineraryById(long id) throws InterruptedException, ExecutionException {
        Itinerary itinerary = itineraryRepo.findById(id).orElse(null);
        try {
            return new ItineraryDTO(itinerary.getId(), itinerary.getTour().getId(), itinerary.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<ItineraryDetailDTO> getItineraryByTourId(long tourId) throws InterruptedException, ExecutionException {
        Itinerary itinerary = itineraryRepo.findItineraryByTourId(tourId);
        return itineraryDetailRepo.findItineraryDetailByItineraryId(itinerary.getId()).stream().map(itineraryDetail -> {
            ItineraryDetailDTO dto = new ItineraryDetailDTO();
            dto.setId(itineraryDetail.getId());
            dto.setTitle(itineraryDetail.getTitile());
            dto.setDescription(itineraryDetail.getDescription());
            return dto;
        }).toList();
    }

}
