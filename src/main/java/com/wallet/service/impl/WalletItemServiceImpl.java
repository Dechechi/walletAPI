package com.wallet.service.impl;

import com.wallet.entity.WalletItem;
import com.wallet.enums.TypeEnum;
import com.wallet.repository.WalletItemRepository;
import com.wallet.service.WalletItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WalletItemServiceImpl implements WalletItemService {

    WalletItemRepository repository;

    @Value("${pagination.items_per_page}")
    private int itemsPerPage;

    @Autowired
    public WalletItemServiceImpl(WalletItemRepository repository) {
        this.repository = repository;
    }

    @Override
    @CacheEvict(value = "findByWalletandType", allEntries = true)
    public WalletItem save(WalletItem walletItem) {
        return repository.save(walletItem);
    }

    @Override
    public Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page) {
        PageRequest pg = PageRequest.of(page, itemsPerPage);

        return repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet, start, end, pg);
    }

    @Override
    @Cacheable(value = "findByWalletAndType")
    public List<WalletItem> findByWalletAndType(Long wallet, TypeEnum typeEnum) {
        return repository.findByWalletIdAndType(wallet, typeEnum);
    }

    @Override
    public BigDecimal sumByWalletId(Long wallet) {
        return repository.sumByWalletId(wallet);
    }

    @Override
    public Optional<WalletItem> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @CacheEvict(value = "findByWalletandType", allEntries = true)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
