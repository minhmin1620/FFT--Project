package dataaccess;

import model.pojos.BankTransaction;
import model.pojos.BankStatement;
import java.util.ArrayList;

//Contributing authors: Minh Nguyen
public class DummyRepo implements TransactionRepo<BankStatement> {

    public DummyRepo() {}

    @Override
    public BankStatement load(String buffer) {
        ArrayList<BankTransaction> list = new ArrayList<>();

        list.add(new BankTransaction("2024-01-01", "Starbucks", 10.5, 0.0, 100.0, "MN", "Food & Beverage"));
        list.add(new BankTransaction("2024-01-01", "Walmart", 50.0, 0.0, 50.0, "MN", "Retail"));
        list.add(new BankTransaction("2024-01-01", "McDonald's", 20.0, 0.0, 30.0, "KD", "Food & Beverage"));
        list.add(new BankTransaction("2024-01-01", "Amazon", 0.0, 100.0, 70.0, "MN", "E-Commerce"));

        list.add(new BankTransaction("2024-01-05", "Netflix", 15.99, 0.0, 300.0, "MK", "Entertainment"));
        list.add(new BankTransaction("2024-02-20", "Spotify", 10.0, 0.0, 290.0, "MK", "Entertainment"));
        list.add(new BankTransaction("2024-03-12", "Amazon", 25.0, 0.0, 265.0, "MK", "E-Commerce"));
        list.add(new BankTransaction("2024-03-25", "Tim Hortons", 5.0, 0.0, 260.0, "MK", "Food & Beverage"));

        list.add(new BankTransaction("2024-01-01", "Walmart", 100.0, 0.0, 400.0, "KD", "Retail"));
        list.add(new BankTransaction("2024-04-12", "Best Buy", 0.0, 200.0, 200.0, "KD", "Electronics"));
        list.add(new BankTransaction("2024-03-05", "Apple Store", 50.0, 0.0, 150.0, "KD", "Electronics"));
        list.add(new BankTransaction("2024-04-12", "Amazon", 75.0, 0.0, 75.0, "KD", "E-Commerce"));

        list.add(new BankTransaction("2024-04-15", "Gas Station", 40.0, 0.0, 35.0, "MN", "Transportation"));
        list.add(new BankTransaction("2024-04-20", "Grocery Store", 25.0, 0.0, 10.0, "MN", "Groceries"));

        return new BankStatement(list);
    }
}