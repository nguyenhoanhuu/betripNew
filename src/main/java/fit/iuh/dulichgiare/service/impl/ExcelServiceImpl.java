package fit.iuh.dulichgiare.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.constant.Constants;
import fit.iuh.dulichgiare.entity.Booking;
import fit.iuh.dulichgiare.entity.Tour;
import fit.iuh.dulichgiare.repository.BookingRepository;
import fit.iuh.dulichgiare.repository.TourRepository;
import fit.iuh.dulichgiare.service.ExcelService;

@Service
public class ExcelServiceImpl implements ExcelService {
	@Autowired
	private BookingRepository bookingRepo;
	@Autowired
	private TourRepository tourRepo;

	public void exportBookingsToExcel(List<Booking> bookings) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Bookings");

		// Tạo hàng tiêu đề
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Active");
		headerRow.createCell(2).setCellValue("Start Day Tour");
		headerRow.createCell(3).setCellValue("End Day Tour");
		headerRow.createCell(4).setCellValue("Departure Time");
		// Tiếp tục thêm các cột tiêu đề khác tùy theo yêu cầu của bạn

		// Tạo các hàng dữ liệu
		int rowNum = 1;
		for (Booking booking : bookings) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(booking.getId());
			row.createCell(1).setCellValue(booking.isActive());
			row.createCell(2).setCellValue(booking.getStartDayTour().toString());
			row.createCell(3).setCellValue(booking.getEndDayTour().toString());
			row.createCell(4).setCellValue(booking.getDepartureTime());
			// Tiếp tục thêm các cột dữ liệu khác tùy theo yêu cầu của bạn
		}

		// Tự động điều chỉnh kích thước cột cho phù hợp với nội dung
		for (int i = 0; i < bookings.get(0).getClass().getDeclaredFields().length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Ghi workbook vào file
		try (FileOutputStream outputStream = new FileOutputStream("bookings.xlsx")) {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] exportTravelerListToExcel(long id) throws IOException {
		List<Booking> bookingList = new ArrayList<>();
		Tour tour = tourRepo.findById(id).get();
		List<Booking> bookings = bookingRepo.findBookingByTourIdAndStatus(id, Constants.STATUS_THANH_CONG);
		for (Booking booking : bookings) {
			if (booking.getStartDayTour().equals(tour.getStartday())) {
				bookingList.add(booking);
			}
		}
		Workbook workbook = new XSSFWorkbook();
		CreationHelper creationHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("Danh sách người đi du lịch");

		// Cách ra và căn giữa tiêu đề
		Row headerRow = sheet.createRow(0);
		String[] headers = { "STT", "Họ và tên", "Giới tính", "Ngày sinh", "Departure Time", "Customer", "Price Tour",
				"Price Voucher", "Tour", "Note", "Number of Adults", "Number of Children", "Info of Adults",
				"Info of Children", "Created At", "Total", "Status" };
		int numberOfColumns = headers.length;

		// Merge các ô trong hàng header
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, numberOfColumns - 1));
		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Danh Sách khách hàng tham gia tour");
		headerCell.setCellStyle(getHeaderCellStyle(workbook)); // Tạo cell style cho header
		headerCell.getCellStyle().setAlignment(HorizontalAlignment.CENTER); // Căn giữa trong cell

		// Ghi dòng "Tên hướng dẫn viên du lịch"
		Row guideRow = sheet.createRow(1);
		Cell guideCell = guideRow.createCell(0);
		guideCell.setCellValue("Tên hướng dẫn viên du lịch: Nguyễn Nam");
		guideCell.setCellStyle(getHeaderCellStyle(workbook)); // Tạo cell style cho header
		guideCell.getCellStyle().setAlignment(HorizontalAlignment.CENTER); // Căn giữa trong cell

		// Ghi dòng "Thời gian: 20203"
		Row timeRow = sheet.createRow(2);
		Cell timeCell = timeRow.createCell(0);
		timeCell.setCellValue("Thời gian: 20203");
		timeCell.setCellStyle(getHeaderCellStyle(workbook)); // Tạo cell style cho header
		timeCell.getCellStyle().setAlignment(HorizontalAlignment.CENTER); // Căn giữa trong cell

		// Tạo dòng tiếp theo để bắt đầu ghi dữ liệu booking
		int dataRowIndex = 3;
		// Ghi dữ liệu booking vào các hàng trong sheet
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		for (int i = 0; i < bookingList.size(); i++) {
			Booking booking = bookingList.get(i);
			Row row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(i + 1);
			row.createCell(1).setCellValue(booking.isActive());
			row.createCell(2).setCellValue(booking.getStartDayTour().format(dateFormatter));
			row.createCell(3).setCellValue(booking.getEndDayTour().format(dateFormatter));
			row.createCell(4).setCellValue(booking.getDepartureTime());
			row.createCell(5).setCellValue(booking.getCustomer().toString());
			row.createCell(6).setCellValue(booking.getPriceTour());
			row.createCell(7).setCellValue(booking.getPriceVoucher());
			row.createCell(8).setCellValue(booking.getTour().toString());
			row.createCell(9).setCellValue(booking.getNote());
			row.createCell(10).setCellValue(booking.getNumberofadbult());
			row.createCell(11).setCellValue(booking.getNumberofchildren());
			row.createCell(12).setCellValue(booking.getInfoofadbult().toString());
			row.createCell(13).setCellValue(booking.getInfoofchildren().toString());
			row.createCell(14).setCellValue(booking.getCreateat().format(dateTimeFormatter));
			row.createCell(15).setCellValue(booking.getTotal());
			row.createCell(16).setCellValue(booking.getStatus());
		}
		// Tự động điều chỉnh độ rộng cột
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}
		// Chuyển workbook thành mảng byte
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		workbook.close();
		// Gửi file Excel về frontend
		byte[] excelContent = outputStream.toByteArray();

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		header.setContentDispositionFormData("attachment", "bookings.xlsx");
		return excelContent;
	}

	// Hàm để tạo CellStyle cho header
	private CellStyle getHeaderCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		return cellStyle;
	}

}
