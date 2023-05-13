package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.TourGuideDTO;
import fit.iuh.dulichgiare.entity.TourGuide;

/**
 * 
 * @author HOAN HUU
 *
 */
@Service
public interface TourGuideService {

    public List<TourGuideDTO> getAllTourGuides() throws InterruptedException, ExecutionException;

    public int saveTourGuide(TourGuideDTO tourGuide) throws InterruptedException, ExecutionException;

    public int updateTourGuide(TourGuide tourGuide) throws InterruptedException, ExecutionException;

    public String deleteTourGuide(long id) throws InterruptedException, ExecutionException;

    public TourGuideDTO getTourGuideById(long id) throws InterruptedException, ExecutionException;

    public List<String> getAllNameTourGuide();

}
