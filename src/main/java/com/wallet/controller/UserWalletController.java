package com.wallet.controller;

import com.wallet.dto.UserWalletDto;
import com.wallet.entity.User;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;
import com.wallet.response.Response;
import com.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user-wallet")
public class UserWalletController {

    private UserWalletService userWalletService;

    @Autowired
    public UserWalletController(UserWalletService userWalletService) {
        this.userWalletService = userWalletService;
    }

    @PostMapping
    public ResponseEntity<Response<UserWalletDto>> create(@Valid @RequestBody UserWalletDto dto, BindingResult result){

        Response<UserWalletDto> response = new Response<>();

        if(result.hasErrors()){
            result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        UserWallet userWallet = userWalletService.save(convertDtoToEntity(dto));

        response.setData(convertEntityToDto(userWallet));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    public UserWallet convertDtoToEntity(UserWalletDto dto){
        UserWallet userWallet = new UserWallet();

        User user =  new User();
        user.setId(dto.getUsers());

        Wallet wallet = new Wallet();
        wallet.setId(dto.getWallet());

        userWallet.setId(dto.getId());
        userWallet.setUsers(user);
        userWallet.setWallet(wallet);
        return userWallet;
    }

    public UserWalletDto convertEntityToDto(UserWallet userWallet){
        UserWalletDto userWalletDto = new UserWalletDto();
        userWalletDto.setId(userWallet.getId());
        userWalletDto.setUsers(userWallet.getUsers().getId());
        userWalletDto.setWallet(userWallet.getWallet().getId());
        return userWalletDto;
    }
}
