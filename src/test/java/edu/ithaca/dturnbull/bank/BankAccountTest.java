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
        assertNotEquals(201, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        bankAccount.withdraw(100); // if amount < balance 
        assertEquals(100, bankAccount.getBalance(), 0.001);

        bankAccount.withdraw(100); // if amount == balance
        assertEquals(0, bankAccount.getBalance(), 0.001);

        bankAccount.withdraw(0); // if amount == 0
        assertEquals(0, bankAccount.getBalance(), 0.001);

        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); // if amount > balance
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(123.456)); // if positive amount with too many digits after decimal
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-123.45)); // if amount < 0
    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 123);
        
        bankAccount.deposit(100); // amount > 0
        assertEquals(223, bankAccount.getBalance(), 0.001);
        
        bankAccount.deposit(0); // amount == 0
        assertEquals(223, bankAccount.getBalance(), 0.001);
        
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-123)); // amount < 0
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(12.345)); // amount has more than two digits after decimal
    }

    @Test
    void transferTest() throws IllegalArgumentException, InsufficientFundsException{
        BankAccount accountFrom = new BankAccount("from@b.com",250);
        BankAccount accountTo = new BankAccount("to@b.com", 200);

        accountFrom.transfer(accountTo, 25.50); // amount > 0
        assertEquals(224.50, accountFrom.getBalance(), 0.001);
        assertEquals(225.50, accountTo.getBalance(), 0.001);

        accountFrom.transfer(accountTo, 0); // amount == 0
        assertEquals(224.50, accountFrom.getBalance(), 0.001);
        assertEquals(225.50, accountTo.getBalance(), 0.001);

        assertThrows(IllegalArgumentException.class, () -> accountFrom.transfer(accountTo, -12)); // amount < 0
        assertThrows(IllegalArgumentException.class, () -> accountFrom.transfer(accountTo, 12.345)); // amount with too many digits after decimal
        assertThrows(InsufficientFundsException.class, () -> accountFrom.withdraw(250)); // amount > balance
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //equivalence class - no punctuation in prefix
        assertFalse( BankAccount.isEmailValid("")); //equivalence class - no email given
        assertFalse( BankAccount.isEmailValid("a@b@c.com")); //more than one '@'
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
        assertTrue(BankAccount.isAmountValid(100)); // if amount has no digits after decimal
        assertTrue(BankAccount.isAmountValid(100.12)); // if amount has two digits after decimal
        assertTrue(BankAccount.isAmountValid(100.120)); // if amount has more than two digits after decimal but last digit == 0
        assertTrue(BankAccount.isAmountValid(100.0)); // if amount has one digit after decimal
        assertFalse(BankAccount.isAmountValid(100.123)); // if amount had greater than two digits after decimal
        assertFalse(BankAccount.isAmountValid(-100.12)); // if amount < 0
        assertTrue(BankAccount.isAmountValid(0.00)); // if amount == 0.00
    }

    @Test
    void constructorTest() throws IllegalArgumentException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200); //valid email and amount inputs
        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);

        BankAccount bankAccount1 = new BankAccount("b@c.com", 0); // if email is valid and balance == 0
        assertEquals("b@c.com", bankAccount1.getEmail());
        assertEquals(0, bankAccount1.getBalance(), 0.001);
    
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b", 123)); // if email is invalid and balance is valid
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 123.456));// if email is valid and balance < 0
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100)); // no email input and valid balance
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -123.45)); // valid email and amount < 0
    }

}