package fit.iuh.dulichgiare.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.VoucherCheck;
import fit.iuh.dulichgiare.dto.VoucherDTO;
import fit.iuh.dulichgiare.entity.Voucher;
import fit.iuh.dulichgiare.repository.VoucherRepository;
import fit.iuh.dulichgiare.service.VoucherService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepo;

    @Override
    public List<VoucherDTO> getAllVouchers() throws InterruptedException, ExecutionException {
        return voucherRepo.findVoucherByActiveTrue(Sort.by("id").descending()).stream().map(voucher -> {
            VoucherDTO dto = new VoucherDTO();
            dto.setId(voucher.getId());
            dto.setActive(voucher.isActive());
            dto.setCode(voucher.getCode());
            dto.setDiscount(voucher.getDiscount());
            dto.setLimit(voucher.getLimit());
            dto.setExpriedDate(voucher.getExpriedDate());
            return dto;
        }).toList();

    }

    @Override
    public int saveVoucher(Voucher saveVoucher) throws InterruptedException, ExecutionException {
        Voucher voucher = voucherRepo.findVoucherByCode(saveVoucher.getCode());
        if (saveVoucher.getDiscount() < 0 || saveVoucher.getLimit() < 0) {
            return 0;
        } else if (saveVoucher.getDiscount() == 0 || saveVoucher.getLimit() == 0) {
            return 1;
        } else if (saveVoucher.getExpriedDate().isBefore(LocalDate.now())) {
            return 2;
        } else {
            if (voucher != null && saveVoucher.getCode().equals(voucher.getCode())) {
                if (voucher.isActive() == true) {
                    return 4;
                } else {
                    voucher.setActive(true);
                    voucher.setDiscount(saveVoucher.getDiscount());
                    voucher.setExpriedDate(saveVoucher.getExpriedDate());
                    voucherRepo.save(voucher);
                    return 3;
                }
            } else {
                saveVoucher.setActive(true);
                voucherRepo.save(saveVoucher);
                return 3;
            }
        }
    }

    @Override
    public int updateVoucher(Voucher voucher) throws InterruptedException, ExecutionException {
        if (voucher != null) {
            voucherRepo.save(voucher);
            return 0;
        }

        return 1;
    }

    @Override
    public int deleteVoucher(long id) throws InterruptedException, ExecutionException {
        if (id > 0) {
            Voucher voucher = voucherRepo.findById(id).get();
            voucher.setActive(false);
            voucherRepo.save(voucher);
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public VoucherCheck checkVoucher(String code) {
        VoucherCheck voucherCheck = new VoucherCheck();
        if (code != null) {
            Voucher voucher = voucherRepo.findVoucherByActiveTrueAndCode(code);
            if (voucher != null) {
                if (voucher.getLimit() > 0) {
                    voucherCheck.setResult(0);
                    voucherCheck.setVoucher(voucher);
                    return voucherCheck;
                } else if (voucher.getLimit() <= 0) {
                    voucherCheck.setResult(1);
                    voucherCheck.setVoucher(voucher);
                    return voucherCheck;
                }
            } else {
                voucherCheck.setResult(2);
                return voucherCheck;
            }
        }
        return voucherCheck;

    }

    @Scheduled(cron = "0 0 0 * * ?") // Thực hiện mỗi ngày lúc 0h00
//    @Scheduled(cron = "0 */1 * ? * *")
    @Override
    public void automationCheckVoucherWhenExpiryDate() {
        List<Voucher> vouchers = voucherRepo.findVoucherByActiveTrue(Sort.by("id"));
        for (Voucher voucher : vouchers) {
            if (voucher.getExpriedDate().equals(LocalDate.now())) {
                log.info("Start update active is false of the voucher Id:" + voucher.getId());
                voucher.setActive(false);
                voucherRepo.save(voucher);
                log.info("Updated active is false of the voucher Id:" + voucher.getId());
            }
        }
    }
}
