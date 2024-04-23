package au.edu.sydney.brawndo.erp.todo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class TaskImpl implements Task {
    LocalDateTime time;
    String description;
    String location;
    boolean complete;
    int id;

    public TaskImpl(int id, LocalDateTime time, String location, String description) {
        this.time = time;
        this.description = description;
        this.location = location;
        this.complete = false;
        this.id = id;

    }
    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The id of this task
     */
    public int getID() {
        return this.id;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The datetime of this task (may not be null)
     */
    public LocalDateTime getDateTime() {
        return this.time;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The location of this task (may not be null, empty, and must be 256 characters or less)
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The description of this task if present, otherwise null
     */
    public String getDescription() {
        if (this.description == null) {
            return null;
        }
        return this.description;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The completion status of this task
     */
    public boolean isCompleted() {
        return this.complete;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * The new datetime should be returned from any future calls to this Task’s getDateTime method<br>
     *
     * @param dateTime May not be null
     * @throws IllegalArgumentException If the preconditions are violated
     */
    public void setDateTime(LocalDateTime dateTime) throws IllegalArgumentException {
        if (dateTime == null) throw new IllegalArgumentException();
        
        this.time = dateTime;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     *  The new location should be returned from any future calls to this Task’s getLocation method<br>
     *
     * @param location May not be null or empty, must be 256 characters or less
     * @throws IllegalArgumentException  If the preconditions are violated
     */
    public void setLocation(String location) throws IllegalArgumentException {
        if (location == null || location.equals("") || location.length() > 256) {
            throw new IllegalArgumentException();
        }
        this.location = location;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * The new description (or null if set) should be returned from any future calls to this Task’s getDescription method<br>
     *
     * @param description May be null
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * The task will be set to completed and will reflect this in isComplete()<br>
     *
     * @throws IllegalStateException  If this task was already complete
     */
    public void complete() throws IllegalStateException {
        if (this.complete == true) throw new IllegalStateException();

        this.complete = true;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     * @param field A mapping to a field stored by this Task (may not be null)
     * @throws IllegalArgumentException If the preconditions are violated
     * @return The string contents of the field mapped by the parameter (with inherited postconditions)
     *
     */
    public String getField(Field field) throws IllegalArgumentException {
        if (field == null) throw new IllegalArgumentException();
        
        if (field == Task.Field.LOCATION) {
            return this.location;
        }
        return this.description;
    }
}