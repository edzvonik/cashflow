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
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Embeddable
@EqualsAndHashCode(of = {"calculatedAt"})
@ToString(of = {"calculatedAt", "subtotal"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AccountSubtotal {

    @Column(nullable = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @CreationTimestamp
    private LocalDateTime calculatedAt;

    @Column(nullable = false)
    private BigDecimal subtotal;

}
