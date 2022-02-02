package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * the constructor should not rely on isAmountValid
     * 0 as the starting balance should be a valid input
     * but, 0 as an amount for withdraw, deposit, or trasfer should be an invalid input
     * @post initializes BankAccount object with an email and starting balance
     * @param email
     * @param startingBalance
     * @throws IllegalArgumentException if email or starting balance is invalid
     */
    public BankAccount(String email, double startingBalance) throws IllegalArgumentException{
        if (!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Invalid starting balance: " + startingBalance);
        }
        else if (!isEmailValid(email)){
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    
        this.email = email;
        this.balance = startingBalance;
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post deposits amount into balance of account
     * @param amount
     * @throws IllegalArgumentException if amount is not valid
     */
    public void deposit(double amount) throws IllegalArgumentException{
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException("Invalid deposit amount: " + amount);
        }
        else{
            balance += amount;
        }
    }

    /**
     * @post transfers amount from account1 to account2
     * @param accountTo
     * @param amount
     * @throws IllegalArgumentException if amount is not valid
     * @throws InsufficientFundsException if balance < amount
     */
    public void transfer(BankAccount accountTo, double amount) throws IllegalArgumentException, InsufficientFundsException{
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException("Invalid transfer amount: " + amount);
        }

        this.withdraw(amount);
        accountTo.deposit(amount);
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @param amount
     * @throws InsufficientFundsException if balance < amount
     * @throws IllegalArgumetException if amount is not valid
     */
    public void withdraw (double amount) throws InsufficientFundsException, IllegalArgumentException{
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException("Invalid withdrawal amount: " + amount);
        }
        else if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * @param amount
     * @return true if the amount < 0 and has two decimal points or less, and false otherwise
     */
    public static boolean isAmountValid(double amount){
        if (amount < 0){
            return false;
        }
        
        String[] amountSplit = Double.toString(amount).split("\\."); // change type double to string and split into arrays
        int afterDecimal = amountSplit[1].length(); // number of digits after decimal

        if (afterDecimal > 2){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * @param email
     * @return true if all characters of prefix and domain follow email requirements, otherwise false
     */
    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        else {
            String validChars = "abcdefghijklmnopqrstuvwxyz1234567890"; // all valid chars
            String specialChars = "_-.";
            String[] splitEmail = email.split("@"); // separate prefix and domain into diff arrays
            String prefix = splitEmail[0];
            String domain = splitEmail[1];
            prefix = prefix.toLowerCase(); // normalizing prefix
            domain = domain.toLowerCase(); // normalizing domain
            // first char of prefix must be in alphabet
            if (!validChars.substring(0, 26).contains(String.valueOf(prefix.charAt(0)))){
                return false;
            }
            // last char of prefix must be in validChars
            if (!validChars.contains(String.valueOf(prefix.charAt(prefix.length()-1)))){
                return false;
            }
            
            for (int i = 1; i < prefix.length(); i++){
                String tempChar = String.valueOf(prefix.charAt(i));
                
                // is rest of prefix valid
                if (!validChars.contains(tempChar) && !specialChars.contains(tempChar)){ 
                    return false;
                }
                else if (tempChar.equals(".") || tempChar.equals("_") || tempChar.equals("-")){
                    
                    // no consecutive symbols
                    if (!validChars.contains(String.valueOf(prefix.charAt(i+1)))){ 
                        return false;
                    }
                }
            }
            // does domain have '.'
            if (domain.indexOf('.') == -1){ 
                return false;
            }
            else{
                String[] domainSplit = domain.split("\\."); // split domain into before and after '.'
                String domain1 = domainSplit[0];
                String domain2 = domainSplit[1];
                // make sure the end has 2 or more characters
                if (domain2.length() < 2){ 
                    return false;
                }
                // validation of domain's first part
                for (int i = 0; i < domain1.length(); i++){
                    String tempChar = String.valueOf(domain1.charAt(i));
                    if (!validChars.contains(tempChar) && !tempChar.equals("-")){
                        return false;
                    }
                    else if (tempChar.equals("-")){
                    
                        // no consecutive symbols
                        if (!validChars.contains(String.valueOf(domain1.charAt(i+1)))){ 
                            return false;
                        }
                    }
                }
                // validation of domain's second part
                for (int i = 0; i < domain2.length() - 1; i++){
                    String tempChar = String.valueOf(domain2.charAt(i));
                    if (!validChars.contains(tempChar) && !tempChar.equals("-")){
                        return false;
                    }
                    else if (tempChar.equals("-")){
                    
                        // no consecutive symbols
                        if (!validChars.contains(String.valueOf(domain2.charAt(i+1)))){ 
                            return false;
                        }
                    }
                }
                if (!validChars.contains(String.valueOf(domain2.charAt(domain2.length()-1)))){
                    return false;
                }
            }
            return true;
        }
    }
}