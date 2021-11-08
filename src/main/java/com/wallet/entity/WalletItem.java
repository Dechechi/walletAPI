package com.wallet.entity;

import com.wallet.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "wallet_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletItem implements Serializable {

    private static final long serialVersionUID = -1796790346218894235L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "wallet", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;
    @NotNull
    private Date date;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeEnum type;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal value;
}
