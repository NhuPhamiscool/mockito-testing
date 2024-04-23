// package soft3202_A1;
package au.edu.sydney.brawndo.erp.todo;

import au.edu.sydney.brawndo.erp.todo.TaskImpl;
// import soft3202_A1.spfea.SPFEAFacade;
import au.edu.sydney.brawndo.erp.todo.ToDoList;
import au.edu.sydney.brawndo.erp.todo.ToDoFactory;
import au.edu.sydney.brawndo.erp.todo.ToDoListImpl;
import au.edu.sydney.brawndo.erp.todo.Task;
// import org.junit.Test;

// import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ToDoFactoryTest {

    @Test 
    public void makeToDoListTest() {
        ToDoFactory td = new ToDoFactory();

        assertNotNull(td.makeToDoList());
    }

}