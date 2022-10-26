package bookface.model.book.exceptions;

public class BookOnLoanException extends RuntimeException {
    public BookOnLoanException() {
        super("Book cannot be deleted; it is currently loaned to someone");
    }
}
