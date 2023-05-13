package fit.iuh.dulichgiare.entity;

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

/**
 * @author HOAN HUU
 */
@Entity
@Table(name = "customer")
public class Customer implements java.io.Serializable {
    private long id;
    private Account account;
    private String name;
    private String phone;
    private String email;
    private Set<Booking> orders;
    private Set<BlogPost> blogPosts;
    private Set<UserRequestTravel> userRequestTravels;

    public Customer() {
    }

    /**
     * @param account
     * @param name
     * @param address
     * @param phone
     * @param email
     */
    public Customer(Account account, String name, String phone, String email) {
        super();
        this.account = account;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Customer(long id, Account account, String name, String phone, String email) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Customer(long id, Account account, String name, String phone, String email, Set<Booking> orders,
            Set<BlogPost> blogPosts) {
        super();
        this.id = id;
        this.account = account;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.orders = orders;
        this.blogPosts = blogPosts;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    public Set<Booking> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Booking> orders) {
        this.orders = orders;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    public Set<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(Set<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    public Set<UserRequestTravel> getUserRequestTravels() {
        return userRequestTravels;
    }

    public void setUserRequestTravels(Set<UserRequestTravel> userRequestTravels) {
        this.userRequestTravels = userRequestTravels;
    }

}
