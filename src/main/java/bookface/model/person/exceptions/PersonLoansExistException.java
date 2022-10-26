package bookface.model.person.exceptions;

/**
 * Signals that the operation cannot be done due to existing loans
 * from a {@code Person}
 */
public class PersonLoansExistException extends RuntimeException {
    public PersonLoansExistException() {
        super("Person cannot be deleted; loaned books are not returned");
    }
}
