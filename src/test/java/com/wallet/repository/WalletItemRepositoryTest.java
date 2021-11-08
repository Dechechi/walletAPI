package com.wallet.repository;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.enums.TypeEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class WalletItemRepositoryTest {

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);
    private Long savedWalletItemId = null;
    private Long savedWalletId = null;

    @Autowired
    private WalletItemRepository repository;
    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    public void setUp(){
        Wallet w = new Wallet();
        w.setName("Carteira Teste");
        w.setValue(BigDecimal.valueOf(250));
        walletRepository.save(w);

        WalletItem wi = new WalletItem(null, w, DATE, TYPE, DESCRIPTION, VALUE);
        repository.save(wi);

        savedWalletItemId = wi.getId();
        savedWalletId = w.getId();
    }

    @AfterEach
    public void tearDown(){
        repository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    public void testSave(){

        Wallet wallet =  new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));
        walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(1L, wallet, DATE, TYPE, DESCRIPTION, VALUE);

        WalletItem response = repository.save(walletItem);

        assertNotNull(response);
        assertEquals(response.getDescription(), DESCRIPTION);
        assertEquals(response.getType(), TYPE);
        assertEquals(response.getValue(), VALUE);
        assertEquals(response.getWallet().getId(), wallet.getId());

    }

    @Test()
    public void testSaveInvalidWalletItem(){
        assertThrows(ConstraintViolationException.class, () ->{
            WalletItem wi = new WalletItem(null, null, DATE, null, DESCRIPTION, null);
            repository.save(wi);
        });
    }

    @Test
    public void testUpdate(){
        Optional<WalletItem> wi = repository.findById(savedWalletItemId);

        String description = "Descricao alterada";

        if(wi.isPresent()){
            WalletItem changed = wi.get();
            changed.setDescription(description);

            repository.save(changed);
        }

        Optional<WalletItem> newWalletItem = repository.findById(savedWalletItemId);

        newWalletItem.ifPresent(walletItem -> assertEquals(description, walletItem.getDescription()));
    }

    @Test
    public void deleteWalletItem(){
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        if(wallet.isPresent()){
            WalletItem wi = new WalletItem(null, wallet.get(), DATE, TYPE, DESCRIPTION, VALUE);

            repository.save(wi);

            repository.deleteById(wi.getId());

            Optional<WalletItem> response = repository.findById(wi.getId());

            assertFalse(response.isPresent());
        }

    }

    @Test
    public void testFindBetweenDates(){

        Optional<Wallet> w = walletRepository.findById(savedWalletId);
        LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
        Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

        if(w.isPresent()){
            repository.save(new WalletItem(null, w.get(), currentDatePlusFiveDays, TYPE, DESCRIPTION, VALUE));
            repository.save(new WalletItem(null, w.get(), currentDatePlusSevenDays, TYPE, DESCRIPTION, VALUE));

            PageRequest pg = PageRequest.of(0, 10);
            Page<WalletItem> response = repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(savedWalletId, DATE, currentDatePlusFiveDays, pg);

            assertEquals(response.getContent().size(), 2);
            assertEquals(response.getTotalElements(), 2);
            assertEquals(response.getContent().get(0).getWallet().getId(), savedWalletId);
        }
    }

    @Test
    public void testFindByType(){

        List<WalletItem> response = repository.findByWalletIdAndType(savedWalletId, TYPE);

        assertEquals(response.size(), 1);
        assertEquals(response.get(0).getType(), TYPE);

    }

    @Test
    public void testFindByTypeSd(){

        Optional<Wallet> w = walletRepository.findById(savedWalletId);

        if (w.isPresent()){
            repository.save(new WalletItem(null, w.get(), DATE, TypeEnum.SD, DESCRIPTION, VALUE));

            List<WalletItem> response = repository.findByWalletIdAndType(savedWalletId, TypeEnum.SD);

            assertEquals(response.size(), 1);
            assertEquals(response.get(0).getType(), TypeEnum.SD);
        }

    }

    @Test
    public void testSumByWallet(){

        Optional<Wallet> w = walletRepository.findById(savedWalletId);

        if(w.isPresent()){
            repository.save(new WalletItem(null, w.get(), DATE, TYPE, DESCRIPTION, BigDecimal.valueOf(150.80)));

            BigDecimal response = repository.sumByWalletId(savedWalletId);

            assertEquals(response.compareTo(BigDecimal.valueOf(215.8)), 0);
        }

    }

}
