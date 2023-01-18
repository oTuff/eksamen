package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "Tenant.deleteAllRows", query = "DELETE from Tenant")
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "tenant_name", nullable = false, length = 45)
    private String tenantName;

    @NotNull
    @Column(name = "tenant_phone", nullable = false)
    private Integer tenantPhone;

    @Size(max = 45)
    @NotNull
    @Column(name = "tenant_job", nullable = false, length = 45)
    private String tenantJob;

    @ManyToMany
    @JoinTable(name = "rental_has_tenant",
            joinColumns = @JoinColumn(name = "tenant_tenant_id"),
            inverseJoinColumns = @JoinColumn(name = "rental_rental_id"))
    private Set<Rental> rentals = new LinkedHashSet<>();

    public Tenant() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Integer getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(Integer tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public String getTenantJob() {
        return tenantJob;
    }

    public void setTenantJob(String tenantJob) {
        this.tenantJob = tenantJob;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addRental(Rental rental){
        this.rentals.add(rental);
//        rental.getTenants().add(this);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", tenantName='" + tenantName + '\'' +
                ", tenantPhone=" + tenantPhone +
                ", tenantJob='" + tenantJob + '\'' +
                ", rentals=" + rentals +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tenant)) return false;
        Tenant tenant = (Tenant) o;
        return getId().equals(tenant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}