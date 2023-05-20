package fit.iuh.dulichgiare.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.service.ExcelService;

@RestController
@RequestMapping("/excel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExcelController {
	@Autowired
	private ExcelService excelService;

	@GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> exportBookings(@RequestParam long id) throws IOException {
		return new ResponseEntity<>(excelService.exportTravelerListToExcel(id), HttpStatus.OK);
	}
}
