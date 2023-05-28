package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.DestinationCount;
import fit.iuh.dulichgiare.dto.ResponseFavoriteDestination;
import fit.iuh.dulichgiare.dto.TourDTO;
import fit.iuh.dulichgiare.dto.TourDTOImages;
import fit.iuh.dulichgiare.dto.TourDTOSave;

/**
 * 
 * @author HOAN HUU
 *
 */
@Service
public interface TourService {
	public List<TourDTOImages> getAllTours(Integer pageNo, Integer pageSize, String sortBy)
			throws InterruptedException, ExecutionException;

	public int saveTour(TourDTOSave tourDTOSave, String userName) throws InterruptedException, ExecutionException;
	
	public int saveTourWhenUserRequestTour(TourDTOSave tourDTOSave, String userName) throws InterruptedException, ExecutionException;

	public int updateTour(TourDTOSave tourDTOSave, String userName) throws InterruptedException, ExecutionException ;

	public int deleteTour(long id) throws InterruptedException, ExecutionException;

	public TourDTO getTourById(long id) throws InterruptedException, ExecutionException;

	public List<TourDTOImages> getTopFavoriteTours(int number) throws InterruptedException, ExecutionException;

	public List<TourDTOImages> getRandomTours(String type);

	public List<TourDTOImages> getAllToursByName(String name) throws InterruptedException, ExecutionException;

	public List<TourDTOImages> getAlls(String departure, String destination, double startPrice, double endPrice,
			String type, Integer pageNo, Integer pageSize, String sortBy, long numberDays, int checkPromotion,
			int checkSubcriber);

	public List<TourDTOSave> LastTimeMinuteTour() throws InterruptedException, ExecutionException;

	public List<DestinationCount> getTopFavoriteDestinations(int numberFavorite, int numberDay);

	public void updateActiveStatus();

	public List<TourDTOImages> getTop3ToursSoonToExpireWithPromotion();

	public List<ResponseFavoriteDestination> getTop8FavoriteDestinations();

	public List<TourDTOImages> getAllTourNorthDestination();

	public List<TourDTOImages> getAllTourSorthDestination();

	public List<TourDTOImages> getAllTourCentralDestination();

}
