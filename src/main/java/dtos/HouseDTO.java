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
 * A DTO for the {@link entities.House} entity
 */
public class HouseDTO implements Serializable {
    private Integer id;
    @NotNull
    private Integer houseNumberOfRooms;
    @NotNull
    private AddressDTO address;
    private Set<RentalDto> rentals = new LinkedHashSet<>();

    public HouseDTO() {
    }

    public HouseDTO(Integer id, Integer houseNumberOfRooms, AddressDTO address, Set<RentalDto> rentals) {
        this.id = id;
        this.houseNumberOfRooms = houseNumberOfRooms;
        this.address = address;
        this.rentals = rentals;
    }

    public HouseDTO(House house) {
        if (house.getId() !=0) {
            this.id = house.getId();
        }
            this.houseNumberOfRooms = house.getHouseNumberOfRooms();
            this.address = new AddressDTO(house.getAddress());
            for(Rental r : house.getRentals()){
                this.rentals.add(new RentalDto(r));
        }
    }

    public House getEntity() {
        House house = new House();
        if (this.id != null) {
            house.setId(this.id);
        }
        house.setHouseNumberOfRooms(this.houseNumberOfRooms);
        house.setAddress(this.address.getEntity());
        for(RentalDto rdto:this.rentals){
        }
        return house;
    }

    public static List<HouseDTO> getHouseDTOs(List<House> houses){
        List<HouseDTO> houseDTOS = new ArrayList<>();
        houses.forEach(house -> houseDTOS.add(new HouseDTO(house)));
        return houseDTOS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHouseNumberOfRooms() {
        return houseNumberOfRooms;
    }

    public void setHouseNumberOfRooms(Integer houseNumberOfRooms) {
        this.houseNumberOfRooms = houseNumberOfRooms;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Set<RentalDto> getRentals() {
        return rentals;
    }

    public void setRentals(Set<RentalDto> rentals) {
        this.rentals = rentals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseDTO entity = (HouseDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.houseNumberOfRooms, entity.houseNumberOfRooms) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.rentals, entity.rentals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, houseNumberOfRooms, address, rentals);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "houseNumberOfRooms = " + houseNumberOfRooms + ", " +
                "address = " + address + ", " +
                "rentals = " + rentals + ")";
    }

    /**
     * A DTO for the {@link entities.Rental} entity
     */
    public static class RentalDto implements Serializable {
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
        private Set<TenantDto> tenants = new LinkedHashSet<>();

        public RentalDto() {
        }

        public RentalDto(Integer id, LocalDate rentalStartDate, LocalDate rentalEndDate, Integer rentalPriceAnnual, Integer rentalDeposit, String rentalContactPerson, Set<TenantDto> tenants) {
            this.id = id;
            this.rentalStartDate = rentalStartDate;
            this.rentalEndDate = rentalEndDate;
            this.rentalPriceAnnual = rentalPriceAnnual;
            this.rentalDeposit = rentalDeposit;
            this.rentalContactPerson = rentalContactPerson;
            this.tenants = tenants;
        }

        public RentalDto(Rental rental) {
            if (rental.getId() != null) {
                this.id = rental.getId();
            }
            this.rentalStartDate = rental.getRentalStartDate();
            this.rentalEndDate = rental.getRentalEndDate();
            this.rentalPriceAnnual = rental.getRentalPriceAnnual();
            this.rentalDeposit = rental.getRentalDeposit();
            this.rentalContactPerson = rental.getRentalContactPerson();
            for (Tenant t : rental.getTenants()) {
                this.tenants.add(new TenantDto(t));
            }
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
            for (TenantDto tdto : this.tenants) {
                rental.addTenant(tdto.getEntity());
            }
            return rental;
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

        public Set<TenantDto> getTenants() {
            return tenants;
        }

        public void setTenants(Set<TenantDto> tenants) {
            this.tenants = tenants;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RentalDto entity = (RentalDto) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.rentalStartDate, entity.rentalStartDate) &&
                    Objects.equals(this.rentalEndDate, entity.rentalEndDate) &&
                    Objects.equals(this.rentalPriceAnnual, entity.rentalPriceAnnual) &&
                    Objects.equals(this.rentalDeposit, entity.rentalDeposit) &&
                    Objects.equals(this.rentalContactPerson, entity.rentalContactPerson) &&
                    Objects.equals(this.tenants, entity.tenants);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, rentalStartDate, rentalEndDate, rentalPriceAnnual, rentalDeposit, rentalContactPerson, tenants);
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
                    "tenants = " + tenants + ")";
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

            public TenantDto() {
            }

            public TenantDto(Tenant t) {
                if (t.getId() !=0) {
                    this.id = t.getId();
                }
                    this.tenantName = t.getTenantName();
                    this.tenantPhone = t.getTenantPhone();
                    this.tenantJob = t.getTenantJob();
                }

            public TenantDto(Integer id, String tenantName, Integer tenantPhone, String tenantJob) {
                this.id = id;
                this.tenantName = tenantName;
                this.tenantPhone = tenantPhone;
                this.tenantJob = tenantJob;
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
        }
    }
}