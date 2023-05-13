package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fit.iuh.dulichgiare.dto.TourGuideTourDTO;
import fit.iuh.dulichgiare.entity.TourGuideTour;

public interface TourGuideTourService {

    public List<TourGuideTour> getAllTourGuideTour() throws InterruptedException, ExecutionException;

    public List<TourGuideTourDTO> getTourGuideTourByTourId(long tourId) throws InterruptedException, ExecutionException;

    public TourGuideTour getTourGuideTourById(long id) throws InterruptedException, ExecutionException;

    public TourGuideTourDTO saveTourGuideTour(TourGuideTourDTO tourGuideTourDTO)
            throws InterruptedException, ExecutionException;

    public int updateTourGuideTour(TourGuideTour tourGuideTour) throws InterruptedException, ExecutionException;

    public String deleteTourGuideTour(long id) throws InterruptedException, ExecutionException;

}
