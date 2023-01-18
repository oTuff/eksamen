package businessfacades;

import datafacades.RentalFacade;
import dtos.RentalDTO;
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

//    public List<RentalDTO> getAllBoats() throws API_Exception {
//    }

    public List<RentalDTO> getRentalsByTenant(Integer id) throws API_Exception {
        return RentalDTO.getRentalDTOs(rentalFacade.getRentalsByTenant(id));
    }

}
