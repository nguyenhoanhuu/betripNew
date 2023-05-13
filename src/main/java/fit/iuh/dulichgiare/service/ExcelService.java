package fit.iuh.dulichgiare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.entity.Booking;

@Service
public interface ExcelService {
    public void exportBookingsToExcel(List<Booking> bookings) ;
}
