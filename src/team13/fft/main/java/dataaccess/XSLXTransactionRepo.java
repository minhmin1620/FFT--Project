//Contributing Authors: K DeMerchant, Minh Nguyen, Yousef Khai
package dataaccess;

import model.pojos.BankTransaction;
import model.pojos.BankStatement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
//Contributing Authors: Minh nguyen, Yousef Khai
public class XSLXTransactionRepo implements TransactionRepo<BankStatement> {

    public XSLXTransactionRepo() {}

    @Override
    public BankStatement load(String filePath) {
        ArrayList<BankTransaction> transactions = new ArrayList<>();

        try (FileInputStream fileIn = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fileIn)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            //looping through the rows
            while (rowIterator.hasNext()) {
                Row currRow = rowIterator.next();
                Iterator<Cell> cellIterator = currRow.cellIterator();

                String date = "";
                String description = "";
                double credit = 0.0;
                double debit = 0.0;
                double balance = 0.0;

                while (cellIterator.hasNext()) {
                    Cell currCell = cellIterator.next();

                    switch (currCell.getColumnIndex()) {
                        case 0:
                            date = getCellValue(currCell);
                            break;
                        case 1:
                            description = getCellValue(currCell);
                            break;
                        case 2:
                            credit = parseDoubleSafely(getCellValue(currCell));             
                            break;
                        case 3:
                            debit = parseDoubleSafely(getCellValue(currCell));
                            break;
                        case 4:
                            balance = parseDoubleSafely(getCellValue(currCell));
                            break;
                        default:                    
                            break;
                    }
                }

                if (!date.isEmpty() && !description.isEmpty()) {
                    BankTransaction transaction = new BankTransaction(date, description, credit, debit, balance,"","");
                    checkRepeated(transaction);
                    transactions.add(transaction);
                } else {
                    System.err.println("Skipping row due to missing data: " + currRow.getRowNum());
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return new BankStatement(transactions);
    }

    private double parseDoubleSafely(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + value);
            return 0.0; 
        }
    }
    
    //Contributing Author: Yousef Khai
    private void checkRepeated(BankTransaction transIn) {
        for (BankTransaction i : TransactionAccess.getStoredTransactions()) {
            if (i.getDescription() != null && i.getDescription().equals(transIn.getDescription())) {
                if ((i.getCredit() == transIn.getCredit()) || (i.getDebit() == transIn.getDebit())) {
                    transIn.setBuyer(i.getBuyer());
                    break; 
                }
            }
        }
    }


    private static String getCellValue(Cell cell) {
		String data = "";
		if (cell.getCellType() == CellType.STRING) {
		       data = cell.getStringCellValue();
		} 
		else if (cell.getCellType() == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				data = cell.getLocalDateTimeCellValue().toLocalDate().toString(); 
             } 
			else {
                 data = Double.toString(cell.getNumericCellValue());
             }
		}
		return data;
	}
}
