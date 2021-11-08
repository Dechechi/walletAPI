package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.wallet.entity.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    private static final String EMAIL = "email@teste.com";
    
    @Autowired
    UserRepository repository;

    @BeforeEach
    public void setUp(){

        User user = new User();
        user.setName("SetUpUser");
        user.setPassword("12345678");
        user.setEmail(EMAIL);

        repository.save(user);

    }

    @AfterEach
    public void tearDown(){

        repository.deleteAll();

    }

    @Test
    public void testSave(){
        User user = new User();
        user.setName("Teste");
        user.setPassword("123456");
        user.setEmail("teste@teste.com");

        User response = repository.save(user);

        assertNotNull(response);
    }

    @Test
    public void testFindByEmail(){
        Optional<User> response = repository.findByEmailEquals(EMAIL);

        assertTrue(response.isPresent());
        assertEquals(response.get().getEmail(), EMAIL);
    }

}
