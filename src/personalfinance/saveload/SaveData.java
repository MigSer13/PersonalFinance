package personalfinance.saveload;

import personalfinance.model.*;

import java.util.ArrayList;
import java.util.List;

public class SaveData {
    private static SaveData instance;

    private List<Article> articles = new ArrayList<>();
    private List<Currency> currencies = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Transfer> transfers = new ArrayList<>();

    private SaveData() {
    }

    public static SaveData getInstance() {
        if(instance == null){
            instance = new SaveData();
        }
        return instance;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public Currency getBaseCurrency(){
        for (Currency currency : currencies){
            if (currency.isBase()){
                return currency;
            }

        }
        return new Currency();
    }
}
