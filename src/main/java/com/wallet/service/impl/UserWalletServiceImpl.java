package com.wallet.service.impl;

import com.wallet.entity.UserWallet;
import com.wallet.repository.UserWalletRepository;
import com.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserWalletServiceImpl implements UserWalletService {

    UserWalletRepository userWalletRepository;

    @Autowired
    public UserWalletServiceImpl(UserWalletRepository userWalletRepository) {
        this.userWalletRepository = userWalletRepository;
    }

    @Override
    public UserWallet save(UserWallet userWallet) {
        return userWalletRepository.save(userWallet);
    }

    @Override
    public Optional<UserWallet> findByUsersIdAndWalletId(Long user, Long wallet) {
        return userWalletRepository.findByUsersIdAndWalletId(user, wallet);
    }
}
