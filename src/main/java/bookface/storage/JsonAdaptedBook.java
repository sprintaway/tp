package bookface.storage;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import bookface.commons.exceptions.IllegalValueException;
import bookface.commons.util.StringUtil;
import bookface.logic.parser.exceptions.ParseException;
import bookface.model.book.Author;
import bookface.model.book.Book;
import bookface.model.book.Title;

/**
 * Jackson-friendly version of {@link Book}.
 */
class JsonAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Book's %s field is missing!";
    public static final String INVALID_BOOK_FORMAT = "Invalid format for a loaned book detected!";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static {
        dateFormat.setLenient(false);
    }

    private final String title;
    private final String author;

    private final String returnDate;

    private final Boolean isLoaned;

    /**
     * Constructs a {@code JsonAdaptedBook} with the given book details.
     */
    @JsonCreator
    public JsonAdaptedBook(@JsonProperty("title") String title, @JsonProperty("author") String author,
                           @JsonProperty("returnDate") String returnDate, @JsonProperty("isLoaned") Boolean isLoaned) {
        this.title = title;
        this.author = author;
        this.returnDate = returnDate;
        this.isLoaned = isLoaned;
    }

    /**
     * Converts a given {@code Book} into this class for Jackson use.
     */
    public JsonAdaptedBook(Book source) {
        title = source.getTitle().bookTitle;
        author = source.getAuthor().bookAuthor;
        isLoaned = source.isLoaned();
        if (isLoaned) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            returnDate = formatter.format(source.getReturnDate()
                    .orElseGet(bookface.commons.util.Date::getFourteenDaysLaterDate));
        } else {
            returnDate = "";
        }
    }

    /**
     * Converts this Jackson-friendly adapted book object into the model's {@code Book} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Book toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName()));
        }
        if (!Author.isValidAuthor(author)) {
            throw new IllegalValueException(Author.MESSAGE_CONSTRAINTS);
        }
        final Author modelAuthor = new Author(author);

        if (isLoaned == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isLoaned"));
        }

        if (returnDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "returnDate"));
        }

        if (!isLoaned && !StringUtil.containsWhitespaceOnly(returnDate)) {
            throw new IllegalValueException("An unloaned Book's returnDate field is not the empty string!");
        }

        if (isLoaned && StringUtil.containsWhitespaceOnly(returnDate)) {
            throw new IllegalValueException("A loaned Book's returnDate field is the empty string!");
        }

        if (isLoaned) {
            try {
                final Date modelDate = dateFormat.parse(returnDate);
                return new Book(modelTitle, modelAuthor, modelDate);
            } catch (java.text.ParseException pe) {
                throw new ParseException(String.valueOf(pe));
            }
        } else {
            return new Book(modelTitle, modelAuthor);
        }
    }
}

