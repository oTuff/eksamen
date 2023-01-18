package businessfacades;

import datafacades.RentalFacade;
import dtos.HouseDTO;
import dtos.RentalDTO;
import dtos.TenatDTO;
import errorhandling.API_Exception;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class RentalDTOFacade {

    private static RentalDTOFacade instance;
    private static RentalFacade rentalFacade;

    private RentalDTOFacade() {}

    public static RentalDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            rentalFacade = RentalFacade.getInstance(_emf);
            instance = new RentalDTOFacade();
        }
        return instance;
    }

    public List<RentalDTO> getAllRentals() throws API_Exception {
        return RentalDTO.getRentalDTOs(rentalFacade.getAllRentals());
    }

    public HouseDTO getAllHouses() {
        return null;
    }

    public TenatDTO getAllTenats(){
        return null;
    }

    public List<RentalDTO> getRentalsByTenant(Integer id) throws API_Exception {
        return RentalDTO.getRentalDTOs(rentalFacade.getRentalsByTenant(id));
    }

    public RentalDTO createRental(RentalDTO rentalDTO) throws API_Exception{
        return new RentalDTO(rentalFacade.createRental(rentalDTO.getEntity()));
    }

    public RentalDTO deleteRental(Integer id) throws API_Exception {
        return new RentalDTO(rentalFacade.deleteRental(id));
    }

}
