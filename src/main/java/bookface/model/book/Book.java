package bookface.model.book;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import bookface.commons.util.CollectionUtil;
import bookface.model.person.Person;


/**
 * Represents a Book in the BookFace application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {
    private final Title title;
    private final Author author;
    private Person loanee = null;

    private Date returnDate = null;

    /**
     * Every field must be present and not null.
     */
    public Book(Title title, Author author) {
        CollectionUtil.requireAllNonNull(title, author);
        this.title = title;
        this.author = author;
    }

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
    }

    public boolean isLoaned() {
        return this.loanee != null;
    }

    public String getLoanStatus() {
        if (isLoaned()) {
            return "Loaned to " + loanee.getName();
        } else {
            return "Available";
        }
    }

    /**
     * Loans this book to a patron.
     *
     * @param loanee the person borrowing this book
     */
    public void loanTo(Person loanee, Date returnDate) {
        this.loanee = loanee;
        this.returnDate = returnDate;
    }


    /**
     * Return this loaned book .
     */
    public void markBookAsReturned() {
        this.loanee = null;
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
        return Objects.hash(title, author);
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
