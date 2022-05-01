package pl.electricity_supply_support.domain.model.client.information.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.electricity_supply_support.base.querydsl.BaseQueryDslRepository;
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationFilter;
import pl.electricity_supply_support.domain.model.client.information.dto.QClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.entity.ClientInformation;
import pl.electricity_supply_support.domain.model.client.information.entity.QClientInformation;

import java.util.Optional;

@Repository
public class ClientInformationQueryDslRepository extends BaseQueryDslRepository {

    public ClientInformationQueryDslRepository() {
        super(ClientInformation.class);
    }

    public Page<ClientInformationDto> findClientInformation(ClientInformationFilter filter, Pageable pageable) {
        QClientInformation qClientInformation = QClientInformation.clientInformation;

        JPQLQuery<ClientInformationDto> query = from(qClientInformation)
                .select(new QClientInformationDto(qClientInformation.id,
                        qClientInformation.firstName,
                        qClientInformation.lastName,
                        qClientInformation.serviceCountry,
                        qClientInformation.serviceRegion,
                        qClientInformation.serviceCity,
                        qClientInformation.serviceZipCode,
                        qClientInformation.serviceStreet,
                        qClientInformation.houseNumber,
                        qClientInformation.apartmentNumber,
                        qClientInformation.serviceProvidedKw,
                        qClientInformation.note))
                .where(prepareBooleanBuilder(filter, qClientInformation));

        return applyPagination(pageable, query);
    }

    private BooleanBuilder prepareBooleanBuilder(ClientInformationFilter filter, QClientInformation qClientInformation) {

        BooleanBuilder predicate = new BooleanBuilder();

        Optional.ofNullable(filter.getFirstName())
                .ifPresent(firstName -> predicate.and(qClientInformation.firstName.eq(firstName)));

        Optional.ofNullable(filter.getLastname())
                .ifPresent(lastName -> predicate.and(qClientInformation.lastName.eq(lastName)));

        Optional.ofNullable(filter.getServiceProvidedKwMin())
                .ifPresent(serviceProvidedMin -> predicate.and(qClientInformation.serviceProvidedKw.goe(serviceProvidedMin)));

        Optional.ofNullable(filter.getServiceProvidedKwMax())
                .ifPresent(serviceProvidedMax -> predicate.and(qClientInformation.serviceProvidedKw.loe(serviceProvidedMax)));

        return predicate;
    }
}
