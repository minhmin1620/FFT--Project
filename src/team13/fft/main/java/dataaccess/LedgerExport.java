package dataaccess;

import model.pojos.BankTransaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
//Contributing Authors: Kyle DeMerchant
public class LedgerExport {
	
	public LedgerExport() {}
	
	public void exportToExcel(ArrayList<BankTransaction> arrayList) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Ledger");
			
			Row ledgerTitleRow = sheet.createRow(0);
            Cell titleCell = ledgerTitleRow.createCell(0);
            titleCell.setCellValue("Ledger");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCell.setCellStyle(titleStyle);
            
			
			Row headerRow = sheet.createRow(1);
			headerRow.createCell(0).setCellValue("User");
            headerRow.createCell(1).setCellValue("Month");
            headerRow.createCell(2).setCellValue("Has Paid (Debit)");
            headerRow.createCell(3).setCellValue("Purchased (Credit)");
            headerRow.createCell(4).setCellValue("Current Balance Owed");
            
            ArrayList<String> users = getUniqueUsers(arrayList);
            
            int rowIndex = 2;  
            for (String user : users) {
                
                ArrayList<BankTransaction> userTransactions = getTransactionsForUser(arrayList, user);
                
                double previousBalance = 0.0;
                
                CellStyle userCellStyle = workbook.createCellStyle();
                userCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                
                Row firstRow = sheet.createRow(rowIndex);
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 12, 0, 0));

                Cell userCell = firstRow.createCell(0);
                userCell.setCellValue(user); 
                userCell.setCellStyle(userCellStyle);
                
                rowIndex++;
                
                for (String month : getMonthsOfYear()) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(1).setCellValue(month); 

                    double debit = 0.0;
                    double credit = 0.0;

                    for (BankTransaction transaction : userTransactions) {
                        int transactionMonth = transaction.getDate().getMonth().getValue() - 1;
                        if (transactionMonth == getMonthsOfYear().indexOf(month)) {
                            debit += transaction.getDebit();
                            credit += transaction.getCredit();
                        }
                    }

                   double balanceOwed = previousBalance + credit - debit;
                    
                    row.createCell(2).setCellValue("$" + String.format("%.2f", debit));  
                    row.createCell(3).setCellValue("$" + String.format("%.2f", credit));
                    
                    if (previousBalance != balanceOwed) {
                    	row.createCell(4).setCellValue("$" + String.format("%.2f", balanceOwed)); 
                    }
                    
                    previousBalance = balanceOwed;
                }
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            
            
            
            for (String month : getMonthsOfYear()) {
                Sheet monthSheet = workbook.createSheet(month);

                Row monthTitleRow = monthSheet.createRow(0);
                Cell titleCellMonth = monthTitleRow.createCell(0);
                titleCellMonth.setCellValue(month + " Transactions");
                monthSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
                titleCellMonth.setCellStyle(titleStyle);

                Row monthHeaderRow = monthSheet.createRow(1);
                monthHeaderRow.createCell(0).setCellValue("Date");
                monthHeaderRow.createCell(1).setCellValue("User");
                monthHeaderRow.createCell(2).setCellValue("Description");
                monthHeaderRow.createCell(3).setCellValue("Amount");
                monthHeaderRow.createCell(4).setCellValue("Category");
                monthHeaderRow.createCell(5).setCellValue("Type (Debit/Credit)");

                int monthRowIndex = 2;

                for (BankTransaction transaction : arrayList) {
                    int transactionMonth = transaction.getDate().getMonth().getValue() - 1;

                    if (transactionMonth == getMonthsOfYear().indexOf(month)) {
                        String user = transaction.getBuyer();

                        Row row = monthSheet.createRow(monthRowIndex++);

                        row.createCell(0).setCellValue(transaction.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                        row.createCell(1).setCellValue(user); 
                        row.createCell(2).setCellValue(transaction.getDescription());
                        row.createCell(3).setCellValue("$" + String.format("%.2f", (transaction.getDebit() + transaction.getCredit())));
                        row.createCell(4).setCellValue(transaction.getCategory());
                        row.createCell(5).setCellValue(transaction.getCredit() > 0 ? "Credit" : "Debit");
                    }
                }
                monthSheet.autoSizeColumn(0);
                monthSheet.autoSizeColumn(1);
                monthSheet.autoSizeColumn(2);
                monthSheet.autoSizeColumn(3);
                monthSheet.autoSizeColumn(4);
                monthSheet.autoSizeColumn(5);
            }
            String FILE_PATH = System.getProperty("user.home") + "/Documents/Ledger.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getUniqueUsers(ArrayList<BankTransaction> transactions) {
        ArrayList<String> users = new ArrayList<String>();
        for (BankTransaction transaction : transactions) {
            String user = transaction.getBuyer();
            if (!users.contains(user)) {
                users.add(user);
            }
        }
        return users;
    }

  
    private ArrayList<BankTransaction> getTransactionsForUser(ArrayList<BankTransaction> transactions, String user) {
        ArrayList<BankTransaction> userTransactions = new ArrayList<BankTransaction>();
        for (BankTransaction transaction : transactions) {
            if (transaction.getBuyer().equals(user)) {
                userTransactions.add(transaction);
            }
        }
        return userTransactions;
    }

    private ArrayList<String> getMonthsOfYear() {
        return new ArrayList<>(List.of(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", 
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        ));
    }

    

}
	
