package fit.iuh.dulichgiare.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fit.iuh.dulichgiare.entity.Booking;
import fit.iuh.dulichgiare.service.ExcelService;


public class ExcelServiceImpl implements ExcelService {
    
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
}
