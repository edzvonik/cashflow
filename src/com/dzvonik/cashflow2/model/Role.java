package com.dzvonik.cashflow2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@Table(name = "roles")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

  @Id
  @SequenceGenerator(name = "seq_roles", sequenceName = "seq_roles")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_roles")
  private Long id;

  @Column(nullable = false)
  private String name;

}
