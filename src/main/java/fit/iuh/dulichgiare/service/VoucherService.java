package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.VoucherCheck;
import fit.iuh.dulichgiare.dto.VoucherDTO;
import fit.iuh.dulichgiare.entity.Voucher;

/**
 * 
 * @author HOAN HUU
 *
 */
@Service
public interface VoucherService {

    public List<VoucherDTO> getAllVouchers() throws InterruptedException, ExecutionException;

    public int saveVoucher(Voucher voucher) throws InterruptedException, ExecutionException;

    public int updateVoucher(Voucher voucher) throws InterruptedException, ExecutionException;

    public int deleteVoucher(long id) throws InterruptedException, ExecutionException;

    public VoucherCheck checkVoucher(String code);
    
    public void automationCheckVoucherWhenExpiryDate();

}
