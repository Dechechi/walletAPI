package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.wallet.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {
    
    @Autowired
    UserRepository repository;

    @Test
    public void testSave(){
        User user = new User();
        user.setName("Teste");
        user.setPassword("123456");
        user.setEmail("teste@teste.com");

        User response = repository.save(user);

        assertNotNull(response);
    }

}
