package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.TourDetailDTO;

@Service
public interface TourDetailService {
    public List<TourDetailDTO> getAllTours() throws InterruptedException, ExecutionException;

    public int saveTourDetail(TourDetailDTO tourDetailDTO) throws InterruptedException, ExecutionException;

    public int updateTourDetail(TourDetailDTO tourDetailDTO) throws InterruptedException, ExecutionException;

    public String deleteTourDetail(long id) throws InterruptedException, ExecutionException;

    public TourDetailDTO getTourDetailById(long id) throws InterruptedException, ExecutionException;

    public TourDetailDTO getTourDetailByTourId(long id) throws InterruptedException, ExecutionException;
}
