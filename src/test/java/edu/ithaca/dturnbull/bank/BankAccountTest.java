package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;


class BankAccountTest {

    @Test
    @DisplayName("Get correct balance")
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
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
        assertFalse(BankAccount.isEmailValid("a#@b.com")); //duplicate test
        assertFalse(BankAccount.isEmailValid("a@b#.com")); //equivalence class - invalid character in domain

        //other possible tests - boundary case where end of domain is exactly two characters, equivalence class where an _ is used





    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}