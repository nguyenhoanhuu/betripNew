package fit.iuh.dulichgiare.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.TourGuideTourDTO;
import fit.iuh.dulichgiare.entity.TourGuideTour;
import fit.iuh.dulichgiare.service.TourGuideTourService;

@RestController
@RequestMapping("/tourguidetours")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TourGuideTourController {
    @Autowired
    private TourGuideTourService tourGuideTourService;

    @GetMapping(value = { "", "/" })
    public List<TourGuideTour> getAllTourGuideTour() throws InterruptedException, ExecutionException {
        return tourGuideTourService.getAllTourGuideTour();
    }

    @GetMapping("/{id}")
    public TourGuideTour getTourGuideTourById(@PathVariable long id) throws InterruptedException, ExecutionException {
        return tourGuideTourService.getTourGuideTourById(id);
    }

    @PostMapping(value = { "", "/save" })
    public TourGuideTourDTO saveTourGuideTour(@RequestBody TourGuideTourDTO tourGuideTour)
            throws InterruptedException, ExecutionException {
        return tourGuideTourService.saveTourGuideTour(tourGuideTour);
    }

    @PostMapping(value = { "", "/update" })
    public int updateTourGuideTour(@RequestBody TourGuideTour tourGuideTour)
            throws InterruptedException, ExecutionException {
        return tourGuideTourService.updateTourGuideTour(tourGuideTour);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTourGuideTour(@PathVariable long id) throws InterruptedException, ExecutionException {
        return tourGuideTourService.deleteTourGuideTour(id);
    }
}
