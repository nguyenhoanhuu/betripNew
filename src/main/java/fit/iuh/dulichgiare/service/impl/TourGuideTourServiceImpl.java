package fit.iuh.dulichgiare.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.TourGuideTourDTO;
import fit.iuh.dulichgiare.entity.Tour;
import fit.iuh.dulichgiare.entity.TourGuide;
import fit.iuh.dulichgiare.entity.TourGuideTour;
import fit.iuh.dulichgiare.repository.TourGuideRepository;
import fit.iuh.dulichgiare.repository.TourGuideTourRepository;
import fit.iuh.dulichgiare.repository.TourRepository;
import fit.iuh.dulichgiare.service.TourGuideTourService;

@Service
public class TourGuideTourServiceImpl implements TourGuideTourService {

    @Autowired
    private TourGuideTourRepository tourGuideTourRepo;
    @Autowired
    private TourRepository tourRepo;
    @Autowired
    private TourGuideRepository tourGuideRepo;

    @Override
    public List<TourGuideTour> getAllTourGuideTour() throws InterruptedException, ExecutionException {
        return tourGuideTourRepo.findAll();
    }

    @Override
    public TourGuideTour getTourGuideTourById(long id) throws InterruptedException, ExecutionException {
        return tourGuideTourRepo.findById(id).get();
    }

    @Override
    public TourGuideTourDTO saveTourGuideTour(TourGuideTourDTO tourGuideTourDTO)
            throws InterruptedException, ExecutionException {
        Tour tour = tourRepo.findTourByName(tourGuideTourDTO.getNameTour());
        TourGuide tourGuide = tourGuideRepo.findTourGuideByName(tourGuideTourDTO.getNameTourGuide());
        TourGuideTour tourGuideTour = new TourGuideTour(tour, tourGuide);
        tourGuideTourRepo.save(tourGuideTour);
        return new TourGuideTourDTO(tourGuideTour.getId(), tourGuideTour.getTour().getName(),
                tourGuideTour.getTourguide().getName());
    }

    @Override
    public int updateTourGuideTour(TourGuideTour tourGuideTour) throws InterruptedException, ExecutionException {
        if (tourGuideTour != null) {
            tourGuideTourRepo.save(tourGuideTour);
            return 0;
        }
        return 1;
    }

    @Override
    public String deleteTourGuideTour(long id) throws InterruptedException, ExecutionException {
        if (id > 0) {
            tourGuideTourRepo.deleteById(id);
            return "delete success id - " + id;
        }
        return "Delete failed";
    }

    @Override
    public List<TourGuideTourDTO> getTourGuideTourByTourId(long tourId)
            throws InterruptedException, ExecutionException {
        return tourGuideTourRepo.findByTourId(tourId).stream().map(tourGuideTour -> {
            TourGuideTourDTO dto = new TourGuideTourDTO();
            dto.setId(tourGuideTour.getId());
            dto.setNameTour(tourGuideTour.getTour().getName());
            dto.setNameTourGuide(tourGuideTour.getTourguide().getName());
            return dto;
        }).toList();
    }

}
