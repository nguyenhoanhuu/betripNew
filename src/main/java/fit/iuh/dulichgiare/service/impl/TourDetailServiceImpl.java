package fit.iuh.dulichgiare.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.TourDetailDTO;
import fit.iuh.dulichgiare.entity.TourDetail;
import fit.iuh.dulichgiare.repository.TourDetailRepository;
import fit.iuh.dulichgiare.service.TourDetailService;

@Service
public class TourDetailServiceImpl implements TourDetailService {

    @Autowired
    private TourDetailRepository tourDetailRepository;

    @Override
    public List<TourDetailDTO> getAllTours() throws InterruptedException, ExecutionException {
        return tourDetailRepository.findAll().stream().map(tourdetail -> {
            TourDetailDTO dto = new TourDetailDTO();
            dto.setId(tourdetail.getId());
            dto.setTourId(tourdetail.getTour().getId());
            dto.setTransport(tourdetail.getTransport());
            dto.setDescription(tourdetail.getDescription());
            dto.setStarHotel(tourdetail.getstarhotel());
            return dto;
        }).toList();
    }

    @Override
    public int saveTourDetail(TourDetailDTO tourDetailDTO) throws InterruptedException, ExecutionException {
//        if (tourDetailDTO != null) {
//            TourDTO tourDTO = tourService.getTourById(tourDetailDTO.getTourId());
//            Tour tour = new Tour();
//            tour.setId(tourDTO.getId());
//            tour.setName(tourDTO.getName());
//            tour.setImage(tourDTO.getImage());
//            tour.setDeparture(tourDTO.getDeparture());
//            tour.setDestination(tourDTO.getDestination());
//            tour.setStartday(tourDTO.getStartday());
//            tour.setNumberofday(tourDTO.getNumberofday());
//            tour.setNumberofpeople(tourDTO.getNumberofpeople());
//            tour.setSubcriber(tourDTO.getSubcriber());
//            tour.setType(tourDTO.getType());
//            tour.setPrice(tourDTO.getPrice());
//            tour.setCreateat(tourDTO.getCreateat());
//            tour.setLiked(tourDTO.getLiked());
//
//            TourDetail tourDetail1 = new TourDetail(tourDetailDTO.getDescription(), tourDetailDTO.getTransport(),
//                    tourDetailDTO.getStarhotel(), tour);
//            tourDetailRepository.save(tourDetail1);
//            return 0;
//        }
        return 1;
    }

    @Override
    public int updateTourDetail(TourDetailDTO tourDetailDTO) throws InterruptedException, ExecutionException {
//        if (tourDetailDTO != null) {
//            TourDTO tourDTO = tourService.getTourById(tourDetailDTO.getTourId());
//            Tour tour = new Tour();
//            tour.setId(tourDTO.getId());
//            tour.setName(tourDTO.getName());
//            tour.setImage(tourDTO.getImage());
//            tour.setDeparture(tourDTO.getDeparture());
//            tour.setDestination(tourDTO.getDestination());
//            tour.setStartday(tourDTO.getStartday());
//            tour.setNumberofday(tourDTO.getNumberofday());
//            tour.setNumberofpeople(tourDTO.getNumberofpeople());
//            tour.setSubcriber(tourDTO.getSubcriber());
//            tour.setType(tourDTO.getType());
//            tour.setPrice(tourDTO.getPrice());
//            tour.setCreateat(tourDTO.getCreateat());
//            tour.setLiked(tourDTO.getLiked());
//
//            TourDetail tourDetail1 = new TourDetail(tourDetailDTO.getId(), tourDetailDTO.getDescription(),
//                    tourDetailDTO.getTransport(), tourDetailDTO.getStarhotel(), tour);
//            tourDetailRepository.save(tourDetail1);
//            return 0;
//        }
        return 1;
    }

    @Override
    public String deleteTourDetail(long id) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public TourDetailDTO getTourDetailById(long id) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public TourDetailDTO getTourDetailByTourId(long id) throws InterruptedException, ExecutionException {
        TourDetail tourDetail = tourDetailRepository.findTourDetailByTourId(id);
        TourDetailDTO dto = new TourDetailDTO();
        dto.setId(tourDetail.getId());
        dto.setTourId(tourDetail.getTour().getId());
        dto.setTransport(tourDetail.getTransport());
        dto.setDescription(tourDetail.getDescription());
        dto.setStarHotel(tourDetail.getstarhotel());
        return dto;
    }

}
