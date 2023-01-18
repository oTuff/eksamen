package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "rental_start_date", nullable = false)
    private LocalDate rentalStartDate;

    @NotNull
    @Column(name = "rental_end_date", nullable = false)
    private LocalDate rentalEndDate;

    @NotNull
    @Column(name = "rental_price_annual", nullable = false)
    private Integer rentalPriceAnnual;

    @NotNull
    @Column(name = "rental_deposit", nullable = false)
    private Integer rentalDeposit;

    @Size(max = 45)
    @NotNull
    @Column(name = "rental_contact_person", nullable = false, length = 45)
    private String rentalContactPerson;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "house_house_id", nullable = false)
    private House houseHouse;

    @ManyToMany
    @JoinTable(name = "rental_has_tenant",
            joinColumns = @JoinColumn(name = "rental_rental_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_tenant_id"))
    private Set<Tenant> tenants = new LinkedHashSet<>();

    public Rental() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDate getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(LocalDate rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public Integer getRentalPriceAnnual() {
        return rentalPriceAnnual;
    }

    public void setRentalPriceAnnual(Integer rentalPriceAnnual) {
        this.rentalPriceAnnual = rentalPriceAnnual;
    }

    public Integer getRentalDeposit() {
        return rentalDeposit;
    }

    public void setRentalDeposit(Integer rentalDeposit) {
        this.rentalDeposit = rentalDeposit;
    }

    public String getRentalContactPerson() {
        return rentalContactPerson;
    }

    public void setRentalContactPerson(String rentalContactPerson) {
        this.rentalContactPerson = rentalContactPerson;
    }

    public House getHouseHouse() {
        return houseHouse;
    }

    public void setHouseHouse(House houseHouse) {
        this.houseHouse = houseHouse;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
        return getId().equals(rental.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}