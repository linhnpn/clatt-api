package container.code.data.repository;

import container.code.data.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a, a.district.name,a.account.fullname From Address a")
    List<Address> findAll();

    @Query(value = "SELECT t.* " +
            "FROM address t, address_User au " +
            "WHERE au.account_id = :accountId AND  t.id = au.address_id " ,nativeQuery = true)
    List<Address> getAddressesByAccount(@Param("accountId") int accountId);

    @Query(value = "SELECT a, a.district.name,a.account.fullname, a.account.id, a.district.province.name" +
            " From Address a where a.accountId = ?1",nativeQuery = false)
    List<Address> findAddressByAccountId(int accountId);
}
