package com.practice.locking.sqllocking.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "audit_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Audit {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  private String id;


  @Column(name = "count", nullable = false)
  private int count;

}
