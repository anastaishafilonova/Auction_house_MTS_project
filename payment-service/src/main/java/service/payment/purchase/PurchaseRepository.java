package service.payment.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.payment.purchase.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
