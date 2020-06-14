package pl.todosandjokes.api.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    EmailValidator emailValidator = new EmailValidator();

    @Test
    void isValidEmailShouldReturnTrue() {
        String email = "email@wp.pl";
        assertTrue(emailValidator.isValidEmail(email));
    }

    @Test
    void isValidEmailWithoutAtShouldReturnFalse() {
        String email = "emailwp.pl";
        assertFalse(emailValidator.isValidEmail(email));
    }

    @Test
    void isValidEmailWithoutFirstSegmentShouldReturnFalse() {
        String email = "@wp.pl";
        assertFalse(emailValidator.isValidEmail(email));
    }

    @Test
    void isValidEmailWithoutDotShouldReturnFalse() {
        String email = "email@wppl";
        assertFalse(emailValidator.isValidEmail(email));
    }

    @Test
    void isValidEmailEmptyStringShouldReturnFalse() {
        String email = "";
        assertFalse(emailValidator.isValidEmail(email));
    }

    @Test
    void isValidEmailWithoutTopLevelDomainNameShouldReturnFalse() {
        String email = "email@wp.";
        assertFalse(emailValidator.isValidEmail(email));
    }

    @Test
    void isValidEmailLocalPartLongerThan64CharactersShouldReturnFalse() {
        String email = "emailaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@wp.";
        assertFalse(emailValidator.isValidEmail(email));
    }

}