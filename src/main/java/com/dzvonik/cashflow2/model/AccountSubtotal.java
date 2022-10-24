package com.dzvonik.cashflow2.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"calculatedAt", "updatedAt", "subtotal"})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSubtotal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_subtotal")
    @SequenceGenerator(name = "seq_account_subtotal", sequenceName = "seq_account_subtotal")
    private Long id;

    @CreationTimestamp
    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private BigDecimal subtotal;

}
