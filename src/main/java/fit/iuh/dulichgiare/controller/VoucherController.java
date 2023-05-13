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

import fit.iuh.dulichgiare.dto.VoucherCheck;
import fit.iuh.dulichgiare.dto.VoucherDTO;
import fit.iuh.dulichgiare.entity.Voucher;
import fit.iuh.dulichgiare.respone.MessageResponse;
import fit.iuh.dulichgiare.service.VoucherService;

@RestController
@RequestMapping("/vouchers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping(value = { "", "/" })
    public List<VoucherDTO> getAllVouchers() throws InterruptedException, ExecutionException {
        return voucherService.getAllVouchers();
    }

    @PostMapping(value = { "", "/save" })
    public ResponseEntity<MessageResponse> saveVoucher(@RequestBody Voucher voucher)
            throws InterruptedException, ExecutionException {
        int result = voucherService.saveVoucher(voucher);
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
            messageResponse.setMessage("Mã voucher đã tồn tại");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else {
            messageResponse.setStatus(true);
            messageResponse.setMessage("Thêm voucher thành công");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }

    @PostMapping(value = { "", "/update" })
    public int updateVoucher(@RequestBody Voucher voucher) throws InterruptedException, ExecutionException {
        return voucherService.updateVoucher(voucher);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteVoucher(@PathVariable int id)
            throws InterruptedException, ExecutionException {
        int result = voucherService.deleteVoucher(id);
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

    @GetMapping(value = { "/checVoucher" })
    public ResponseEntity<MessageResponse> checkVoucher(@RequestParam String code) {
        VoucherCheck voucherCheck = voucherService.checkVoucher(code);
        MessageResponse message = new MessageResponse();
        if (voucherCheck.getResult() == 0) {
            message.setStatus(true);
            message.setMessage("Khuyến mãi còn lượt sử dụng");
            message.setPrice(voucherCheck.getVoucher().getDiscount());
            return new ResponseEntity<MessageResponse>(message, HttpStatus.OK);
        } else if (voucherCheck.getResult() == 1) {
            message.setStatus(false);
            message.setMessage("Khuyến mãi hết lượt sử dụng");
            return new ResponseEntity<MessageResponse>(message, HttpStatus.OK);
        } else {
            message.setStatus(false);
            message.setMessage("Nhập sai mã khuyến mãi ");
            return new ResponseEntity<MessageResponse>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
