package bookface.logic.commands.add;

import static bookface.logic.parser.CliSyntax.*;
import static java.util.Objects.requireNonNull;

import bookface.logic.commands.CommandResult;
import bookface.logic.commands.exceptions.CommandException;
import bookface.model.Model;
import bookface.model.book.Book;

/**
 * The command that adds a book to the BookList.
 */
public class AddBookCommand extends AddCommand {
    public static final String COMMAND_WORD = "book";
    public static final String MESSAGE_USAGE = AddCommand.COMMAND_WORD
            + " " + COMMAND_WORD
            + ": Adds a book to the book list."
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AUTHOR + "AUTHOR "
            + PREFIX_QUANTITY + "QUANTITY\n"
            + "Example: " + AddCommand.COMMAND_WORD
            + " " + COMMAND_WORD + " "
            + PREFIX_TITLE + "The Hobbit "
            + PREFIX_AUTHOR + "JRR Tolkien "
            + PREFIX_QUANTITY + "3";
    public static final String MESSAGE_SUCCESS = "New book added: %s by %s\nBook has a quantity of %d";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book is already in our records.";
    private final Book bookToAdd;

    /**
     * Constructs a AddBookCommand for adding of a book.
     * @param book the book to add
     */
    public AddBookCommand(Book book) {
        requireNonNull(book);
        this.bookToAdd = book;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBook(this.bookToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

        model.addBook(this.bookToAdd);
        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                this.bookToAdd.getTitle(),
                this.bookToAdd.getAuthor(),
                this.bookToAdd.getQuantity()));
    }
}
