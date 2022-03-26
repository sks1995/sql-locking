package com.practice.locking.sqllocking.service;

import com.practice.locking.sqllocking.persistence.entity.Audit;
import com.practice.locking.sqllocking.persistence.entity.Stock;
import com.practice.locking.sqllocking.persistence.exceptions.OptimisticLockingException;
import com.practice.locking.sqllocking.persistence.repository.AuditRepository;
import com.practice.locking.sqllocking.persistence.repository.StockRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class PurchaseOrder {

  private StockRepository stockRepository;
  private AuditRepository auditRepository;

  public void purchase(String productId) {
    Optional<Stock> practiceOptional = stockRepository.findById(productId);
    if (practiceOptional.isPresent() && practiceOptional.get().getCount() > 0){
      Stock practice = practiceOptional.get();
      Audit audit = Audit.builder().count(practice.getCount())
          .id(UUID.randomUUID().toString()).build();
      practice.setCount(practice.getCount() - 1);
      int update = stockRepository.updateCount(practice.getCount(), productId);
      if(update == 0) throw new OptimisticLockingException("Update failed");
      auditRepository.save(audit);
    }
  }

  public void purchaseV1(String productId){
    Optional<Stock> practiceOptional = stockRepository.findById(productId);
    if (practiceOptional.isPresent() && practiceOptional.get().getCount() > 0){
      Stock practice = practiceOptional.get();
      Audit audit = Audit.builder().count(practice.getCount()).id(UUID.randomUUID().toString()).build();
      practice.setCount(practice.getCount() - 1);
      int update = stockRepository.updateCountWithVersion(practice.getCount(), practice.getVersion(), productId);
      if(update == 0) throw new OptimisticLockingException("Update failed");
      auditRepository.save(audit);
    }
  }

  @Transactional
  public void purchaseV2(String productId){
    Optional<Stock> practiceOptional = stockRepository.findById1(productId);
    if (practiceOptional.isPresent() && practiceOptional.get().getCount() > 0){
      Stock practice = practiceOptional.get();
      Audit audit = Audit.builder().count(practice.getCount()).id(UUID.randomUUID().toString()).build();
      practice.setCount(practice.getCount() - 1);
      int update = stockRepository.updateCountWithVersion(practice.getCount(), practice.getVersion(), productId);
      if(update == 0) throw new OptimisticLockingException("Update failed");
      auditRepository.save(audit);
    }
  }
}
