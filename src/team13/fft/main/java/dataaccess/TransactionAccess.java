package dataaccess;

import model.pojos.BankTransaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.ObservableList;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
//Contributing Authors: Yousef Khai
public class TransactionAccess {
    private static final String FILE_PATH = System.getProperty("user.home") + "/Documents/BankTransactionList.xlsx";
    private static ArrayList<BankTransaction> transactions;
    //load transactions from the file
    public static synchronized void loadTransactions() {
        transactions = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            createNewFile();
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
            	
                if (row.getRowNum() == 0) continue;
                
                String date = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "";
                String description = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
                double credit = row.getCell(2) != null ? row.getCell(2).getNumericCellValue() : 0.0;
                double debit = row.getCell(3) != null ? row.getCell(3).getNumericCellValue() : 0.0;
                double balance = row.getCell(4) != null ? row.getCell(4).getNumericCellValue() : 0.0;
                String category = row.getCell(5) != null ? row.getCell(5).getStringCellValue() : "";
                String buyerInitials = row.getCell(6) != null ? row.getCell(6).getStringCellValue() : "";

                BankTransaction transaction = new BankTransaction( date, description, credit, debit, balance, buyerInitials,category);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

    }
    public static ArrayList<BankTransaction> getStoredTransactions(){
    	loadTransactions();
    	return transactions;
    	
    }

    public static synchronized void writeTransactions() {
        File file = new File(FILE_PATH);
        try (Workbook workbook = new XSSFWorkbook(); 
             FileOutputStream fos = new FileOutputStream(file)) {

            Sheet sheet = workbook.createSheet("BankTransactions");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Date");
            header.createCell(1).setCellValue("Description");
            header.createCell(2).setCellValue("Credit");
            header.createCell(3).setCellValue("Debit");
            header.createCell(4).setCellValue("Balance");
            header.createCell(5).setCellValue("Category");
            header.createCell(6).setCellValue("Buyer Initials");

            int newRowNum = 1;
            for (BankTransaction transaction : transactions) {
                Row newRow = sheet.createRow(newRowNum++);
                newRow.createCell(0).setCellValue(transaction.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                newRow.createCell(1).setCellValue(transaction.getDescription());
                newRow.createCell(2).setCellValue(transaction.getCredit());
                newRow.createCell(3).setCellValue(transaction.getDebit());
                newRow.createCell(4).setCellValue(transaction.getBalance());
                newRow.createCell(5).setCellValue(transaction.getCategory());
                newRow.createCell(6).setCellValue(transaction.getBuyer());
            }

            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public static synchronized void addTransactions(List<BankTransaction> newTrans) {
	   for(BankTransaction i : newTrans) {
		   transactions.add(i);
	   }
   }

    public static synchronized void clearTransactions() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("BankTransactions");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Date");
            header.createCell(1).setCellValue("Description");
            header.createCell(2).setCellValue("Credit");
            header.createCell(3).setCellValue("Debit");
            header.createCell(4).setCellValue("Balance");
            header.createCell(5).setCellValue("Category");
            header.createCell(6).setCellValue("Buyer Initials");

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    private static synchronized void createNewFile() {
        clearTransactions();
    }
}