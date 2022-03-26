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
@Table(name = "stock_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

  @Id
  @Column(name = "id", unique = true)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "count")
  private int count;

  @Column(name = "version")
  private int version;

}
