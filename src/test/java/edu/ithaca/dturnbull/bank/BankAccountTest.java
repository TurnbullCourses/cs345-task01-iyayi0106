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
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));
        
        assertTrue(BankAccount.isEmailValid("a@b-c.com"));
        assertTrue(BankAccount.isEmailValid("a1@b.com"));
        assertTrue(BankAccount.isEmailValid("a.1@b.com"));
        assertFalse(BankAccount.isEmailValid("1@b.com"));
        assertFalse(BankAccount.isEmailValid("a..b@c.com"));
        assertFalse(BankAccount.isEmailValid(".a@b.com"));
        assertFalse(BankAccount.isEmailValid("a#@b.com"));
        assertFalse(BankAccount.isEmailValid("a@b..com"));
        assertFalse(BankAccount.isEmailValid("a@b"));
        assertFalse(BankAccount.isEmailValid("a#@b.com"));
        assertFalse(BankAccount.isEmailValid("a@b#.com"));




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