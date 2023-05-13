package fit.iuh.dulichgiare.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.PromotionDTO;
import fit.iuh.dulichgiare.entity.Promotion;
import fit.iuh.dulichgiare.respone.MessageResponse;
import fit.iuh.dulichgiare.service.PromotionService;

@RestController
@RequestMapping("/promotions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping(value = { "", "/" })
    public List<PromotionDTO> getAllPromotions() throws InterruptedException, ExecutionException {
        return promotionService.getAllPromotions();
    }

    @GetMapping("/search")
    public List<PromotionDTO> getAllPromotionByName(@RequestParam String name)
            throws InterruptedException, ExecutionException {
        return promotionService.getAllPromotionByName(name);
    }

    @GetMapping("/{id}")
    public PromotionDTO getPromotionById(@PathVariable int id) throws InterruptedException, ExecutionException {
        return promotionService.getPromotionById(id);
    }

    @PostMapping(value = { "", "/save" })
    public ResponseEntity<MessageResponse> savePromotion(@RequestBody Promotion promotion)
            throws InterruptedException, ExecutionException {
        int result = promotionService.savePromotion(promotion);
        MessageResponse messageResponse = new MessageResponse();
        if (result == 0) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Vui lòng nhập giảm giá lớn hơn 0");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else if (result == 1) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Vui lòng nhập giảm giá lớn hơn 0");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else if (result == 2) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Vui lòng nhập ngày kết thúc trước ngày hiện tại ");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else if (result == 4) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Tên khuyến mãi đã tồn tại");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else {
            messageResponse.setStatus(true);
            messageResponse.setMessage("Thêm khuyến mãi thành công");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }

    @PostMapping(value = { "", "/update" })
    public int updatePromotion(@RequestBody Promotion promotion) throws InterruptedException, ExecutionException {
        return promotionService.updatePromotion(promotion);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deletePromotion(@PathVariable int id)
            throws InterruptedException, ExecutionException {
        int result = promotionService.deletePromotion(id);
        MessageResponse messageResponse = new MessageResponse();
        if (result == 0) {
            messageResponse.setStatus(true);
            messageResponse.setMessage("Xoá thành công");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Lỗi khi xoá");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = { "/listName" })
    public List<String> getAllNamePromotion() {
        return promotionService.getAllNamePromotion();
    }
}
