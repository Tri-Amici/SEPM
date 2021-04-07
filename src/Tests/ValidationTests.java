package Tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import TriAmici.Validation;

class ValidationTests {

	@Test
	void validEmail0() {
		assertTrue(Validation.validEmail("firstname@email.com"));
	}
	
	@Test
	void validEmail1() {
		assertTrue(Validation.validEmail("firstname@email.com.au"));
	}
	
	@Test
	void invalidEmail0() {
		assertFalse(Validation.validEmail("firstname@emailcom"));
	}
	
	@Test
	void invalidEmail1() {
		assertFalse(Validation.validEmail("firstname2emailcom"));
	}
	
	@Test
	void validLengthTrue0() {
		assertTrue(Validation.validLength((short)5, "1234"));
	}
	
	@Test
	void validLengthTrue1() {
		assertTrue(Validation.validLength((short)5, "12345"));
	}
	
	@Test
	void validLengthFalse0() {
		assertFalse(Validation.validLength((short)5, "123456"));
	}
	
	@Test
	void validPhoneVIC0() {
		assertTrue(Validation.validPhone("0312345678"));
	}
	
	@Test
	void validPhoneVIC1() {
		assertTrue(Validation.validPhone("03 1234 5678"));
	}
	
	@Test
	void validPhoneVIC2() {
		assertTrue(Validation.validPhone("03 1234-5678"));
	}
	
	@Test
	void validPhoneNSW0() {
		assertTrue(Validation.validPhone("0212345678"));
	}
	
	@Test
	void validPhoneNSW1() {
		assertTrue(Validation.validPhone("02 1234 5678"));
	}
	
	@Test
	void validPhoneNSW2() {
		assertTrue(Validation.validPhone("02 1234-5678"));
	}
	
	@Test
	void validPhoneQLD0() {
		assertTrue(Validation.validPhone("0712345678"));
	}
	
	@Test
	void validPhoneQLD1() {
		assertTrue(Validation.validPhone("07 1234 5678"));
	}
	
	@Test
	void validPhoneQLD2() {
		assertTrue(Validation.validPhone("07 1234-5678"));
	}
	
	@Test
	void validPhoneWA0() {
		assertTrue(Validation.validPhone("0812345678"));
	}
	
	@Test
	void validPhoneWA1() {
		assertTrue(Validation.validPhone("08 1234 5678"));
	}
	
	@Test
	void validPhoneWA2() {
		assertTrue(Validation.validPhone("08 1234-5678"));
	}
	
	@Test
	void validMobile0() {
		assertTrue(Validation.validPhone("0412 345 678"));
	}
	
	@Test
	void validMobile1() {
		assertTrue(Validation.validPhone("0412345678"));
	}
	
	@Test
	void invalidPhone0() {
		assertFalse(Validation.validPhone("asdfadf#$$#cc"));
	}
	
	@Test
	void invalidPhone1() {
		assertFalse(Validation.validPhone("031234567"));
	}
	
	@Test
	void invalidPhone2() {
		assertFalse(Validation.validPhone("03 1234 567"));
	}
	
	@Test
	void invalidPhone3() {
		assertFalse(Validation.validPhone("03-1234 567"));
	}
	
	@Test
	void validPassword0() {
		assertTrue(Validation.validPassword("Passw0rd"));
	}
	
	@Test
	void invalidPassword0() {
		assertFalse(Validation.validPassword("Passw0r"));
	}
	
	@Test
	void invalidPassword1() {
		assertFalse(Validation.validPassword("Password"));
	}
	
	@Test
	void invalidPassword2() {
		assertFalse(Validation.validPassword("password"));
	}
	
	@Test
	void invalidPassword3() {
		assertFalse(Validation.validPassword("PASSWORD"));
	}
	
	@Test
	void invalidPassword4() {
		assertFalse(Validation.validPassword("12345678"));
	}
	
	@Test
	void invalidPassword5() {
		assertFalse(Validation.validPassword("Pa#sw0rd"));
	}
	
	@Test
	void validInteger0() {
		assertTrue(Validation.validInteger("0123"));
	}
	
	@Test
	void invalidInteger0() {
		assertFalse(Validation.validInteger("Text!"));
	}
	
	@Test
	void validShortRange0() {
		assertTrue(Validation.validShortRange((short)1, (short)3, (short)1));
	}

	@Test
	void validShortRange1() {
		assertTrue(Validation.validShortRange((short)1, (short)3, (short)2));
	}
	
	@Test
	void validShortRange2() {
		assertTrue(Validation.validShortRange((short)1, (short)3, (short)3));
	}
	
	@Test
	void invalidShortRange0() {
		assertFalse(Validation.validShortRange((short)1, (short)3, (short)0));
	}
	
	@Test
	void invalidShortRange1() {
		assertFalse(Validation.validShortRange((short)1, (short)3, (short)4));
	}
	
}
