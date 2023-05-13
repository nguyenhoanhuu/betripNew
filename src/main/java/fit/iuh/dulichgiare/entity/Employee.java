package fit.iuh.dulichgiare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author HOAN HUU
 */
@Entity
@Table(name = "employee")
public class Employee implements java.io.Serializable {

    private long id;
    private Account account;
    private String name;
    private String address;
    private String phone;
    private String email;

    public Employee() {
    }

    public Employee(long id, Account account, String name, String address, String phone, String email) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    /**
     * @param account
     * @param name
     * @param address
     * @param phone
     * @param email
     */
    public Employee(Account account, String name, String address, String phone, String email) {
        super();
        this.account = account;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone", nullable = false, length = 10)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
