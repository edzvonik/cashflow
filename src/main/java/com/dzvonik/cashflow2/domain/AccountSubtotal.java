package com.dzvonik.cashflow2.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"calculatedAt", "subtotal"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AccountSubtotal {

    @Id
    @SequenceGenerator(name = "seq_account_subtotal", sequenceName = "seq_account_subtotal")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_subtotal")
    private Long id;

    @CreationTimestamp
    private LocalDateTime calculatedAt;

    @Column(nullable = false)
    private BigDecimal subtotal;

}
