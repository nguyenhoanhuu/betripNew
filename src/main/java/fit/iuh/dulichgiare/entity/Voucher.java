package fit.iuh.dulichgiare.entity;
// Generated Mar 13, 2023, 11:14:32 AM by Hibernate Tools 4.3.6.Final

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author HOAN HUU
 */
@Entity
@Table(name = "voucher")
public class Voucher implements java.io.Serializable {
    private long id;
    private boolean active;
    private String code;
    private double discount;
    private double limit;
    private LocalDate expriedDate;
    private Set<Booking> orders;

    public Voucher() {
    }

    /**
     * @param id
     * @param code
     * @param discount
     * @param limit
     * @param expriedDate
     */
    public Voucher(long id, String code, double discount, double limit, LocalDate expriedDate) {
        super();
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.limit = limit;
        this.expriedDate = expriedDate;
    }

    /**
     * @param code
     * @param discount
     * @param limit
     * @param expriedDate
     */
    public Voucher(String code, double discount, double limit, LocalDate expriedDate) {
        super();
        this.code = code;
        this.discount = discount;
        this.limit = limit;
        this.expriedDate = expriedDate;
    }

    /**
     * @param active
     * @param code
     * @param discount
     * @param limit
     * @param expriedDate
     */
    public Voucher(boolean active, String code, double discount, double limit, LocalDate expriedDate) {
        super();
        this.active = active;
        this.code = code;
        this.discount = discount;
        this.limit = limit;
        this.expriedDate = expriedDate;
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

    @Column(name = "code", nullable = false)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "discount", nullable = false)
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Column(name = "exprieddate", nullable = false)
    public LocalDate getExpriedDate() {
        return expriedDate;
    }

    @Column(name = "\"limit\"", nullable = true)
    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public void setExpriedDate(LocalDate expriedDate) {
        this.expriedDate = expriedDate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voucher")
    public Set<Booking> getOrders() {
        return orders;
    }

    public void setOrders(Set<Booking> orders) {
        this.orders = orders;
    }

    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
