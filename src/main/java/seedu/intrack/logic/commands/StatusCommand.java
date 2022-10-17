package seedu.intrack.logic.commands;

import static seedu.intrack.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.intrack.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

import java.util.List;

import seedu.intrack.commons.core.Messages;
import seedu.intrack.commons.core.index.Index;
import seedu.intrack.logic.commands.exceptions.CommandException;
import seedu.intrack.model.Model;
import seedu.intrack.model.internship.Internship;
import seedu.intrack.model.internship.Remark;
import seedu.intrack.model.internship.Status;

/**
 * Updates the status of an Internship with upper and lowercase "p", "r" and "o" after
 * the s/ prefix.
 */
public class StatusCommand extends Command {

    public static final String COMMAND_WORD = "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the status of the internship identified by "
            + "the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + " STATUS\n"
            + "Example: " + COMMAND_WORD + " 1 o";

    public static final String STATUS_COMMAND_CONSTRAINTS = "STATUS must be either \"o\" to denote Offered, "
            + "\"p\" to denote in Progress, "
            + "or \"r\" to denote Rejected.";

    public static final String MESSAGE_UPDATE_STATUS_SUCCESS = "Updated status of internship: %1$s";

    private final Index index;

    private final Status status;

    /**
     * @param index of the internship in the internship list to update the status of
     * @param status of the internship application
     */
    public StatusCommand(Index index, Status status) {
        requireAllNonNull(index, status);

        this.index = index;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Internship> lastShownList = model.getFilteredInternshipList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        Internship internshipToEdit = lastShownList.get(index.getZeroBased());
        Internship editedInternship = new Internship(internshipToEdit.getName(),
                internshipToEdit.getPosition(), internshipToEdit.getPhone(),
                internshipToEdit.getEmail(), status, internshipToEdit.getAddress(),
                internshipToEdit.getTags(), internshipToEdit.getRemark());

        model.setInternship(internshipToEdit, editedInternship);
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);

        return new CommandResult(String.format(MESSAGE_UPDATE_STATUS_SUCCESS, editedInternship));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusCommand)) {
            return false;
        }

        StatusCommand e = (StatusCommand) other;

        return index.equals(e.index)
                && status.equals(e.status);
    }

}
