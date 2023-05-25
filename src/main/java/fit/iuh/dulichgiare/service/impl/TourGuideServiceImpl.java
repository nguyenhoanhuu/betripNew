package fit.iuh.dulichgiare.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.TourGuideDTO;
import fit.iuh.dulichgiare.entity.TourGuide;
import fit.iuh.dulichgiare.repository.TourGuideRepository;
import fit.iuh.dulichgiare.service.TourGuideService;

@Service
public class TourGuideServiceImpl implements TourGuideService {

	@Autowired
	private TourGuideRepository tourGuideRepo;

	@Override
	public List<TourGuideDTO> getAllTourGuides() throws InterruptedException, ExecutionException {

		return tourGuideRepo.findAll(Sort.by("id").descending()).stream().map(tourGuide -> {
			TourGuideDTO dto = new TourGuideDTO();
			dto.setId(tourGuide.getId());
			dto.setActive(tourGuide.isActive());
			dto.setName(tourGuide.getName());
			dto.setAddress(tourGuide.getAddress());
			dto.setPhone(tourGuide.getPhone());
			dto.setEmail(tourGuide.getEmail());
			return dto;
		}).toList();
	}

	@Override
	public int saveTourGuide(TourGuideDTO tourGuideDTO) throws InterruptedException, ExecutionException {
		TourGuide tourGuide = tourGuideRepo.findTourGuideByPhone(tourGuideDTO.getPhone());
		if (tourGuide == null) {
			TourGuide tourGuideSave = new TourGuide();
			tourGuideSave.setActive(true);
			tourGuideSave.setAddress(tourGuideDTO.getAddress());
			tourGuideSave.setEmail(tourGuideDTO.getEmail());
			tourGuideSave.setPhone(tourGuideDTO.getPhone());
			tourGuideSave.setName(tourGuideDTO.getName());
			tourGuideRepo.save(tourGuideSave);
			return 0;
		} else {
			if (tourGuide.isActive() && tourGuide.getPhone().equals(tourGuideDTO.getPhone())) {
				return 1;
			} else if (!tourGuide.isActive() && tourGuide.getPhone().equals(tourGuideDTO.getPhone())) {
				tourGuide.setActive(true);
				tourGuide.setAddress(tourGuideDTO.getAddress());
				tourGuide.setEmail(tourGuideDTO.getEmail());
				tourGuide.setPhone(tourGuideDTO.getPhone());
				tourGuide.setName(tourGuideDTO.getName());
				tourGuideRepo.save(tourGuide);
				return 2;
			}
		}
		return 3;

	}

	@Override
	public int updateTourGuide(TourGuide tourGuide) throws InterruptedException, ExecutionException {

		if (tourGuide != null) {
			tourGuideRepo.save(tourGuide);
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteTourGuide(long id) throws InterruptedException, ExecutionException {
		if (id > 0) {
			TourGuide tourGuide = tourGuideRepo.findById(id).get();
			tourGuide.setActive(false);
			tourGuideRepo.save(tourGuide);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public TourGuideDTO getTourGuideById(long id) throws InterruptedException, ExecutionException {
		TourGuide tourGuide = tourGuideRepo.findById(id).get();
		TourGuideDTO tourGuideDTO = new TourGuideDTO();
		tourGuideDTO.setId(tourGuide.getId());
		tourGuideDTO.setName(tourGuide.getName());
		tourGuideDTO.setAddress(tourGuide.getAddress());
		tourGuideDTO.setPhone(tourGuide.getPhone());
		tourGuideDTO.setEmail(tourGuide.getEmail());
		return tourGuideDTO;
	}

	@Override
	public List<String> getAllNameTourGuide() {
		List<String> tourGuideName = new ArrayList<>();
		List<TourGuide> tourGuides = tourGuideRepo.findAll();
		for (TourGuide tourGuide : tourGuides) {
			tourGuideName.add(tourGuide.getName());
		}
		return tourGuideName;
	}

}
