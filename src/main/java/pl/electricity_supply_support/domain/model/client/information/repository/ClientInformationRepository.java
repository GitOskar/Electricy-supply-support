package pl.electricity_supply_support.domain.model.client.information.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.electricity_supply_support.domain.model.client.information.entity.ClientInformation;

import java.math.BigDecimal;

@Repository
public interface ClientInformationRepository extends JpaRepository<ClientInformation, Long> {

    @Query("select sum(serviceProvidedKw) from ClientInformation where firstName = :firstName and lastName = :lastName")
    BigDecimal findSumOfClientServices(String firstName, String lastName);

    @Query("select sum(serviceProvidedKw) from ClientInformation where firstName = :firstName and lastName = :lastName and id <> :idExcluded")
    BigDecimal findSumOfClientServicesWithout(String firstName, String lastName, Long idExcluded);
}
