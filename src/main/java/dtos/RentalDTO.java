package dtos;

import entities.House;
import entities.Rental;
import entities.Tenant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * A DTO for the {@link entities.Rental} entity
 */
public class RentalDTO implements Serializable {
    private Integer id;
    @NotNull
    private LocalDate rentalStartDate;
    @NotNull
    private LocalDate rentalEndDate;
    @NotNull
    private Integer rentalPriceAnnual;
    @NotNull
    private Integer rentalDeposit;
    @Size(max = 45)
    @NotNull
    private String rentalContactPerson;
    @NotNull
    private HouseDto houseHouse;
    private Set<TenantDto> tenants = new HashSet<>();

    public RentalDTO(Integer id, LocalDate rentalStartDate, LocalDate rentalEndDate, Integer rentalPriceAnnual, Integer rentalDeposit, String rentalContactPerson, HouseDto houseHouse, Set<TenantDto> tenants) {
        this.id = id;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.rentalPriceAnnual = rentalPriceAnnual;
        this.rentalDeposit = rentalDeposit;
        this.rentalContactPerson = rentalContactPerson;
        this.houseHouse = houseHouse;
        this.tenants = tenants;
    }

    public RentalDTO(Rental rental) {
        if (rental.getId() != null) {
            this.id = rental.getId();
        }
        this.rentalStartDate = rental.getRentalStartDate();
        this.rentalEndDate = rental.getRentalEndDate();
        this.rentalPriceAnnual = rental.getRentalPriceAnnual();
        this.rentalDeposit = rental.getRentalDeposit();
        this.rentalContactPerson = rental.getRentalContactPerson();
        this.houseHouse = new HouseDto(rental.getHouseHouse());
        for (Tenant t : rental.getTenants()) {
            this.tenants.add(new TenantDto(t));
        }
    }

    public static List<RentalDTO> getRentalDTOs(List<Rental> rentals) {
        List<RentalDTO> rentalDTOS = new ArrayList<>();
        rentals.forEach(rental -> rentalDTOS.add(new RentalDTO(rental)));
        return rentalDTOS;
    }

    public Rental getEntity() {
        Rental rental = new Rental();
        if (this.id != null) {
            rental.setId(this.id);
        }
        rental.setId(this.id);
        rental.setRentalStartDate(this.rentalStartDate);
        rental.setRentalEndDate(this.rentalEndDate);
        rental.setRentalPriceAnnual(this.rentalPriceAnnual);
        rental.setRentalDeposit(this.rentalDeposit);
        rental.setRentalContactPerson(this.rentalContactPerson);
        rental.setHouseHouse(this.houseHouse.getEntity());
        for (TenantDto tdto : this.tenants) {
            rental.addTenant(tdto.getEntity());
        }
        return rental;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public LocalDate getRentalEndDate() {
        return rentalEndDate;
    }

    public Integer getRentalPriceAnnual() {
        return rentalPriceAnnual;
    }

    public Integer getRentalDeposit() {
        return rentalDeposit;
    }

    public String getRentalContactPerson() {
        return rentalContactPerson;
    }

    public HouseDto getHouseHouse() {
        return houseHouse;
    }

    public Set<TenantDto> getTenants() {
        return tenants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDTO entity = (RentalDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.rentalStartDate, entity.rentalStartDate) &&
                Objects.equals(this.rentalEndDate, entity.rentalEndDate) &&
                Objects.equals(this.rentalPriceAnnual, entity.rentalPriceAnnual) &&
                Objects.equals(this.rentalDeposit, entity.rentalDeposit) &&
                Objects.equals(this.rentalContactPerson, entity.rentalContactPerson) &&
                Objects.equals(this.houseHouse, entity.houseHouse) &&
                Objects.equals(this.tenants, entity.tenants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rentalStartDate, rentalEndDate, rentalPriceAnnual, rentalDeposit, rentalContactPerson, houseHouse, tenants);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "rentalStartDate = " + rentalStartDate + ", " +
                "rentalEndDate = " + rentalEndDate + ", " +
                "rentalPriceAnnual = " + rentalPriceAnnual + ", " +
                "rentalDeposit = " + rentalDeposit + ", " +
                "rentalContactPerson = " + rentalContactPerson + ", " +
                "houseHouse = " + houseHouse + ", " +
                "tenants = " + tenants + ")";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * A DTO for the {@link entities.House} entity
     */
    public static class HouseDto implements Serializable {
        private Integer id;
        @NotNull
        private Integer houseNumberOfRooms;
        @NotNull
        private AddressDTO address;

        public HouseDto(Integer id, Integer houseNumberOfRooms, AddressDTO address) {
            this.id = id;
            this.houseNumberOfRooms = houseNumberOfRooms;
            this.address = address;
        }

        public HouseDto(House house) {
            if (house.getId() > 0) {
                this.id = house.getId();
                this.houseNumberOfRooms = house.getHouseNumberOfRooms();
                this.address = new AddressDTO(house.getAddress());
            }
        }

        public Integer getId() {
            return id;
        }

        public Integer getHouseNumberOfRooms() {
            return houseNumberOfRooms;
        }

        public AddressDTO getAddress() {
            return address;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HouseDto entity = (HouseDto) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.houseNumberOfRooms, entity.houseNumberOfRooms) &&
                    Objects.equals(this.address, entity.address);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, houseNumberOfRooms, address);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "houseNumberOfRooms = " + houseNumberOfRooms + ", " +
                    "address = " + address + ")";
        }

        public House getEntity() {
            House house = new House();
            if (this.id != null) {
                house.setId(this.id);
            }
            house.setHouseNumberOfRooms(this.houseNumberOfRooms);
            house.setAddress(this.address.getEntity());
            return house;
        }
    }

    /**
     * A DTO for the {@link entities.Tenant} entity
     */
    public static class TenantDto implements Serializable {
        private Integer id;
        @Size(max = 45)
        @NotNull
        private String tenantName;
        @NotNull
        private Integer tenantPhone;
        @Size(max = 45)
        @NotNull
        private String tenantJob;

        public TenantDto(Integer id, String tenantName, Integer tenantPhone, String tenantJob) {
            this.id = id;
            this.tenantName = tenantName;
            this.tenantPhone = tenantPhone;
            this.tenantJob = tenantJob;
        }

        public TenantDto(Tenant t) {
            if (t.getId() > 0) {
                this.id = t.getId();
            }
                this.tenantName = t.getTenantName();
                this.tenantPhone = t.getTenantPhone();
                this.tenantJob = t.getTenantJob();
            }

        public Integer getId() {
            return id;
        }

        public String getTenantName() {
            return tenantName;
        }

        public Integer getTenantPhone() {
            return tenantPhone;
        }

        public String getTenantJob() {
            return tenantJob;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TenantDto entity = (TenantDto) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.tenantName, entity.tenantName) &&
                    Objects.equals(this.tenantPhone, entity.tenantPhone) &&
                    Objects.equals(this.tenantJob, entity.tenantJob);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, tenantName, tenantPhone, tenantJob);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "tenantName = " + tenantName + ", " +
                    "tenantPhone = " + tenantPhone + ", " +
                    "tenantJob = " + tenantJob + ")";
        }

        public Tenant getEntity() {
            Tenant tenant = new Tenant();
            if (this.id != null) {
                tenant.setId(this.id);
            }
            tenant.setTenantName(this.tenantName);
            tenant.setTenantPhone(this.tenantPhone);
            tenant.setTenantJob(this.tenantJob);
            return tenant;
        }
    }
}