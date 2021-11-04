package com.wallet.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserWalletDto {

    private Long id;
    @NotNull(message = "Informe o id do usuario")
    private Long users;
    @NotNull(message = "Informe o id da carteira")
    private Long wallet;
}
