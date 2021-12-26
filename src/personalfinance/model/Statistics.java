package personalfinance.model;

import personalfinance.saveload.SaveData;

import java.util.HashMap;
import java.util.List;

public class Statistics {

    public static double getBalanceCurrency(Currency currency){
        SaveData saveData = SaveData.getInstance();
        double amount = 0;
        for (Account account : saveData.getAccounts()){
            if(currency.equals(account.getCurrency())){
                amount += account.getAmount();
            }
        }
        return amount;
    }

    public static double getBalance(Currency currency){
        SaveData saveData = SaveData.getInstance();
        double amount = 0;
        for (Account account : saveData.getAccounts()){
            amount += account.getAmount() * account.getCurrency().getRateByCurrency(currency);
        }
        return amount;
    }

    private static HashMap<String, Double> getDataForChartOnArticles(boolean income){
        List<Transaction> transactions = SaveData.getInstance().getTransactions();
        HashMap<String, Double> data = new HashMap<>();
        for (Transaction transaction : transactions){
            if(!income && transaction.getAmount() > 0 || !income && transaction.getAmount() < 0){
                String key = transaction.getArticle().getTitle();
                double sum = 0;
                double amount = transaction.getAmount();
                if(!income){
                    amount *= -1;
                }
                if(data.containsKey(key)){
                    sum = data.get(key);
                }
                sum += amount * transaction.getAccount().getCurrency().getRateByCurrency(SaveData.getInstance().getBaseCurrency());
                data.put(key, round(sum));
            }
        }
        return  data;
    }

    private static HashMap<String, Double> getDataForChartIncomeArticles(boolean income){
        return  getDataForChartOnArticles(true);
    }
    private static HashMap<String, Double> getDataForChartOnExpArticles(boolean income){
        return getDataForChartOnArticles(false);
    }

    private static Double round(double value) {
        return (double) Math.round(value * 100 / 100);
    }


}
