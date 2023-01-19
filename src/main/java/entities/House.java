package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "House.deleteAllRows", query = "DELETE from House")
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "house_number_of_rooms", nullable = false)
    private Integer houseNumberOfRooms;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "houseHouse")
    private Set<Rental> rentals = new LinkedHashSet<>();

    public House() {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }
    public void addRental(Rental rental){
        this.rentals.add(rental);
//        rental.setHouseHouse(this);
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", houseNumberOfRooms=" + houseNumberOfRooms +
                ", address=" + address +
                ", rentals=" + rentals +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House)) return false;
        House house = (House) o;
        return getId().equals(house.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}