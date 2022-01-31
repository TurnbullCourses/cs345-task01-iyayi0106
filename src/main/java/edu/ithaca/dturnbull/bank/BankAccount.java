package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) throws IllegalArgumentException{
        if (!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Invalid starting balance: " + startingBalance);
        }
        else if (!isEmailValid(email)){
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        else {
            this.email = email;
            this.balance = startingBalance;
        }
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
     * @param account
     * @throws IllegalArgumentException if amount is not valid
     */
    public void deposit(double amount, BankAccount account) throws IllegalArgumentException{
        //if (!isAmountValid(amount)){}

    }

    /**
     * @post transfers amount from account1 to account2
     * @param amount
     * @param account
     * @throws IllegalArgumentException if amount is not valid
     * @throws InsufficientFundsException if balance < amount
     */
    public void transfer(double amount, BankAccount account1, BankAccount account2) throws IllegalArgumentException, InsufficientFundsException{

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
    public static boolean isAmountValid(Double amount){
        if (amount < 0.00){
            return false;
        }
        
        String[] amountSplit = amount.toString().split("\\."); // change type double to string and split into arrays
        int afterDecimal = amountSplit[1].length(); // number of digits after decimals

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
        if (email.indexOf('@') == -1){ //needs an @ symbol to be valid
            return false;
        }
        else {
            String lettersAndNumbers = "abcdefghijklmnopqrstuvwxyz1234567890"; //valid characters
            String otherChars = "_.-"; //other valid characters for the prefix
            String[] tempArray = email.split("@"); //separate prefix and domain
            String prefix = tempArray[0];
            String domain = tempArray[1];
            prefix = prefix.toLowerCase(); //for easier checking
            domain = domain.toLowerCase();
            if (!lettersAndNumbers.substring(0, 26).contains(String.valueOf(prefix.charAt(0)))){ //first char of prefix must be a letter
                return false;
            }
            for (int i = 1; i < prefix.length(); i++){
                String tempString = String.valueOf(prefix.charAt(i));
                if (!lettersAndNumbers.contains(tempString) && !otherChars.contains(tempString)){ //make sure other chars in prefix valid
                    return false;
                }
                else if (tempString.equals(".") || tempString.equals("_") || tempString.equals("-")){
                    if (!lettersAndNumbers.contains(String.valueOf(prefix.charAt(i+1)))){ //no repeating symbols
                        return false;
                    }
                }
            }
            if (domain.indexOf('.') == -1){ //make sure that domain has .
                return false;
            }
            else{
                String[] tempDomainArray = domain.split("\\."); //split domain into initial part and . part
                String firstPortion = tempDomainArray[0];
                String secondPortion = tempDomainArray[1];
                if (secondPortion.length() < 2){ //make sure the end has 2 or more characters
                    return false;
                }
                for (int i = 0; i < firstPortion.length(); i++){ //make sure all characters are valid in first part
                    String tempString = String.valueOf(firstPortion.charAt(i));
                    if (!lettersAndNumbers.contains(tempString) && !tempString.equals("-")){
                        return false;
                    }
                }
                for (int i = 0; i < secondPortion.length(); i++){ //make sure all characters are valid in second part
                    String tempString = String.valueOf(secondPortion.charAt(i));
                    if (!lettersAndNumbers.contains(tempString) && !tempString.equals("-")){
                        return false;
                    }
                }
            }
            return true;
        }
    }
}