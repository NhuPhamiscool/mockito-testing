
package au.edu.sydney.brawndo.erp.todo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

public class ToDoListImpl implements ToDoList {
    List<Task> addedTask = new ArrayList<>();
    List<Integer> usedInteger = new ArrayList<>();
    int generatedInteger = 0;
    boolean hasNoneNullID = false;
    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * If id is null, an integer id is generated which is guaranteed to be unique for this instance of ToDoList if
     * and only if no set-integer tasks have been added - that is, either manage ids externally or allow the ToDoList
     * to manage them, do not mix the two<br>
     * The task added will be available to matching remove and find method calls until it is removed using
     * ToDoList.remove(matching id) or the list is cleared using ToDoList.clear()<br>
     *
     * @param id The intended id for this task (may be null, may not already be present in the ToDoList)
     * @param dateTime may not be null
     * @param location may not be null, empty, or longer than 256 characters
     * @param description Task description (may be null)
     * @return •	A reference to the created (not completed) Task matching the given parameters
     * @throws IllegalStateException If a null id is passed after a non-null id task has already been added
     * @throws IllegalArgumentException If any other preconditions are violated
     */

    public Task add(Integer id, LocalDateTime dateTime, String location, String description) throws IllegalArgumentException, IllegalStateException {
        if (dateTime == null) throw new IllegalArgumentException();
        if (location == null || location.equals("") || location.length() > 256)
         throw new IllegalArgumentException();
        // check if first non-null value has the same id as other null values before it
        if (id != null && generatedInteger != 0 && id <= generatedInteger - 1) throw new IllegalArgumentException();
        // if non-null value has the same id as other non-null values before it
        if (id != null && usedInteger.contains(id)) throw new IllegalArgumentException();


        if (id != null) {
            hasNoneNullID = true;
            usedInteger.add(id);


        } else {
            if (hasNoneNullID == true) {
                throw new IllegalStateException();

            } else {
                id = generatedInteger;
                generatedInteger += 1;
            }

        }
        Task newTask = new TaskImpl(id, dateTime, location, description);
        addedTask.add(newTask);
        return newTask;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * Any task matching the given id will no longer be available to remove and find methods<br>
     *
     * @param id The target task id
     * @return True if a task with a matching id was present in the list – otherwise false
     */
    public boolean remove(int id) {
        for (Task t : addedTask) {
            if (t.getID() == id) {
                addedTask.remove(t);
                return true;
            }
        }
        return false;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param id The target task id
     * @return If a task with a matching id exists in the list, the matching task – otherwise null
     */
    public Task findOne(int id) {
        for (Task t : addedTask) {
            if (t.getID() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return A list of all tasks contained by this ToDoList (may be empty)
     * Changes to this list itself will not be reflected in the ToDoList, but changes to the Tasks it contains will be
     */
    public List<Task> findAll() {
        List<Task> copyTasks = new ArrayList<Task>();
        int index = 0;
        for(Task t: addedTask){
            copyTasks.add(index, t);
            index++;
        }
        // return copyItems;
        return copyTasks;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param completed The target task completion status
     * @return A list of all tasks contained by this ToDoList which match the completion status given by the parameter
     */
    public List<Task> findAll(boolean completed) {
        List<Task> returnedList = new ArrayList<>();
        for (Task t : addedTask) {
            if (t.isCompleted() == completed) {
                returnedList.add(t);
            }
        }
        return returnedList;
    }

    /**
     * <b>Preconditions:</b><br>
     * The to parameter must be later in time than the from parameter<br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param from The starting boundary (may be null)
     * @param to The finishing boundary (may be null)
     * @param completed The target completion status (may be null)
     * @return If completed is null, a list of all tasks contained by this ToDoList whose datetime occurs after from
     * and before to (both exclusive). If either is null their boundary is removed (e.g. if to is null then the method
     * will return a list of all tasks whose datetime occurs after from). If both are removed this yields the same
     * list as ToDoList.findAll(). If completed is not null, the yielded list additionally filtered to those tasks
     * matching the given completion status.
     * @throws IllegalArgumentException If any of the preconditions are violated
     */
    public List<Task> findAll(LocalDateTime from, LocalDateTime to, Boolean completed) throws IllegalArgumentException {
        List<Task> returnedList = new ArrayList<>();
        if (to != null && from != null && to.isBefore(from)) throw new IllegalArgumentException();

        if (from == null && to == null && completed == null) {
            return addedTask;

        } 

        if (from != null && to != null) {
            for (Task t : addedTask) {
                if ( (t.getDateTime().isAfter(from) || t.getDateTime().equals(from)) 
                && (t.getDateTime().isBefore(to) || t.getDateTime().equals(to))) {
                    returnedList.add(t);
                }
            }
          
        } else if (from == null && to != null) {
            for (Task t : addedTask) {
                if (t.getDateTime().isBefore(to) || t.getDateTime().equals(to)) {
                    returnedList.add(t);

                } 
            }

        } else if (from != null && to == null) {
            for (Task t : addedTask) {
                if (t.getDateTime().isAfter(from) || t.getDateTime().equals(from)) {
                    returnedList.add(t);

                } 
            }

        } else if (from == null && to == null) {
            for (Task t : addedTask) {
                returnedList.add(t);
            }
        }

        if (completed != null) {
            List<Task> temp = new ArrayList<>();
            int index = 0;

            for(Task t: returnedList){
                temp.add(index, t);
                index++;
            }

            for (Task t2 : temp) {
                if (t2.isCompleted() != completed) {
                    returnedList.remove(t2);
                }
            }
        }

        return returnedList;
    }

    /**
     * <b>Preconditions:</b><br>
     * The to parameter must be later in time than the from parameter<br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param params A map of Task.Fields and values to filter on within those Fields - may be null, but may not
     *               contain null values
     * @param from The starting boundary (may be null)
     * @param to The finishing boundary (may be null)
     * @param completed The target completion status (may be null)
     * @param andSearch Sets the filter status to match all if true, match any if false
     * @return A list of all tasks which match the given filter status based on the following rules:<br>
     *     <ul>
     *         <li>from and to filter on dateTime as exclusive boundaries - if either is null their side of the boundary
     *         is opened, if both are null does not filter on dateTime</li>
     *         <li>completed filters on matching completion status if not null</li>
     *         <li>params filters to the matching Task.Field and mapped value (contained string, not equality)</li>
     *         <li>If andSearch is true this requires ALL above filters to match for all returned Tasks. If andSearch
     *         is false any task matching any given filter will be included</li>
     *     </ul>
     * @throws IllegalArgumentException If any of the preconditions are violated
     */
    public List<Task> findAll(Map<Task.Field, String> params, LocalDateTime from, LocalDateTime to, Boolean completed, boolean andSearch) throws IllegalArgumentException {
        
        List<Task> returnedTask = new ArrayList<>();
        Map.Entry<Task.Field, String> entry;
        Task.Field key;
        String value;

        
        if (to != null && from != null && to.isBefore(from)) throw new IllegalArgumentException();

        if (from != null && to != null) {
            for (Task t : addedTask) {
                if ( (t.getDateTime().isAfter(from) || t.getDateTime().equals(from)) 
                && (t.getDateTime().isBefore(to) || t.getDateTime().equals(to))) {
                    returnedTask.add(t);
                }
            }
          
        } else if (from == null && to != null) {
            for (Task t : addedTask) {
                
                if (t.getDateTime().isBefore(to) || t.getDateTime().equals(to)) {
                    returnedTask.add(t);

                } 
            }

        } else if (from != null && to == null) {
            for (Task t : addedTask) {
                if (t.getDateTime().isAfter(from) || t.getDateTime().equals(from)) {
                    returnedTask.add(t);

                } 
            }

        } else if (from == null && to == null) {
            for (Task t : addedTask) {
                returnedTask.add(t);
            }
        }

        if (completed != null) {
            for (Task t : addedTask) {
                if (andSearch == true && t.isCompleted() != completed && returnedTask.contains(t)) {
                    returnedTask.remove(t);
                
                // } else if (from == null && to == null && andSearch == true && t.isCompleted() == completed 
                // && !returnedTask.contains(t)) {
                //     returnedTask.add(t);
                
                } else if (andSearch == false && t.isCompleted() == completed && !returnedTask.contains(t)) {
                    returnedTask.add(t);
                }
            }
        }

        if (params != null) {
            entry = params.entrySet().iterator().next();
            key = entry.getKey();

            if (entry.getValue() == null) throw new IllegalArgumentException();
            value = entry.getValue();
            
            for (Task t : addedTask) {
                if (andSearch == true && !t.getField(key).equals(value) && returnedTask.contains(t)) {
                    returnedTask.remove(t);

                // } else if (from == null && to == null && completed == null && andSearch == true 
                // && t.getField(key).equals(value) && !returnedTask.contains(t)) {
                //     returnedTask.add(t);

                } else if (andSearch == false && t.getField(key).equals(value) && !returnedTask.contains(t)) {
                    returnedTask.add(t);
                }
            }

        }


        return returnedTask;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * All tasks in the ToDoList will be removed and no longer available to matching remove and find methods<br>
     *
     */
    public void clear() {
        addedTask.clear();
    }
}
