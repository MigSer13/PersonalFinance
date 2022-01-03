package personalfinance;

import personalfinance.exception.ModelException;
import personalfinance.model.*;
import personalfinance.saveload.SaveData;
import personalfinance.settings.Settings;
import personalfinance.settings.Text;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalFinance {

    public static void main(String[] args) throws ModelException {
        init();
//        SaveData saveData = SaveData.getInstance();
        testModel();
    }

    public static void init(){
        try {
            Settings.init();
            Text.init();
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(
                    Font.createFont(Font.TRUETYPE_FONT, Settings.FONT_ROBOTO_LIGHT));
        }catch (FontFormatException | IOException e){
            Logger.getLogger(PersonalFinance.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private static void testModel() throws ModelException {
        Currency rub = new Currency("Рубль", "RUB", 1, true, true);
        Currency usd = new Currency("Доллар", "USD", 65, true, false);
        Currency eur = new Currency("Евро", "EUR", 75, false, false);
        Currency uah    = new Currency("Гривна", "UAH", 2.5, false, false);

        Account wallet = new Account("Кошелек", rub, 1000);
        Account card_VISA = new Account("Карта VISA", rub, 0);
        Account deposit_RUB = new Account("Депозит в банке (РУБ)", rub, 100_000);
        Account deposit_USD = new Account("Депозит в банке (USD)", usd, 0);

        Article products = new Article("Продукты");
        Article communal = new Article("ЖКХ");
        Article salary = new Article("Зарплата");
        Article cafeteria = new Article("Столовая");
        Article persentsDepozit = new Article("Проценты по депозиту");

        ArrayList<Currency> currencies = new ArrayList<>();
        currencies.add(rub);
        currencies.add(usd);
        currencies.add(eur);
        currencies.add(uah);

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(wallet);
        accounts.add(card_VISA);
        accounts.add(deposit_RUB);
        accounts.add(deposit_USD);

        ArrayList<Article> articles = new ArrayList<>();
        articles.add(products);
        articles.add(communal);
        articles.add(salary);
        articles.add(cafeteria);
        articles.add(persentsDepozit);

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(card_VISA, salary, 30_000));
        transactions.add(new Transaction(card_VISA, products, -1500, "На выходные"));
        transactions.add(new Transaction(wallet, communal, -5500, "Основная квартира"));
        transactions.add(new Transaction(wallet, communal, -4000, "Вторая квартира"));
        transactions.add(new Transaction(deposit_RUB, persentsDepozit, 1000));
        transactions.add(new Transaction(card_VISA, salary, 25_000, new Date(new Date().getTime() -  (long) 86400_000 * 30)));
        transactions.add(new Transaction(deposit_RUB, persentsDepozit, 1000, new Date(new Date().getTime() -  (long) 86400_000 * 30)));

        for (int i = 0; i < 50; i++) {
            Article tempArticle;
            Account tempAccount;
            if (Math.random() < 0.5){
                tempArticle = cafeteria;
                tempAccount = wallet;
            }else {
                tempArticle = communal;
                tempAccount = card_VISA;
            }
            double amount = Math.round(Math.random() * (-1000));
            Date tempDate = new Date((long) (new Date().getTime() -  (long) 86400_000 * 30 * Math.random()));
            transactions.add(new Transaction(tempAccount, tempArticle, amount, tempDate));
        }

        ArrayList<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer(card_VISA, wallet, 25_000, 25_000));
        transfers.add(new Transfer(card_VISA, deposit_RUB, 3_000, 3_000));
        transfers.add(new Transfer(card_VISA, deposit_USD, 6_000, 90));

        for (Account account : accounts) {
            account.setAmountFromTransactionsAndTransfer(transactions, transfers);
        }

        SaveData saveData = SaveData.getInstance();
        saveData.setArticles(articles);
        saveData.setAccounts(accounts);
        saveData.setCurrencies(currencies);
        saveData.setTransactions(transactions);
        saveData.setTransfers(transfers);
        saveData.save();
//        saveData.load();
        System.out.println(saveData);


    }
}
