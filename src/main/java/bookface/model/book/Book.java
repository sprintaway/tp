package bookface.model.book;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
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

    /**
     * Every field must be present and not null.
     */
    public Book(Title title, Author author, int quantity) {
        CollectionUtil.requireAllNonNull(title, author, quantity);
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Set<Person> getLoanees() {
        return loanees;
    }

    public int getQuantity() {
        return quantity;
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
    public void loanTo(Person loanee) {
        this.loanees.add(loanee);
        this.quantity--;
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
                .append(getAuthor());

        return builder.toString();
    }
}
