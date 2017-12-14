package app1.validator;

import app1.model.UserCustom;
import app1.repository.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

    @InjectMocks
    private UserValidator validator;

    @Test
    public void shouldSupportOnlyUserCutstomClass(){
        assertTrue(validator.supports(UserCustom.class));
        assertFalse(validator.supports(String.class));
    }
}