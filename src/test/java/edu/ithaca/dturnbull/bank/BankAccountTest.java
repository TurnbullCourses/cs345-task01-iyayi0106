package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;


class BankAccountTest {

    @Test
    @DisplayName("Get correct balance")
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200); // if balance > 0
        assertEquals(200, bankAccount.getBalance(), 0.001);

        BankAccount bankAccount1 = new BankAccount("a@b.com", 0); //if balance == 0
        assertEquals(0, bankAccount1.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100)); // if amount < 0

        bankAccount.withdraw(100); // if amount < balance 
        assertEquals(100, bankAccount.getBalance(), 0.001);

        bankAccount.withdraw(100); // if amount == balance
        assertEquals(0, bankAccount.getBalance(), 0.001);

        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); // if amount > balance
    
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(123.456)); // positive amount with too many digits after decimal
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-123.45)); // amount < 0
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //equivalence class - no punctuation in prefix
        assertFalse( BankAccount.isEmailValid("")); //equivalence class - no email given
        
        assertTrue(BankAccount.isEmailValid("a@b-c.com")); //equivalence class - dash in domain
        assertTrue(BankAccount.isEmailValid("a1@b.com")); //equivalence class - number in prefix
        assertTrue(BankAccount.isEmailValid("a.1@b.com")); //equivalence class - period in prefix, and number in prefix
        assertFalse(BankAccount.isEmailValid("1@b.com")); //equivalence class - only number in prefix
        assertFalse(BankAccount.isEmailValid("a..b@c.com")); //equivalence class - double period in prefix
        assertFalse(BankAccount.isEmailValid(".a@b.com")); //boundary case - period in prefix but in front
        assertFalse(BankAccount.isEmailValid("a#@b.com")); //equivalence class - invalid character in prefix
        assertFalse(BankAccount.isEmailValid("a@b..com")); //equivalence class - double period in domain
        assertFalse(BankAccount.isEmailValid("a@b")); //equivalence class - ending shorter than two characters
        assertTrue(BankAccount.isEmailValid("a@b.co")); //boundary case - ending domain w/ exactly 2 chars
        assertFalse(BankAccount.isEmailValid("a@b#.com")); //equivalence class - invalid character in domain

        //equivalence class where an _ is used

    }

    @Test
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(100.12)); // if amount has two digits after decimal
        assertTrue(BankAccount.isAmountValid(100.0)); // if amount has one digit after decimal
        assertFalse(BankAccount.isAmountValid(100.123)); // if amount had greater than two digits after decimal
        assertFalse(BankAccount.isAmountValid(-100.12)); // if amount < 0
        assertTrue(BankAccount.isAmountValid(0.00)); // if amount == 0.00
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 123.456)); // positive amount with too many digits after decimal
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -123.45)); // amount < 0
    }

}