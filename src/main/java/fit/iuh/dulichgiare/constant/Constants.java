package fit.iuh.dulichgiare.constant;

import java.util.Arrays;
import java.util.List;

public class Constants {
	private Constants() {
	}

	public final static String REQUEST_TRAVEL_APPROVAL = "Thông báo chuyến đi du lịch bạn yêu cầu được chấp nhận";
	public final static String REQUEST_TRAVEL_APPROVAL_TEMPLATE = "request-create-tour-approval-template.html";
	public final static String REQUEST_TRAVEL_REJECTED = "Thông báo chuyến đi du lịch bạn yêu cầu  không được chấp nhận";
	public final static String REQUEST_TRAVEL_REJECTED_TEMPLATE = "request-create-tour-reject-template.html";
	public final static String BOOKING_TEMPLATE = "order-template.html";
	public final static String NGOAI_NUOC = "Ngoai Nuoc";
	public final static String TRONG_NUOC = "Trong Nuoc";
	public final static String XAC_NHAN = "Xác nhận";
	public final static String TU_CHOI = "Từ chối";
	public final static String STATUS_THANH_CONG = "Thành công";
	public final static String STATUS_CHUA_CHON_HINH_THUC_THANH_TOAN = "Chưa chọn hình thức thanh toán";
	public final static String STATUS_CHO_THANH_TOAN = "Chờ thanh toán";
	public final static String STATUS_CHO_XAC_NHAN = "Chờ xác nhận";
	public final static String THANH_TOAN_TIEN_MAT = "Tiền mặt";
	public final static String THANH_TOAN_CHUYEN_KHOAN = "Chuyển khoản";
	public final static String THANH_TOAN_NGAN_HANG_STRIPE = "Ngân hàng";
	public final static String MAIL_SENDER = "systemhappytrip@gmail.com";
	public final static String NOTIFICATION_BOOK_TOUR_SUCCESS = "Thông báo: Tour đã đặt thành công";

	public final static List<String> NORTH = Arrays.asList("Hà Nội", "Hà Giang", "Cao Bằng", "Bắc Kạn", "Tuyên Quang",
			"Lào Cai", "Điện Biên", "Lai Châu", "Sơn La", "Yên Bái", "Hòa Bình", "Thái Nguyên", "Lạng Sơn", "Bắc Giang",
			"Phú Thọ", "Quảng Ninh", "Bắc Ninh", "Hải Dương", "Hải Phòng", "Hưng Yên", "Nam Định", "Thái Bình");

	public final static List<String> SOUTH = Arrays.asList("TP. Hồ Chí Minh", "Bình Phước", "Bình Dương", "Đồng Nai",
			"Tây Ninh", "Bà Rịa - Vũng Tàu", "Long An", "Đồng Tháp", "Tiền Giang", "An Giang", "Bến Tre", "Vĩnh Long",
			"Trà Vinh", "Hậu Giang", "Kiên Giang", "Sóc Trăng", "Bạc Liêu", "Cà Mau", "Hà Tiên", "Cần Thơ");

	public final static List<String> CENTRAL = Arrays.asList("Thanh Hóa", "Nghệ An", "Hà Tĩnh", "Quảng Bình",
			"Quảng Trị", "Thừa Thiên Huế", "Đà Nẵng", "Quảng Nam", "Quảng Ngãi", "Bình Định", "Phú Yên", "Khánh Hòa",
			"Ninh Thuận", "Bình Thuận");

}
