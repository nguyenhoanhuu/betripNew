package fit.iuh.dulichgiare.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public interface ExcelService {
	public byte[] exportTravelerListToExcel(long id) throws IOException;
}
