package Devex_Solutions.Services.Repository;

import Devex_Solutions.Services.Entity.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, UUID> {
    List<ServiceItem> findByActiveTrueOrderByCreatedAtAsc();
    List<ServiceItem> findAllByOrderByCreatedAtAsc();
}
