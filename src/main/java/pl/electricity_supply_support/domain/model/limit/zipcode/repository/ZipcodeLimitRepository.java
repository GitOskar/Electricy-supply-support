package pl.electricity_supply_support.domain.model.limit.zipcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.electricity_supply_support.domain.model.limit.zipcode.entity.ZipcodeLimit;
import pl.electricity_supply_support.domain.model.limit.zipcode.enumerate.ZipcodeLimitType;

import java.math.BigDecimal;

@Repository
public interface ZipcodeLimitRepository extends JpaRepository<ZipcodeLimit, Long> {

    @Query("select limitValueKw from ZipcodeLimit where pattern = :zipcode and limitType = :limitType")
    BigDecimal findZipcodeLimitByPatternAndType(String zipcode, ZipcodeLimitType limitType);
}
