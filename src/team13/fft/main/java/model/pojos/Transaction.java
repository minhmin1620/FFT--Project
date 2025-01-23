//@author Mackenzie Carter 3756279

package model.pojos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Transaction {
    private LocalDate date;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String description;

    public Transaction(String dateIn, String descIn) {
        try {
            this.date = LocalDate.parse(dateIn, DATE_FORMATTER); 
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + dateIn);
            this.date = null; 
        }
        this.description = descIn;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}