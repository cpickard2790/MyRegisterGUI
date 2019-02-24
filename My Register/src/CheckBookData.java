/*
CheckBookData class simulates a check register
*/

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


public class CheckBookData
{
    private SimpleStringProperty checkNumber, transaction,withdraw, deposit, date, balance;
 
    
    /**
    constructor
    @param transaction The details of the transaction 
    @param withdraw The amount to withdraw/subtract from balance
    @param deposit The amount to deposit/add to balance
    @param balance The total
    @param checkNumber The check number
    @param date The date of transaction
    */
    
    public CheckBookData(String transaction, String withdraw, String deposit,
            String balance, String checkNumber, String date)
    {
        this.transaction = new SimpleStringProperty(transaction);
        this.withdraw = new SimpleStringProperty(withdraw);
        this.deposit = new SimpleStringProperty(deposit);
        this.balance = new SimpleStringProperty(balance);
        this.checkNumber = new SimpleStringProperty(checkNumber);
        this.date = new SimpleStringProperty(date);
    }
    
    public CheckBookData(String transaction, String balance)
    {
        this.transaction = new SimpleStringProperty(transaction);
        this.balance = new SimpleStringProperty(balance);
    }
    

    public String getDate()
    {
        return date.get();
    }

    public void setDate(SimpleStringProperty date)
    {
        this.date = date;
    }
    
    public CheckBookData(String balance)
    {
        this.balance = new SimpleStringProperty(balance);
    }
    

    public String getCheckNumber()
    {
        return checkNumber.get();
    }

    public void setCheckNumber(SimpleStringProperty checkNumber)
    {
        this.checkNumber = checkNumber;
    }

    public String getTransaction()
    {
        return transaction.get();
    }

    public void setTransaction(SimpleStringProperty transaction)
    {
        this.transaction = transaction;
    }

    public String getWithdraw()
    {
        return withdraw.get();
    }

    public void setWithdraw(SimpleStringProperty withdraw)
    {
        this.withdraw = withdraw;
    }

    public String getDeposit()
    {
        return deposit.get();
    }

    public void setDeposit(SimpleStringProperty deposit)
    {
        this.deposit = deposit;
    }
    
    public String getBalance()
    {
        return balance.get();
    }
    
    public void setBalance(SimpleStringProperty balance)
    {
        this.balance = balance;
    }

}
