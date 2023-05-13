package fit.iuh.dulichgiare.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @author HOAN HUU
 *
 */
@Entity
@Table(name = "booking")
public class Booking implements java.io.Serializable {

    private long id;
    private boolean active;
    private LocalDate startDayTour;
    private LocalDate endDayTour;
    private String departureTime;
    private Customer customer;
    private double priceTour;
    private double priceVoucher;
    private Tour tour;
    private String note;
    private int numberofadbult;
    private int numberofchildren;
    private List<Object> infoofadbult;
    private List<Object> infoofchildren;
    private LocalDateTime createat;
    private double total;
    private String status;
    private Voucher voucher;
    private Set<Payment> payments;
    private Set<Review> reviews;

    public Booking() {
        super();
    }

    /**
     * @param startDayTour
     * @param endDayTour
     * @param departureTime
     * @param customer
     * @param tour
     * @param note
     * @param numberofadbult
     * @param numberofchildren
     * @param createat
     * @param total
     * @param status
     * @param voucher
     */
    public Booking(LocalDate startDayTour, LocalDate endDayTour, String departureTime, Customer customer, Tour tour,
            String note, int numberofadbult, int numberofchildren, LocalDateTime createat, double total, String status,
            Voucher voucher) {
        super();
        this.startDayTour = startDayTour;
        this.endDayTour = endDayTour;
        this.departureTime = departureTime;
        this.customer = customer;
        this.tour = tour;
        this.note = note;
        this.numberofadbult = numberofadbult;
        this.numberofchildren = numberofchildren;
        this.createat = createat;
        this.total = total;
        this.status = status;
        this.voucher = voucher;
    }

    /**
     * @param active
     * @param startDayTour
     * @param endDayTour
     * @param departureTime
     * @param customer
     * @param priceTour
     * @param tour
     * @param note
     * @param numberofadbult
     * @param numberofchildren
     * @param infoofadbult
     * @param infoofchildren
     * @param createat
     * @param total
     * @param status
     */
    public Booking(boolean active, LocalDate startDayTour, LocalDate endDayTour, String departureTime,
            Customer customer, double priceTour, Tour tour, String note, int numberofadbult, int numberofchildren,
            List<Object> infoofadbult, List<Object> infoofchildren, LocalDateTime createat, double total,
            String status) {
        super();
        this.active = active;
        this.startDayTour = startDayTour;
        this.endDayTour = endDayTour;
        this.departureTime = departureTime;
        this.customer = customer;
        this.priceTour = priceTour;
        this.tour = tour;
        this.note = note;
        this.numberofadbult = numberofadbult;
        this.numberofchildren = numberofchildren;
        this.infoofadbult = infoofadbult;
        this.infoofchildren = infoofchildren;
        this.createat = createat;
        this.total = total;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne()
    @JoinColumn(name = "voucher_id", nullable = true)
    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", nullable = false)
    public Tour getTour() {
        return this.tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @Column(name = "note")
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "numberofadbult", nullable = false)
    public int getNumberofadbult() {
        return this.numberofadbult;
    }

    public void setNumberofadbult(int numberofadbult) {
        this.numberofadbult = numberofadbult;
    }

    @Column(name = "numberofchildren", nullable = false)
    public int getNumberofchildren() {
        return this.numberofchildren;
    }

    public void setNumberofchildren(int numberofchildren) {
        this.numberofchildren = numberofchildren;
    }

//    @Temporal(TemporalType.DAT)
    @Column(name = "createat", length = 13)
    public LocalDateTime getCreateat() {
        return this.createat;
    }

    public void setCreateat(LocalDateTime createat) {
        this.createat = createat;
    }

    @Column(name = "total", nullable = false, precision = 17, scale = 17)
    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Column(name = "status", nullable = false)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "booking", cascade = CascadeType.ALL)
    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "booking", cascade = CascadeType.ALL)
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Column(name = "startdaytour", nullable = false)
    public LocalDate getStartDayTour() {
        return startDayTour;
    }

    public void setStartDayTour(LocalDate startDayTour) {
        this.startDayTour = startDayTour;
    }

    @Column(name = "enddaytour", nullable = false)
    public LocalDate getEndDayTour() {
        return endDayTour;
    }

    public void setEndDayTour(LocalDate endDayTour) {
        this.endDayTour = endDayTour;
    }

    @Column(name = "departuretime", nullable = false)
    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    @Column(name = "pricetour", nullable = false)
    public double getPriceTour() {
        return priceTour;
    }

    public void setPriceTour(double priceTour) {
        this.priceTour = priceTour;
    }

    @Column(name = "pricevoucher", nullable = true)
    public double getPriceVoucher() {
        return priceVoucher;
    }

    public void setPriceVoucher(double priceVoucher) {
        this.priceVoucher = priceVoucher;
    }

    @Column(name = "infoofadbult", nullable = true)
    public List<Object> getInfoofadbult() {
        return infoofadbult;
    }

    public void setInfoofadbult(List<Object> infoofadbult) {
        this.infoofadbult = infoofadbult;
    }

    @Column(name = "infoofchildren", nullable = true)
    public List<Object> getInfoofchildren() {
        return infoofchildren;
    }

    public void setInfoofchildren(List<Object> infoofchildren) {
        this.infoofchildren = infoofchildren;
    }

    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
