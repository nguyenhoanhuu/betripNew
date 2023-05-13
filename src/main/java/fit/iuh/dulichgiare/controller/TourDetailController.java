package fit.iuh.dulichgiare.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.TourDetailDTO;
import fit.iuh.dulichgiare.service.TourDetailService;

@RestController
@RequestMapping("/tourdetails")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TourDetailController {
    @Autowired
    private TourDetailService tourDetailService;

    @GetMapping(value = { "", "/" })
    public List<TourDetailDTO> getAllTours() throws InterruptedException, ExecutionException {
        return tourDetailService.getAllTours();
    }

    @PostMapping(value = { "", "/save" })
    public int saveTourDetail(@RequestBody TourDetailDTO tourDetailDTO)
            throws InterruptedException, ExecutionException {
        return tourDetailService.saveTourDetail(tourDetailDTO);
    }

    @PostMapping(value = { "", "/update" })
    public int updateTourDetail(TourDetailDTO tourDetailDTO) throws InterruptedException, ExecutionException {
        return tourDetailService.updateTourDetail(tourDetailDTO);
    }

    @GetMapping(value = { "/tourId/{id}" })
    public TourDetailDTO getTourDetailByTourId(@PathVariable long id) throws InterruptedException, ExecutionException {
        return tourDetailService.getTourDetailByTourId(id);

    }
}
