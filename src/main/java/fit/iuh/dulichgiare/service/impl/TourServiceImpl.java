package fit.iuh.dulichgiare.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.constant.Constants;
import fit.iuh.dulichgiare.dto.DestinationCount;
import fit.iuh.dulichgiare.dto.ItineraryDetailDTO;
import fit.iuh.dulichgiare.dto.PolicyDTO;
import fit.iuh.dulichgiare.dto.ResponseFavoriteDestination;
import fit.iuh.dulichgiare.dto.TourDTO;
import fit.iuh.dulichgiare.dto.TourDTOImages;
import fit.iuh.dulichgiare.dto.TourDTOSave;
import fit.iuh.dulichgiare.dto.TourDetailDTO;
import fit.iuh.dulichgiare.dto.TourGuideDTO;
import fit.iuh.dulichgiare.entity.Booking;
import fit.iuh.dulichgiare.entity.Employee;
import fit.iuh.dulichgiare.entity.Itinerary;
import fit.iuh.dulichgiare.entity.ItineraryDetail;
import fit.iuh.dulichgiare.entity.Policy;
import fit.iuh.dulichgiare.entity.Promotion;
import fit.iuh.dulichgiare.entity.Tour;
import fit.iuh.dulichgiare.entity.TourDetail;
import fit.iuh.dulichgiare.entity.TourGuide;
import fit.iuh.dulichgiare.entity.TourGuideTour;
import fit.iuh.dulichgiare.repository.BookingRepository;
import fit.iuh.dulichgiare.repository.EmployeeRepository;
import fit.iuh.dulichgiare.repository.ItineraryDetailRepository;
import fit.iuh.dulichgiare.repository.ItineraryRepository;
import fit.iuh.dulichgiare.repository.PolicyRepository;
import fit.iuh.dulichgiare.repository.PromotionRepository;
import fit.iuh.dulichgiare.repository.TourDetailRepository;
import fit.iuh.dulichgiare.repository.TourGuideRepository;
import fit.iuh.dulichgiare.repository.TourGuideTourRepository;
import fit.iuh.dulichgiare.repository.TourRepository;
import fit.iuh.dulichgiare.service.PolicyService;
import fit.iuh.dulichgiare.service.TourGuideService;
import fit.iuh.dulichgiare.service.TourService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
@Component
public class TourServiceImpl implements TourService {

	NumberFormat formatter = new DecimalFormat("#0.0");

	@Autowired
	private TourRepository tourRepo;
	@Autowired
	private TourGuideRepository tourGuideRepo;
	@Autowired
	private TourGuideTourRepository tourGuideTourRepo;
	@Autowired
	private PolicyRepository policyRepo;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private ItineraryRepository itineraryRepo;
	@Autowired
	private ItineraryDetailRepository itineraryDetailRepo;
	@Autowired
	private PromotionRepository promotionRepo;
	@Autowired
	private TourDetailRepository tourDetailRepo;
	@Autowired
	private TourGuideService tourGuideService;
	@Autowired
	private BookingRepository bookingRepo;
	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public List<TourDTOImages> getAllTours(Integer pageNo, Integer pageSize, String sortBy)
			throws InterruptedException, ExecutionException {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).descending());
		return tourRepo.findAllByActive(pageable, true).stream().map(tour -> {
			Itinerary itinerary = itineraryRepo.findItineraryByTourId(tour.getId());
			TourDTOImages dto = new TourDTOImages();
			dto.setId(tour.getId());
			dto.setName(tour.getName());
			String[] imagesTour = tour.getImage().split(",");
			dto.setImages(getImages(imagesTour));
			dto.setDepartureTime(tour.getDepartureTime());
			dto.setDeparture(tour.getDeparture());
			dto.setDestination(tour.getDestination());
			dto.setStartDay(tour.getStartday());
			dto.setEndDay(tour.getEndday());
			dto.setNumberOfDay(tour.getNumberofday());
			dto.setNumberOfPeople(tour.getNumberofpeople());
			dto.setSubcriber(tour.getSubcriber());
			dto.setPrice(tour.getPrice());
			dto.setCreateAt(tour.getCreateat());
			dto.setLiked(tour.getLiked());
			dto.setCreatedBy(tour.getCreatedBy());
			dto.setType(tour.getType());
			if (itinerary != null) {
				dto.setItineraryName(itinerary.getDescription());
			}
			return dto;
		}).toList();
	}

	@Override
	public int saveTour(TourDTOSave tourDTOSave, String userName) throws InterruptedException, ExecutionException {
		Employee employee = employeeRepo.findEmployeeByPhone(userName);

		if (employee == null) {
			return 0;
		}
		if (checkTourExisted(tourDTOSave.getName())) {
			return 3;
		}

		if (tourDTOSave != null && !tourDTOSave.getPolicyName().isEmpty()) {

			Policy policy = policyRepo.findPolicyByPolicyName(tourDTOSave.getPolicyName());
			Promotion promotion = promotionRepo.findPromotionByName(tourDTOSave.getPromotionName());
			Tour tourSave = null;
			if (promotion == null) {
				Tour tour = new Tour(true, tourDTOSave.getName(), tourDTOSave.getImage(),
						tourDTOSave.getDepartureTime(), tourDTOSave.getDeparture(), tourDTOSave.getDestination(),
						tourDTOSave.getStartDay(), tourDTOSave.getEndDay(), tourDTOSave.getNumberOfDay(),
						tourDTOSave.getNumberOfPeople(), 0, tourDTOSave.getType(), tourDTOSave.getPrice(),
						LocalDateTime.now(), 0, employee.getName(), policy);
				tourSave = tourRepo.save(tour);
			} else {
				Tour tour = new Tour(true, tourDTOSave.getName(), tourDTOSave.getImage(),
						tourDTOSave.getDepartureTime(), tourDTOSave.getDeparture(), tourDTOSave.getDestination(),
						tourDTOSave.getStartDay(), tourDTOSave.getEndDay(), tourDTOSave.getNumberOfDay(),
						tourDTOSave.getNumberOfPeople(), 0, tourDTOSave.getType(), tourDTOSave.getPrice(),
						LocalDateTime.now(), 0, employee.getName(), promotion, policy);
				tourSave = tourRepo.save(tour);
			}
			if (tourSave.getId() > 0 && !(tourDTOSave.getTourGuideName().isEmpty())) {
				for (String tourGuideName : tourDTOSave.getTourGuideName()) {
					TourGuide tourGuide = tourGuideRepo.findTourGuideByName(tourGuideName);
					TourGuideTour tourGuideTour = new TourGuideTour(tourSave, tourGuide);
					tourGuideTourRepo.save(tourGuideTour);
				}
			}
			if (tourSave != null) {
				TourDetail tourDetail = new TourDetail(tourDTOSave.getTourDetail().getDescription(),
						tourDTOSave.getTourDetail().getTransport(), tourDTOSave.getTourDetail().getStarHotel(),
						tourSave);
				tourDetailRepo.save(tourDetail);
			}
			Itinerary itinerary = new Itinerary(tourSave, tourDTOSave.getName());
			itineraryRepo.save(itinerary);
			List<ItineraryDetail> itineraryDetails = new ArrayList<>();
			for (ItineraryDetailDTO iterableDTO : tourDTOSave.getItineraryDetail()) {
				ItineraryDetail itineraryDetail = new ItineraryDetail();
				itineraryDetail.setDescription(iterableDTO.getDescription());
				itineraryDetail.setTitile(iterableDTO.getTitle());
				itineraryDetail.setItinerary(itinerary);
				itineraryDetails.add(itineraryDetail);
			}
			itineraryDetailRepo.saveAll(itineraryDetails);
			return 1;
		}
		return 2;
	}

	@Override
	public int updateTour(TourDTOSave tourDTOSave, String userName) throws InterruptedException, ExecutionException {
		Employee employee = employeeRepo.findEmployeeByPhone(userName);
		if (employee == null) {
			return 0;
		}
		if (tourDTOSave != null && !tourDTOSave.getPolicyName().isEmpty()) {
			Policy policy = policyRepo.findPolicyByPolicyName(tourDTOSave.getPolicyName());
			Promotion promotion = promotionRepo.findPromotionByName(tourDTOSave.getPromotionName());
			Tour tourSave = null;
			if (promotion == null) {
				Tour tour = new Tour(true, tourDTOSave.getName(), tourDTOSave.getImage(),
						tourDTOSave.getDepartureTime(), tourDTOSave.getDeparture(), tourDTOSave.getDestination(),
						tourDTOSave.getStartDay(), tourDTOSave.getEndDay(), tourDTOSave.getNumberOfDay(),
						tourDTOSave.getNumberOfPeople(), 0, tourDTOSave.getType(), tourDTOSave.getPrice(),
						LocalDateTime.now(), 0, employee.getName(), policy);
				tourSave = tourRepo.save(tour);
			} else {
				Tour tour = new Tour(true, tourDTOSave.getName(), tourDTOSave.getImage(),
						tourDTOSave.getDepartureTime(), tourDTOSave.getDeparture(), tourDTOSave.getDestination(),
						tourDTOSave.getStartDay(), tourDTOSave.getEndDay(), tourDTOSave.getNumberOfDay(),
						tourDTOSave.getNumberOfPeople(), 0, tourDTOSave.getType(), tourDTOSave.getPrice(),
						LocalDateTime.now(), 0, employee.getName(), promotion, policy);
				tourSave = tourRepo.save(tour);
			}
			if (tourSave.getId() > 0 && !(tourDTOSave.getTourGuideName().isEmpty())) {
				for (String tourGuideName : tourDTOSave.getTourGuideName()) {
					TourGuide tourGuide = tourGuideRepo.findTourGuideByName(tourGuideName);
					TourGuideTour tourGuideTour = new TourGuideTour(tourSave, tourGuide);
					tourGuideTourRepo.save(tourGuideTour);
				}
			}
			if (tourSave != null) {
				TourDetail tourDetail = new TourDetail(tourDTOSave.getTourDetail().getDescription(),
						tourDTOSave.getTourDetail().getTransport(), tourDTOSave.getTourDetail().getStarHotel(),
						tourSave);
				tourDetailRepo.save(tourDetail);
			}
			Itinerary itinerary = new Itinerary(tourSave, tourDTOSave.getName());
			itineraryRepo.save(itinerary);
			List<ItineraryDetail> itineraryDetails = new ArrayList<>();
			for (ItineraryDetailDTO iterableDTO : tourDTOSave.getItineraryDetail()) {
				ItineraryDetail itineraryDetail = new ItineraryDetail();
				itineraryDetail.setDescription(iterableDTO.getDescription());
				itineraryDetail.setTitile(iterableDTO.getTitle());
				itineraryDetail.setItinerary(itinerary);
				itineraryDetails.add(itineraryDetail);
			}
			itineraryDetailRepo.saveAll(itineraryDetails);
			return 1;
		}
		return 2;
	}

	@Transactional
	@Override
	public int deleteTour(long id) throws InterruptedException, ExecutionException {
		Tour tour = tourRepo.findTourByActiveTrueAndId(id);
		if (tour != null) {
			tour.setActive(false);
			tourRepo.save(tour);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public TourDTO getTourById(long id) throws InterruptedException, ExecutionException {
		TourDTO tourDTO = new TourDTO();
		List<TourGuideDTO> tourGuideList = new ArrayList<>();
		List<Long> tourGuideIdList = new ArrayList<>();
		Tour tour = tourRepo.findByIdAndActive(id, true);
		if (tour != null) {
			tourDTO.setId(tour.getId());
			tourDTO.setName(tour.getName());
			String[] imagesTour = tour.getImage().split(",");
			tourDTO.setImages(getImages(imagesTour));
			tourDTO.setDepartureTime(tour.getDepartureTime());
			tourDTO.setDeparture(tour.getDeparture());
			tourDTO.setDestination(tour.getDestination());
			tourDTO.setStartDay(tour.getStartday());
			tourDTO.setEndDay(tour.getEndday());
			tourDTO.setNumberOfDay(tour.getNumberofday());
			tourDTO.setNumberOfPeople(tour.getNumberofpeople());
			tourDTO.setSubcriber(tour.getSubcriber());
			tourDTO.setPrice(tour.getPrice());
			tourDTO.setCreateAt(tour.getCreateat());
			tourDTO.setLiked(tour.getLiked());
			tourDTO.setCreatedBy(tour.getCreatedBy());
			tourDTO.setType(tour.getType());
			if (tour.getPromotion() == null) {
			} else {
				Promotion promotion = promotionRepo.findById(tour.getPromotion().getId()).get();
				if (checkExpriryDate(promotion) == true) {
					tourDTO.setPromotionPrice(Double
							.valueOf(formatter.format(tour.getPrice() - (tour.getPrice() * promotion.getDiscount()))));
					tourDTO.setAdultPrice(Double
							.valueOf(formatter.format(tour.getPrice() - (tour.getPrice() * promotion.getDiscount()))));
					tourDTO.setChildPrice(Double.valueOf(
							formatter.format((tour.getPrice() * 0.5) - (tour.getPrice() * promotion.getDiscount()))));
					tourDTO.setBabyPrice(Double.valueOf(
							formatter.format((tour.getPrice() * 0.3) - (tour.getPrice() * promotion.getDiscount()))));
				} else if (checkExpriryDate(promotion) == false || promotion == null) {
					tourDTO.setPromotionPrice(tour.getPrice());
					tourDTO.setAdultPrice(tour.getPrice());
					tourDTO.setChildPrice(Double.valueOf(formatter.format(tour.getPrice() * 0.5)));
					tourDTO.setBabyPrice(Double.valueOf(formatter.format(tour.getPrice() * 0.3)));
				}
			}
			if (tour.getPolicy().getId() > 0) {
				PolicyDTO policyDTO = policyService.getPolicyById(tour.getPolicy().getId());
				tourDTO.setPolicy(policyDTO);
			}
			List<TourGuideTour> tourGuideTours = tourGuideTourRepo.findByTourId(tour.getId());
			for (TourGuideTour tourGuideTour : tourGuideTours) {
				tourGuideIdList.add(tourGuideTour.getTourguide().getId());
			}

			for (Long tourGuideId : tourGuideIdList) {
				TourGuideDTO tourGuide = tourGuideService.getTourGuideById(tourGuideId);
				tourGuideList.add(tourGuide);
			}
			tourDTO.setTourGuides(tourGuideList);

			TourDetail tourDetail = tourDetailRepo.findTourDetailByTourId(tour.getId());
			if (tourDetail == null) {
				tourDTO.setTourDetail(null);
			} else {
				TourDetailDTO tourDetailDTO = new TourDetailDTO(tourDetail.getId(), tourDetail.getDescription(),
						tourDetail.getTransport(), tourDetail.getstarhotel());
				tourDTO.setTourDetail(tourDetailDTO);
			}
			List<ItineraryDetail> itineraryDetails = itineraryDetailRepo.findAllItineraryDetailByTourId(id);
			if (itineraryDetails == null) {
				tourDTO.setItineraryDetail(null);
			} else {
				List<ItineraryDetailDTO> itineraryDetailDTOs = new ArrayList<>();
				for (ItineraryDetail itineraryDetail : itineraryDetails) {
					ItineraryDetailDTO itineraryDetailDTO = new ItineraryDetailDTO();
					itineraryDetailDTO.setId(itineraryDetail.getId());
					itineraryDetailDTO.setTitle(itineraryDetail.getTitile());
					itineraryDetailDTO.setDescription(itineraryDetail.getDescription());
					itineraryDetailDTOs.add(itineraryDetailDTO);
				}
				tourDTO.setItineraryDetail(itineraryDetailDTOs);
			}

			return tourDTO;

		} else {
			return null;
		}
	}

	@Override
	public List<TourDTOImages> getTopFavoriteTours(int number) throws InterruptedException, ExecutionException {
		List<TourDTOImages> dtos = new ArrayList<>();
		List<Tour> tours = tourRepo.findByTopLiked(number);
		for (Tour tour : tours) {
			TourDTOImages dto = new TourDTOImages();
			if (tour.getStartday().isAfter(LocalDate.now())) {
				if (tour.getPromotion() == null) {
					dto.setPromotionPrice(0);
				} else {
					Promotion promotion = promotionRepo.findById(tour.getPromotion().getId()).get();
					if (checkExpriryDate(promotion)) {
						dto.setPromotionPrice(Double.valueOf(
								formatter.format(tour.getPrice() - (tour.getPrice() * promotion.getDiscount()))));
					} else {
						dto.setPromotionPrice(0);
					}
				}
				dto.setId(tour.getId());
				dto.setName(tour.getName());
				String[] imagesTour = tour.getImage().split(",");
				dto.setImages(getImages(imagesTour));
				dto.setDeparture(tour.getDeparture());
				dto.setDestination(tour.getDestination());
				dto.setStartDay(tour.getStartday());
				dto.setEndDay(tour.getEndday());
				dto.setNumberOfDay(tour.getNumberofday());
				dto.setNumberOfPeople(tour.getNumberofpeople());
				dto.setSubcriber(tour.getSubcriber());
				dto.setPrice(tour.getPrice());
				dto.setCreateAt(tour.getCreateat());
				dto.setLiked(tour.getLiked());
				dto.setType(tour.getType());
				dto.setCreatedBy(tour.getCreatedBy());
				if (!dtos.contains(dto)) {
					dtos.add(dto);
				}
			}
		}
		return dtos;
	}

	@Override
	public List<TourDTOImages> getAllToursByName(String name) throws InterruptedException, ExecutionException {
		return tourRepo.findByNameContaining(name).stream().map(tour -> {
			TourDTOImages dto = new TourDTOImages();
			if (tour.getPromotion() == null) {
				dto.setPromotionPrice(0);
			} else {
				Promotion promotion = promotionRepo.findById(tour.getPromotion().getId()).get();
				if (checkExpriryDate(promotion)) {
					dto.setPromotionPrice(Double
							.valueOf(formatter.format(tour.getPrice() - (tour.getPrice() * promotion.getDiscount()))));
				} else {
					dto.setPromotionPrice(0);
				}
			}
			dto.setId(tour.getId());
			dto.setName(tour.getName());
			String[] imagesTour = tour.getImage().split(",");
			dto.setImages(getImages(imagesTour));
			dto.setDeparture(tour.getDeparture());
			dto.setDestination(tour.getDestination());
			dto.setStartDay(tour.getStartday());
			dto.setStartDay(tour.getStartday());
			dto.setNumberOfDay(tour.getNumberofday());
			dto.setNumberOfPeople(tour.getNumberofpeople());
			dto.setSubcriber(tour.getSubcriber());
			dto.setPrice(tour.getPrice());
			dto.setCreateAt(tour.getCreateat());
			dto.setLiked(tour.getLiked());
			dto.setType(tour.getType());
			dto.setCreatedBy(tour.getCreatedBy());
			return dto;
		}).toList();
	}

	@Override
	public List<TourDTOImages> getAlls(String departure, String destination, double startPrice, double endPrice,
			String type, Integer pageNo, Integer pageSize, String sortBy, long numberDays, int checkPromotion,
			int checkSubcriber) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
		List<Tour> resultTours = new LinkedList<>();
		List<Tour> tours = tourRepo.findAllByActiveAndType(true, type, pageable);
		tours.stream().filter(tour -> tour.getStartday().isAfter(LocalDate.now())).filter(tour -> {
			long caculatorDate = ChronoUnit.DAYS.between(tour.getStartday(), tour.getEndday()) + 1;
			if (numberDays <= 0) {
				return true;
			} else if (numberDays > 0 && numberDays <= 3 && caculatorDate <= 3) {
				return true;
			} else if ((numberDays >= 4 && numberDays <= 7) && (caculatorDate >= 4 && caculatorDate <= 7)) {
				return true;
			} else if ((numberDays >= 8 && numberDays <= 14) && (caculatorDate >= 8 && caculatorDate <= 14)) {
				return true;
			} else {
				return numberDays > 14 && caculatorDate > 14;
			}
		}).filter(tour -> {
			if (type.contains(Constants.NGOAI_NUOC)) {
				return tour.getType().contains(Constants.NGOAI_NUOC);
			} else {
				return tour.getType().contains(Constants.TRONG_NUOC);
			}
		}).forEach(tour -> checkPromotionIsExistInTour(checkPromotion, tour, resultTours, checkSubcriber, departure,
				destination, startPrice, endPrice));
		return convertTourDTO(resultTours);
	}

//check promotion 1: have promotion, 0: haven't promotion
	private void checkPromotionIsExistInTour(int checkPromotion, Tour tour, List<Tour> resultTours, int checkSubcriber,
			String departure, String destination, double startPrice, double endPrice) {
		if ((checkPromotion == 1 && tour.getPromotion() != null)
				|| (checkPromotion == 0 && tour.getPromotion() == null)) {
			checkSubcriber(departure, destination, checkSubcriber, tour, resultTours, startPrice, endPrice);
		}
	}

	// check subcriber 1: have seat, 0: haven't promotion
	private void checkSubcriber(String departure, String destination, int checkSubcriber, Tour tour,
			List<Tour> resultTours, double startPrice, double endPrice) {
		if ((checkSubcriber == 1 && tour.getNumberofpeople() - tour.getSubcriber() > 0)) {
			checkDepartureAndDestination(departure, destination, tour, resultTours, startPrice, endPrice);
		} else if ((checkSubcriber == 0 && tour.getNumberofpeople() - tour.getSubcriber() <= 0)) {
			checkDepartureAndDestination(departure, destination, tour, resultTours, startPrice, endPrice);
		}
	}

	private void checkDepartureAndDestination(String departure, String destination, Tour tour, List<Tour> resultTours,
			double startPrice, double endPrice) {
		if (tour.getDeparture().contains(departure) && tour.getDestination().contains(destination)
				&& (!departure.isEmpty() && !destination.isEmpty())) {
			checkStartPriceAndEndPrice(startPrice, endPrice, tour, resultTours);
		} else if (departure.isEmpty() && destination.isEmpty()) {
			checkStartPriceAndEndPrice(startPrice, endPrice, tour, resultTours);
		} else if (tour.getDeparture().contains(departure) && destination.isEmpty()) {
			checkStartPriceAndEndPrice(startPrice, endPrice, tour, resultTours);
		} else if (tour.getDestination().contains(destination) && departure.isEmpty()) {
			checkStartPriceAndEndPrice(startPrice, endPrice, tour, resultTours);
		}
	}

	private void checkStartPriceAndEndPrice(double startPrice, double endPrice, Tour tour, List<Tour> resultTours) {
		if (tour.getPrice() >= startPrice && tour.getPrice() <= endPrice) {
			resultTours.add(tour);
		}
	}

	public List<String> getImages(String[] imagesTour) {
		return Arrays.asList(imagesTour);
	}

	public boolean checkExpriryDate(Promotion promotion) {
		LocalDate today = LocalDate.now();
		if (promotion == null) {
			return false;
		}
		LocalDate dateToCheck = LocalDate.of(promotion.getEndday().getYear(), promotion.getEndday().getMonth(),
				promotion.getEndday().getDayOfMonth());
		if (dateToCheck.isBefore(today)) {
			log.info("Check day less than current day");
			return false;
		} else {
			log.info("check day more than current day");
			return true;
		}
	}

	public List<TourDTOImages> convertTourDTO(List<Tour> tours) {
		return tours.stream().map(tour -> {

			TourDTOImages dto = new TourDTOImages();
			Promotion promotion = tour.getPromotion();
			if (promotion != null && checkExpriryDate(promotion)) {
				double promotionPrice = Double
						.valueOf(formatter.format(tour.getPrice() - (tour.getPrice() * promotion.getDiscount())));
				dto.setPromotionPrice(promotionPrice);
			} else {
				dto.setPromotionPrice(0);
			}
			dto.setId(tour.getId());
			dto.setName(tour.getName());
			String[] imagesTour = tour.getImage().split(",");
			dto.setImages(getImages(imagesTour));
			dto.setDepartureTime(tour.getDepartureTime());
			dto.setDeparture(tour.getDeparture());
			dto.setDestination(tour.getDestination());
			dto.setStartDay(tour.getStartday());
			dto.setEndDay(tour.getEndday());
			dto.setNumberOfDay(tour.getNumberofday());
			dto.setNumberOfPeople(tour.getNumberofpeople());
			dto.setSubcriber(tour.getSubcriber());
			dto.setPrice(tour.getPrice());
			dto.setCreateAt(tour.getCreateat());
			dto.setLiked(tour.getLiked());
			dto.setType(tour.getType());
			dto.setCreatedBy(tour.getCreatedBy());
			return dto;
		}).toList();
	}

	@Override
	public List<TourDTOSave> LastTimeMinuteTour() throws InterruptedException, ExecutionException {
//        return tourRepo.findAll(Sort.by(Sort.Direction.ASC, "seatNumber")).stream().map(tour -> {
//
//        }).collect(Collectors.toList());
		return null;
	}

	public List<DestinationCount> getTopFavoriteDestinations(int numberFavorite, int numberDay) {
		LocalDate now = LocalDate.now();
		LocalDate last30Days = now.minusDays(numberDay);

		List<Booking> bookingsInLast30Days = bookingRepo.findByCreateatBetweenAndStatus(last30Days, now,
				Constants.STATUS_THANH_CONG);

		Map<String, Long> bookingsByDestination = bookingsInLast30Days.stream()
				.collect(Collectors.groupingBy(booking -> booking.getTour().getDestination(), Collectors.counting()));

		List<DestinationCount> destinationCounts = bookingsByDestination.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(numberFavorite)
				.map(entry -> new DestinationCount(entry.getKey(), entry.getValue())).collect(Collectors.toList());

		return destinationCounts;

	}

	@Override
	public List<TourDTOImages> getRandomTours(String type) {
		List<TourDTOImages> tourDTOImagesList = new ArrayList<>();
		List<Tour> tours = tourRepo.findByActiveAndStartdayAfterAndType(true, LocalDate.now(), type);
		Collections.shuffle(tours);
		for (int i = 0; i < 4 && i < tours.size(); i++) {
			Tour tour = tours.get(i);
			TourDTOImages dto = new TourDTOImages();
			if (tour.getStartday().isAfter(LocalDate.now())) {
				if (tour.getPromotion() == null) {
					dto.setPromotionPrice(0);
				} else {
					Promotion promotion = promotionRepo.findById(tour.getPromotion().getId()).get();
					if (checkExpriryDate(promotion)) {
						dto.setPromotionPrice(Double.valueOf(
								formatter.format(tour.getPrice() - (tour.getPrice() * promotion.getDiscount()))));
					} else {
						dto.setPromotionPrice(tour.getPrice());
					}
				}
				dto.setId(tour.getId());
				dto.setName(tour.getName());
				String[] imagesTour = tour.getImage().split(",");
				dto.setImages(getImages(imagesTour));
				dto.setDepartureTime(tour.getDepartureTime());
				dto.setDeparture(tour.getDeparture());
				dto.setDestination(tour.getDestination());
				dto.setStartDay(tour.getStartday());
				dto.setEndDay(tour.getEndday());
				dto.setNumberOfDay(tour.getNumberofday());
				dto.setNumberOfPeople(tour.getNumberofpeople());
				dto.setSubcriber(tour.getSubcriber());
				dto.setPrice(tour.getPrice());
				dto.setCreateAt(tour.getCreateat());
				dto.setLiked(tour.getLiked());
				dto.setType(tour.getType());
				dto.setCreatedBy(tour.getCreatedBy());
				if (!tourDTOImagesList.contains(dto)) {
					tourDTOImagesList.add(dto);
				}
			}
		}

		return tourDTOImagesList;
	}

	@Override
	public List<TourDTOImages> getTop3ToursSoonToExpireWithPromotion() {
		List<TourDTOImages> tourDTOImages = new ArrayList<>();
		LocalDate today = LocalDate.now();
		LocalDate soonToExpire = today.plusDays(10); // Consider tours expiring in 10 days
		List<Tour> tours = tourRepo.findTop3ByActiveIsTrueAndStartdayBetweenOrderByStartdayAsc(today, soonToExpire);
		if (tours != null) {
			for (Tour tour : tours) {
				if (tour.getPromotion() != null) {
					TourDTOImages tourDTOImage = new TourDTOImages();
					tourDTOImage.setId(tour.getId());
					tourDTOImage.setName(tour.getName());
					tourDTOImage.setImages(Arrays.asList(tour.getImage().split(",")));
					tourDTOImage.setDepartureTime(tour.getDepartureTime());
					tourDTOImage.setDeparture(tour.getDeparture());
					tourDTOImage.setDestination(tour.getDestination());
					tourDTOImage.setStartDay(tour.getStartday());
					tourDTOImage.setEndDay(tour.getEndday());
					tourDTOImage.setNumberOfDay(tour.getNumberofday());
					tourDTOImage.setNumberOfPeople(tour.getNumberofpeople());
					tourDTOImage.setSubcriber(tour.getSubcriber());
					tourDTOImage.setType(tour.getType());
					tourDTOImage.setPrice(tour.getPrice());
					tourDTOImage.setCreateAt(tour.getCreateat());
					tourDTOImage.setLiked(tour.getLiked());
					tourDTOImage.setCreatedBy(tour.getCreatedBy());
					tourDTOImage.setPromotionPrice(
							tour.getPrice() - ((tour.getPrice() * tour.getPromotion().getDiscount())));
					tourDTOImages.add(tourDTOImage);
				}
			}
		} else {
			return tourDTOImages;
		}
		return tourDTOImages;
	}

	@Override
	public List<ResponseFavoriteDestination> getTop8FavoriteDestinations() {
//        List<Tour> top8Tours = tourRepo.findTop8ByOrderByLikedDesc();
		List<Tour> top8Tours = tourRepo.findDistinctByActiveTrueOrderByLikedDesc();
		Set<String> destinations = new HashSet<>();
		List<ResponseFavoriteDestination> response = new ArrayList<>();
		for (Tour tour : top8Tours) {
			if (destinations.contains(tour.getDestination())) {
				continue;
			}
			destinations.add(tour.getDestination());
			ResponseFavoriteDestination favoriteDestination = new ResponseFavoriteDestination();
			favoriteDestination.setDestination(tour.getDestination());
			favoriteDestination.setImage(tour.getImage().split(",")[0]);

			response.add(favoriteDestination);

			if (response.size() >= 8) {
				break;
			}
		}
		return response;
	}

    @Scheduled(cron = "0 0 0 * * ?") // Thực hiện mỗi ngày lúc 0h00
	public void deleteTourByPromotionEndDate() {
		LocalDate now = LocalDate.now();
		List<Promotion> expiredPromotions = promotionRepo.findByEndday(now);
		if (Objects.nonNull(expiredPromotions)) {
			for (Promotion promotion : expiredPromotions) {
				for (Tour tour : promotion.getTours()) {
					tour.setPromotion(null);
					tourRepo.save(tour);
					log.info("Success remove promotion end exprire  with tour id - ", +tour.getId());
				}
			}
		}
	}

	@Override
//    @Scheduled(cron = "0 */1 * ? * *")
	public void updateActiveStatus() {
		LocalDate currentDate = LocalDate.now();
		List<Tour> toursToUpdate = tourRepo.findByEndday(currentDate);
		for (Tour tour : toursToUpdate) {
			try {
				tour.setActive(false);
				tourRepo.save(tour);
				log.info("Tour {} has been updated to inactive status", tour.getId());
			} catch (Exception e) {
				log.error("Failed to update active status for tour {}", tour.getId(), e);
			}
		}
	}

	private boolean checkTourExisted(String nameTour) {
		List<Tour> tours = tourRepo.findAll();
		for (Tour tour : tours) {
			if (tour.getName().equals(nameTour)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<TourDTOImages> getAllTourNorthDestination() {
		Pageable pageable = PageRequest.of(1 - 1, 100, Sort.by("id").descending());
		List<TourDTOImages> tourImages = new ArrayList<>();
		List<Tour> tours = tourRepo.findAllByActiveAndType(true, "Trong Nuoc", pageable);
		for (Tour tour : tours) {
			if (Constants.NORTH.contains(tour.getDestination())) {
				tourImages.add(convertTourToTourDTOImage(tour));
			}
		}

		return tourImages;
	}

	@Override
	public List<TourDTOImages> getAllTourSorthDestination() {
		Pageable pageable = PageRequest.of(1 - 1, 100, Sort.by("id").descending());
		List<TourDTOImages> tourImages = new ArrayList<>();
		List<Tour> tours = tourRepo.findAllByActiveAndType(true, Constants.TRONG_NUOC, pageable);
		for (Tour tour : tours) {
			if (Constants.SOUTH.contains(tour.getDestination())) {
				tourImages.add(convertTourToTourDTOImage(tour));
			}
		}
		return tourImages;
	}

	@Override
	public List<TourDTOImages> getAllTourCentralDestination() {
		Pageable pageable = PageRequest.of(1 - 1, 100, Sort.by("id").descending());
		List<TourDTOImages> tourImages = new ArrayList<>();
		List<Tour> tours = tourRepo.findAllByActiveAndType(true, Constants.TRONG_NUOC, pageable);
		for (Tour tour : tours) {
			if (Constants.CENTRAL.contains(tour.getDestination())) {
				tourImages.add(convertTourToTourDTOImage(tour));
			}
		}
		return tourImages;
	}

	private TourDTOImages convertTourToTourDTOImage(Tour tour) {
		TourDTOImages dto = new TourDTOImages();
		if (tour.getPromotion() == null) {
			dto.setPromotionPrice(0);
		} else {
			Promotion promotion = promotionRepo.findById(tour.getPromotion().getId()).get();
			if (checkExpriryDate(promotion)) {
				dto.setPromotionPrice(Double
						.valueOf(formatter.format(tour.getPrice() - (tour.getPrice() * promotion.getDiscount()))));
			} else {
				dto.setPromotionPrice(0);
			}
		}
		dto.setId(tour.getId());
		dto.setName(tour.getName());
		String[] imagesTour = tour.getImage().split(",");
		dto.setImages(getImages(imagesTour));
		dto.setDeparture(tour.getDeparture());
		dto.setDestination(tour.getDestination());
		dto.setStartDay(tour.getStartday());
		dto.setStartDay(tour.getStartday());
		dto.setNumberOfDay(tour.getNumberofday());
		dto.setNumberOfPeople(tour.getNumberofpeople());
		dto.setSubcriber(tour.getSubcriber());
		dto.setPrice(tour.getPrice());
		dto.setCreateAt(tour.getCreateat());
		dto.setLiked(tour.getLiked());
		dto.setType(tour.getType());
		dto.setCreatedBy(tour.getCreatedBy());
		return dto;
	}

}
