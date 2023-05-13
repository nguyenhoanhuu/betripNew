package fit.iuh.dulichgiare.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
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
@Table(name = "account")
public class Account implements java.io.Serializable {

    private long id;
    private String username;
    private String password;
    private String accountType;
    private Set<Employee> employees;
    private Set<Customer> customers;

    public Account() {
    }

    /**
     * @param username
     * @param password
     * @param accountType
     */
    public Account(String username, String password, String accountType) {
        super();
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    /**
     * @param username
     * @param password
     * @param accountType
     * @param customers
     */
    public Account(String username, String password, String accountType, Set<Customer> customers) {
        super();
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.customers = customers;
    }

    public Account(long id, String username, String password, String accountType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    public Account(long id, String username, String password, String accountType, Set<Employee> employees,
            Set<Customer> customers) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.employees = employees;
        this.customers = customers;
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

    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "account_type", nullable = false, length = 10)
    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

}
