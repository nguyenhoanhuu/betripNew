package fit.iuh.dulichgiare.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    Voucher findVoucherByActiveTrueAndCode(String voucherCode);

    List<Voucher> findVoucherByActiveTrue(Sort descending);

    Voucher findVoucherByIdAndActiveTrue(long id);

    Voucher findByActiveIsTrueAndLimitGreaterThanAndCode(int limit, String voucherCode);

    Voucher findVoucherByCode(String code);


//	List<Voucher> findByNameContaining(@Param("infix") String name);

}
