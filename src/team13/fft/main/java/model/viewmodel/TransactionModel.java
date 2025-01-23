package model.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dataaccess.TransactionAccess;
import dataaccess.TransactionRepo;
import model.pojos.BankStatement;
import model.pojos.BankTransaction;
import model.pojos.Buyer;

// Contributing Authors: Minh nguyen, Yousef Khai
public class TransactionModel {
	private Predicate<BankTransaction> userFilter = txn -> true;
    private Predicate<BankTransaction> monthFilter = txn -> true;
    private final StringProperty bankStatementProperty;
    private final ObjectProperty<ObservableList<BankTransaction>> transactionsProperty;
    private final ObjectProperty<ObservableList<BankTransaction>> storedTransactionsProperty;
    private FilteredList<BankTransaction> filteredTransactions;
    private TransactionRepo<BankStatement> repo;
    private ObjectProperty<Buyer> selectedBuyerProperty;
    private StringProperty selectedCategoryProperty;
    private ObservableList<BankTransaction> selectedTransactions;
    private ArrayList<BankTransaction> transactions;


 // Contributing Authors: Minh nguyen, Yousef Khai
    public TransactionModel(TransactionRepo<BankStatement> repo) {
        this.bankStatementProperty = new SimpleStringProperty();
        this.transactionsProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        this.storedTransactionsProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        this.selectedTransactions = FXCollections.observableArrayList();
        this.selectedBuyerProperty = new SimpleObjectProperty<>();
        this.selectedCategoryProperty = new SimpleStringProperty();
        this.filteredTransactions = new FilteredList<>(storedTransactionsProperty.get(), txn -> true);
        this.repo = repo;
        this.transactions = new ArrayList<>(TransactionAccess.getStoredTransactions());
    }

    public void setBankStatement(String fileName) {
        bankStatementProperty.set(fileName);
        loadBankStatement();
    }

 // Contributing Authors: Minh nguyen
    public StringProperty bankStatementProperty() {
        return bankStatementProperty;
    }

 // Contributing Authors: Minh nguyen
    public TransactionRepo<BankStatement> getRepo() {
        return repo;
    }

 // Contributing Authors: Minh nguyen
    public ObservableList<BankTransaction> getTransactions() {
        return transactionsProperty.get();
    }

 // Contributing Authors: Minh nguyen
    public ObservableList<BankTransaction> getFilteredTransactions() {
        return filteredTransactions;
    }

 // Contributing Authors: Minh nguyen
    public void loadBankStatement() {
        BankStatement statement = repo.load(bankStatementProperty.get());
        transactionsProperty.get().setAll(statement.getList());
    }
 // Contributing Authors: Minh nguyen, Yousef Khai
    public void loadStoredTransactions() {
    	storedTransactionsProperty.get().setAll(TransactionAccess.getStoredTransactions());
    	
    }
    
 // Contributing Authors: Minh nguyen, Yousef Khai
    public void filterByUser(String user) {
        if (user == null || user.isEmpty()) {
            userFilter = txn -> true;
        } else {
            userFilter = txn -> txn.getBuyer().equalsIgnoreCase(user);
        }
        applyFilters();
    }

    public void filterByMonth(String month) {
        if (month == null || month.isEmpty()) {
            monthFilter = txn -> true;
        } else {
            monthFilter = txn -> txn.getDate().getMonth().toString().equalsIgnoreCase(month);
        }
        applyFilters();
    }

 // Contributing Authors: Minh nguyen, Yousef Khai
    private void applyFilters() {
        filteredTransactions.setPredicate(userFilter.and(monthFilter));
        
    }
    
 // Contributing Authors: Minh nguyen, Yousef Khai

    public List<String> getUsers() {
        return storedTransactionsProperty.get().stream()
                .map(BankTransaction::getBuyer)
                .distinct()
                .collect(Collectors.toList());
    }

 // Contributing Authors: Minh nguyen, Yousef Khai
    public List<String> getMonths() {
        return TransactionAccess.getStoredTransactions().stream()
                .map(txn -> txn.getDate().getMonth().toString())
                .distinct()
                .collect(Collectors.toList());
    }

 // Contributing Authors: Minh nguyen, Yousef Khai
    public List<String> getDescription() {
        return storedTransactionsProperty.get().stream()
                .map(BankTransaction::getDescription)
                .distinct()
                .collect(Collectors.toList());
    }

 // Contributing Authors: Minh nguyen
    public ObservableList<LedgerViewModel> getLedgerViewModels() {
        return FXCollections.observableArrayList(
            getTransactions().stream()
                .map(txn -> new LedgerViewModel(
                    txn.getBuyer(),
                    txn.getDate().getMonth().toString(),
                    String.valueOf(txn.getDebit()),
                    String.valueOf(txn.getCredit()),
                    String.valueOf(txn.getBalance())
                ))
                .collect(Collectors.toList())
        );
    }

 // Contributing Authors: Minh nguyen
    public ObservableList<LedgerViewModel> getFilteredLedgerViewModels() {
        return FXCollections.observableArrayList(
            getFilteredTransactions().stream()
                .map(txn -> new LedgerViewModel(
                    txn.getBuyer(),
                    txn.getDate().getMonth().toString(),
                    String.valueOf(txn.getDebit()),
                    String.valueOf(txn.getCredit()),
                    String.valueOf(txn.getBalance())
                ))
                .collect(Collectors.toList())
        );
    }

 // Contributing Authors:  Yousef Khai
    public ObjectProperty<Buyer> selectedBuyerProperty() {
        return selectedBuyerProperty;
    }

 // Contributing Authors:  Yousef Khai
    public StringProperty selectedCategoryProperty() {
        return selectedCategoryProperty;
    }

 // Contributing Authors:  Yousef Khai
    public ObservableList<BankTransaction> getSelectedTransactions() {
        return selectedTransactions;
    }
    
 // Contributing Authors: Minh Nguyen, Yousef Khai
    public List<MonthlyUserSummary> getMonthlySummariesByUser(String userFilter) {
      
        List<BankTransaction> filteredTransactions = transactions.stream()
            .filter(txn -> userFilter == null || txn.getBuyer().equalsIgnoreCase(userFilter))
            .sorted(Comparator.comparing(txn -> txn.getDate())) 
            .collect(Collectors.toList());

        Map<String, MonthlyUserSummary> monthlySummaries = new LinkedHashMap<>();
        BigDecimal runningBalance = BigDecimal.ZERO; 

        for (BankTransaction txn : filteredTransactions) {
            String month = txn.getDate().getMonth().toString(); 
            BigDecimal debit = BigDecimal.valueOf(txn.getDebit());
            BigDecimal credit = BigDecimal.valueOf(txn.getCredit());

            runningBalance = runningBalance.subtract(debit).add(credit);

           
            monthlySummaries.putIfAbsent(month, new MonthlyUserSummary(userFilter, month, 0, 0, runningBalance.doubleValue()));

            MonthlyUserSummary summary = monthlySummaries.get(month);

            BigDecimal updatedTotalDebit = summary.totalDebit.add(debit);
            BigDecimal updatedTotalCredit = summary.totalCredit.add(credit);


            monthlySummaries.put(month, new MonthlyUserSummary(
                userFilter,
                month,
                updatedTotalDebit.doubleValue(),
                updatedTotalCredit.doubleValue(),
                runningBalance.doubleValue()
            ));
        }

        return new ArrayList<>(monthlySummaries.values());
    }


 // Contributing Authors: Minh Nguyen
    public static class MonthlyUserSummary {
        private final String user;
        private final String month;
        private final BigDecimal totalDebit;
        private final BigDecimal totalCredit;
        private final BigDecimal balance;

        public MonthlyUserSummary(String user, String month, double totalDebit, double totalCredit, double balance) {
            this.user = user;
            this.month = month;
            this.totalDebit = BigDecimal.valueOf(totalDebit).setScale(2, RoundingMode.HALF_UP);
            this.totalCredit = BigDecimal.valueOf(totalCredit).setScale(2, RoundingMode.HALF_UP);
            this.balance = BigDecimal.valueOf(balance).setScale(2, RoundingMode.HALF_UP);
        }

        public String getUser() {
            return user;
        }

        public String getMonth() {
            return month;
        }

        public BigDecimal getTotalDebit() {
            return totalDebit;
        }

        public BigDecimal getTotalCredit() {
            return totalCredit;
        }

        public BigDecimal getBalance() {
            return balance;
        }
    }

    
}
