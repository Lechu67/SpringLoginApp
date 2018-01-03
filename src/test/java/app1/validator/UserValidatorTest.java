package app1.validator;

import app1.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

    @InjectMocks
    private UserValidator validator;

    @Test
    public void shouldSupportOnlyUserCutstomClass(){
        assertTrue(validator.supports(UserEntity.class));
        assertFalse(validator.supports(String.class));
    }
}