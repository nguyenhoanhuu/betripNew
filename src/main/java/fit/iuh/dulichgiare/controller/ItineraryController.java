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

import fit.iuh.dulichgiare.dto.ItineraryDTO;
import fit.iuh.dulichgiare.dto.ItineraryDetailDTO;
import fit.iuh.dulichgiare.service.ItineraryService;

@RestController
@RequestMapping("/itinerarys")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @GetMapping(value = { "", "/" })
    public List<ItineraryDTO> getAllItinerarys() throws InterruptedException, ExecutionException {
        return itineraryService.getAllItinerarys();
    }

    @PostMapping(value = { "", "/save" })
    public int saveItinerary(@RequestBody ItineraryDTO itineraryDTO) throws InterruptedException, ExecutionException {
        return itineraryService.saveItinerary(itineraryDTO);
    }

    @PostMapping(value = { "", "/update" })
    public int updateItinerary(@RequestBody ItineraryDTO itineraryDTO) throws InterruptedException, ExecutionException {
        return itineraryService.updateItinerary(itineraryDTO);
    }

    public String deleteItinerary(long id) throws InterruptedException, ExecutionException {
        return "12";
    }

    @GetMapping("/{id}")
    public ItineraryDTO getItineraryById(@PathVariable long id) throws InterruptedException, ExecutionException {
        return itineraryService.getItineraryById(id);
    }

    @GetMapping("/tourId/{tourId}")
    public List<ItineraryDetailDTO> getItineraryByTourId(@PathVariable long tourId)
            throws InterruptedException, ExecutionException {
        return itineraryService.getItineraryByTourId(tourId);
    }
}
