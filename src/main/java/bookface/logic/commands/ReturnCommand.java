package bookface.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import bookface.commons.core.Messages;
import bookface.commons.core.index.Index;
import bookface.logic.commands.exceptions.CommandException;
import bookface.model.Model;
import bookface.model.book.Book;
import bookface.model.person.Person;

/**
 * Loans to the user in the user list a book from the book list.
 */
public class ReturnCommand extends Command {
    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Return the book identified by the index"
            + " number in book list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_RETURN_SUCCESS = "Book %1$s returned successfully.";

    public static final String NOT_ON_LOAN = "Book is already loaned out.";

    private final Index targetBookIndex;
    private final Index targetUserIndex;

    /**
     * Creates a ReturnCommand to return the loan of the specified {@code Book}
     */
    public ReturnCommand(Index userIndex, Index bookIndex) {
        this.targetUserIndex = userIndex;
        this.targetBookIndex = bookIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Book> lastShownBookList = model.getFilteredBookList();
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        if (targetBookIndex.getZeroBased() >= lastShownBookList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Book bookToReturn = lastShownBookList.get(targetBookIndex.getZeroBased());
        Person loanee = lastShownPersonList.get(targetUserIndex.getZeroBased());

        if (!bookToReturn.isLoaned()) {
            throw new CommandException(NOT_ON_LOAN);
        }

        model.returnLoanedBook(loanee, bookToReturn);
        model.updateFilteredBookList(Model.PREDICATE_SHOW_ALL_BOOKS);
        return new CommandResult(String.format(MESSAGE_RETURN_SUCCESS, bookToReturn.getTitle()));
    }
}

