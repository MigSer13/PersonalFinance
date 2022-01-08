package personalfinance.saveload;

import personalfinance.exception.ModelException;
import personalfinance.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public final class SaveData {
    private static SaveData instance;

    private List<Article> articles = new ArrayList<>();
    private List<Currency> currencies = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Transfer> transfers = new ArrayList<>();
    private final Filter filter;
    private Common oldCommon;
    private boolean saved = true;

    private SaveData() {
        load();
        this.filter = new Filter();
    }

    public void load() {
        SaveLoad.load(this);
        sort();
    }

    private void sort() {
        this.articles.sort((Article a1, Article a2) -> a1.getTitle().compareToIgnoreCase(a2.getTitle()));
        this.accounts.sort((Account a1, Account a2) -> a1.getTitle().compareToIgnoreCase(a2.getTitle()));
        this.transactions.sort((Transaction t1, Transaction t2) -> (int) (t1.getDate().compareTo(t2.getDate())));
        this.transfers.sort((Transfer t1, Transfer t2) -> (int) (t1.getDate().compareTo(t2.getDate())));
        this.currencies.sort(new Comparator<Currency>() {
            @Override
            public int compare(Currency c1, Currency c2) {
                if(c1.isBase()){
                    return -1;
                }
                if(c2.isBase()){
                    return 1;
                }
                if(c1.isOn() ^ c2.isOn()){
                    if(c2.isOn()) {
                        return 1;
                    }else {
                        return -1;
                    }
                }

                return c1.getTitle().compareToIgnoreCase(c2.getTitle());
            }
        });
    }

    public void save(){
        SaveLoad.save(this);
        saved = true;
    }

    public boolean isSaved() {
        return saved;
    }

    public Filter getFilter() {
        return filter;
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

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public ArrayList<Currency> getEnableCurrencies(){
        ArrayList<Currency> list = new ArrayList<>();
        for (Currency currency : currencies){
            if (currency.isOn()){
                list.add(currency);
            }
        }
        return list;
    }

    public List<Transaction> getFilterTransactions(){
        ArrayList<Transaction> list = new ArrayList<>();
        for (Transaction transaction : transactions){
            if (filter.check(transaction.getDate())){
                list.add(transaction);
            }
        }
        return list;
    }
    public List<Transaction> getTransactionsOnCount(int count){
        return new ArrayList<>(transactions.subList(0, Math.min(count, transactions.size())));
    }
    public List<Transfer> getFilterTransfers(){
        ArrayList<Transfer> list = new ArrayList<>();
        for (Transfer transfer : transfers){
            if (filter.check(transfer.getDate())){
                list.add(transfer);
            }
        }
        return list;
    }

    public Common getOldCommon(){
        return oldCommon;
    }

    public void add(Common common) throws ModelException {
        List ref = getRef(common);
        if (ref.contains(common)){
            throw new ModelException(ModelException.IS_EXIST);
        }
        ref.add(common);
        common.postAdd(this);
        sort();
        saved = false;
    }
    public void edit(Common oldC, Common newCommon) throws ModelException {
        List ref = getRef(oldC);
        if (ref.contains(newCommon) && oldC != ref.get(ref.indexOf(newCommon))){
            throw new ModelException(ModelException.IS_EXIST);
        }
        ref.set(ref.indexOf(oldC), newCommon);
        oldCommon = oldC;
        newCommon.postAdd(this);
        sort();
        saved = false;
    }
    public void remove(Common c){
        getRef(c).remove(c);
        c.postAdd(this);
        saved = false;
    }

    @Override
    public String toString() {
        return "SaveData{" +
                "articles=" + articles +
                ", currencies=" + currencies +
                ", accounts=" + accounts +
                ", transactions=" + transactions +
                ", transfers=" + transfers +
                '}';
    }

    private List getRef(Common common) {
        if(common instanceof Article){
            return articles;
        } else if(common instanceof Account){
            return accounts;
        } else if(common instanceof Currency){
            return currencies;
        } else if(common instanceof Transaction){
            return transactions;
        } else if(common instanceof Transfer){
            return transfers;
        }
        return null;
    }

    public void updateCurrencies() throws Exception {
        HashMap<String, Double> rates = RateCurrency.getRate(getBaseCurrency());
        for (Currency currency : currencies) {
            currency.setRate(rates.get(currency.getCode()));
        }
        for (Account account : accounts) {
            account.getCurrency().setRate(rates.get(account.getCurrency().getCode()));
        }

    }
}
