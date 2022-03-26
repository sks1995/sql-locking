package com.practice.locking.sqllocking.persistence.repository;

import com.practice.locking.sqllocking.persistence.entity.Stock;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {


  /**
   * Does a simple update on Id
   */
  @Transactional
  @Modifying
  @Query(value =
      "update Stock p "
      + "set p.count = :count "
      + "where p.id = :id")
  int updateCount(
      @Param("count") int count,
      @Param("id") String id
  );

  /**
   *
   * Used for optimistic locking.
   * Considers updating version field while updating the record.
   */
  @Transactional
  @Modifying
  @Query(value =
      "update Stock p "
      + "set p.count = :count, "
      + "p.version = :version + 1 "
      + "where p.id = :id and p.version = :version")
  int updateCountWithVersion(
      @Param("count") int count,
      @Param("version") int version,
      @Param("id") String id
  );

  /**
   * Acquire mutually exclusive lock. Used for pessimistic locking
   */
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query(value =
      "select p from Stock p"
      + " where p.id = :id")
  Optional<Stock> findById1(
      @Param("id") String id
  );
}
