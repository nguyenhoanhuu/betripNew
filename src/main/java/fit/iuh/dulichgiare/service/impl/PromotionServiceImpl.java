package fit.iuh.dulichgiare.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.PromotionDTO;
import fit.iuh.dulichgiare.entity.Promotion;
import fit.iuh.dulichgiare.repository.PromotionRepository;
import fit.iuh.dulichgiare.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepo;

    @Override
    public List<PromotionDTO> getAllPromotions() throws InterruptedException, ExecutionException {
        return promotionRepo.findPromotionByActiveTrue(Sort.by("id").descending()).stream().map(promotion -> {
            PromotionDTO dto = new PromotionDTO();
            dto.setId(promotion.getId());
            dto.setName(promotion.getName());
            dto.setDiscount(promotion.getDiscount());
            dto.setEndday(promotion.getEndday());
            return dto;
        }).toList();
    }

    @Override
    public int savePromotion(Promotion savePromotion) throws InterruptedException, ExecutionException {
        Promotion promotion = promotionRepo.findPromotionByName(savePromotion.getName());
        if (savePromotion.getDiscount() < 0) {
            return 0;
        } else if (savePromotion.getDiscount() == 0) {
            System.out.println(savePromotion.getEndday());
            System.out.println(savePromotion.getEndday().isAfter(LocalDate.now()));
            return 1;
        } else if (savePromotion.getEndday().isBefore(LocalDate.now())) {
            return 2;
        } else {
            if (promotion != null && savePromotion.getName().equals(promotion.getName())) {
                if (promotion.isActive() == true) {
                    return 4;
                } else {
                    promotion.setActive(true);
                    promotion.setDiscount(savePromotion.getDiscount());
                    promotion.setEndday(savePromotion.getEndday());
                    promotionRepo.save(promotion);
                    return 3;
                }
            } else {
                savePromotion.setActive(true);
                promotionRepo.save(savePromotion);
                return 3;
            }
        }

    }

    @Override
    public int updatePromotion(Promotion promotion) throws InterruptedException, ExecutionException {
        if (promotion != null) {
            promotionRepo.save(promotion);
            return 0;
        }
        return 1;
    }

    @Override
    public int deletePromotion(long id) throws InterruptedException, ExecutionException {
        if (id > 0) {
            Promotion promotion = promotionRepo.findById(id).get();
            promotion.setActive(false);
            promotionRepo.save(promotion);
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public PromotionDTO getPromotionById(long id) throws InterruptedException, ExecutionException {
        Promotion promotion = promotionRepo.findById(id).get();
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId(promotion.getId());
        promotionDTO.setName(promotion.getName());
        promotionDTO.setDiscount(promotion.getDiscount());
        promotionDTO.setEndday(promotion.getEndday());
        return promotionDTO;
    }

    @Override
    public List<PromotionDTO> getAllPromotionByName(String name) throws InterruptedException, ExecutionException {
        return promotionRepo.findByNameContaining(name).stream().map(promotion -> {
            PromotionDTO dto = new PromotionDTO();
            dto.setId(promotion.getId());
            dto.setName(promotion.getName());
            dto.setDiscount(promotion.getDiscount());
            dto.setEndday(promotion.getEndday());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllNamePromotion() {
        List<String> namePromotion = new ArrayList<>();
        List<Promotion> promotions = promotionRepo.findPromotionByActiveTrue();
        for (Promotion promotion : promotions) {
            namePromotion.add(promotion.getName());
        }
        return namePromotion;
    }

}
