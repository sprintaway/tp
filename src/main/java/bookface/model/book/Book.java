package bookface.model.book;

<<<<<<< HEAD
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
=======
import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
>>>>>>> 4d22afe269d80a37ec9232e2286248e75877a31f
import java.util.Objects;
import java.util.Set;

import bookface.commons.util.CollectionUtil;
import bookface.model.person.Person;


/**
 * Represents a Book in the BookFace application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {
    private final Title title;
    private final Author author;
    private int quantity; // Quantity can't be final since increments/decrements are possible
    private final HashSet<Person> loanees = new HashSet<>();

    private Date returnDate = null;

    /**
     * Every field must be present and not null. This is an overloaded constructor used in JsonAdaptedBook
     * if book does not have return date.
     */
    public Book(Title title, Author author, int quantity) {
        CollectionUtil.requireAllNonNull(title, author, quantity);
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    /**
     * Every field must be present and not null. This is an overloaded constructor used in JsonAdaptedBook
     * if book has a return date.
     */
    public Book(Title title, Author author, Date returnDate) {
        CollectionUtil.requireAllNonNull(title, author, returnDate);
        this.title = title;
        this.author = author;
        this.returnDate = returnDate;
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

<<<<<<< HEAD
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    public String getReturnDateString() {
        if (isLoaned()) {
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            return "Return by: " + formatter.format(returnDate);
        }
        return null;
    }


    public Person getLoanee() {
        return loanee;
=======
    public Set<Person> getLoanees() {
        return loanees;
    }

    public int getQuantity() {
        return quantity;
>>>>>>> 4d22afe269d80a37ec9232e2286248e75877a31f
    }

    public boolean isLoaned() {
        return !this.loanees.isEmpty();
    }

    public String getLoanStatus() {
        String outputStatus = "Available"; // Book is available by default
        if (isLoaned()) {
            List<String> listOfLoanees = null;
            for (Person person : this.loanees) {
                listOfLoanees.add(person.getName().toString());
            }
            outputStatus = "Loaned to " + String.join(", ", listOfLoanees);
        }
        return outputStatus;
    }

    /**
     * Loans this book to a patron.
     * Method will decrement the quantity of the {@code Book} by 1
     *
     * @param loanee the person borrowing this book
     */
<<<<<<< HEAD
    public void loanTo(Person loanee, Date returnDate) {
        this.loanee = loanee;
        this.returnDate = returnDate;
=======
    public void loanTo(Person loanee) {
        this.loanees.add(loanee);
        this.quantity--;
>>>>>>> 4d22afe269d80a37ec9232e2286248e75877a31f
    }


    /**
     * Return this loaned book from a patron.
     */
    public void removeLoanneFromLoan(Person loanee) {
        this.loanees.remove(loanee);
    }

    /**
     * Returns true if both books have the same title.
     * This defines a weaker notion of equality between two books.
     */
    public boolean isSameBook(Book otherBook) {
        if (otherBook == this) {
            return true;
        }

        return otherBook != null
                && otherBook.getTitle().equals(getTitle());
    }

    /**
     * Returns the loaned book by removing the loanee
     * Method will increment the quantity of the {@code Book} by 1
     */
    public void returnBook(Person person) {
        this.loanees.remove(person);
        this.quantity++;
    }

    /**
     * Returns true if both books have the same details.
     * This defines a stronger notion of equality between two books.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        return otherBook.getTitle().equals(getTitle())
                && otherBook.getAuthor().equals(getAuthor());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, author, quantity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append("; Author: ")
                .append(getAuthor())
                .append("; ")
                .append(getReturnDateString());
        return builder.toString();
    }
}
