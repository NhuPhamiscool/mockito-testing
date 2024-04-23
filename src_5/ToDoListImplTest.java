// package soft3202_A1;
package au.edu.sydney.brawndo.erp.todo;

// import au.edu.sydney.brawndo.erp.todo.TaskImpl;
import au.edu.sydney.brawndo.erp.todo.TaskImpl;
// import soft3202_A1.spfea.SPFEAFacade;
import au.edu.sydney.brawndo.erp.todo.ToDoList;
import au.edu.sydney.brawndo.erp.todo.ToDoListImpl;
import au.edu.sydney.brawndo.erp.todo.Task;

// import org.junit.Test;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
// import static org.junit.Assert.*;
import java.time.LocalDateTime;
// import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ToDoListImplTest {

    @Test 
    public void addDateTimeNullTest() {
        ToDoListImpl todo = new ToDoListImpl();
        
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> { todo.add(12, null, "HOME OFFICE", "clean");
        }); 
        assertNotNull(e);
    }

    @Test 
    public void addInvalidLocationTest() {
        ToDoListImpl todo = new ToDoListImpl();
        LocalDateTime lc = LocalDateTime.now();

        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> { todo.add(12, lc, null, "clean");
        }); 
        assertNotNull(e);

        Exception e2 = assertThrows(
            IllegalArgumentException.class,
            () -> { todo.add(12, lc, "", "clean");
        }); 
        assertNotNull(e2);

        Exception e3 = assertThrows(
            IllegalArgumentException.class,
            () -> { todo.add(12, lc,
            "wnmufdddthpxtkhugtslvlttucwxaahafpiqvntwvaxumbiscxgfiuygwlyrnpwxrqlxkpggwtkcx" +
            "huvbwticanuubegdqqavefpwsnhepiekuhadfcdvgjoeijltxrlifcayjiprsgfihsmbbqezqjdwi" +
            "rumolkvsxxrcvkcrtjwldtnbuimmwgcrjyeflysxqrhfbkakbmminkphgxldvjznjmracmcalxxzbelzfu" +
            "ojtrrsfmqmoeekjrlmwdvzbwdlmzibkprmtfus", "clean");
        }); 
        assertNotNull(e3);
    }

    @Test 
    public void addNullDescriptionTest() {
        ToDoListImpl todo = new ToDoListImpl();
        LocalDateTime lc = LocalDateTime.now();
         
        // added task successfully with null description
        assertEquals(12, todo.add(12, lc, "HOME OFFICE", null).getID());
    }

    @Test 
    public void addNullIDTest() {
        ToDoListImpl todo = new ToDoListImpl();
        LocalDateTime lc = LocalDateTime.now();
         
        assertEquals(0, todo.add(null, lc, "HOME OFFICE", null).getID());
        assertEquals(1, todo.add(null, lc, "HOME OFFICE", null).getID());

        assertEquals(13, todo.add(13, lc, "HOME OFFICE", null).getID());

        // raise exception because the id is present in the todo list
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> { todo.add(1, lc, "HOME OFFICE", null);
        }); 

        assertNotNull(e);

        // raise exception because the id is present in the todo list
        Exception e2 = assertThrows(
            IllegalArgumentException.class,
            () -> { todo.add(13, lc, "HOME OFFICE", null);
        }); 

        assertNotNull(e2);

        // raise exception because null id cannot be passed after non null values
        // has been passed
        Exception e3 = assertThrows(
            IllegalStateException.class,
            () -> { todo.add(null, lc, "HOME OFFICE", null);
        }); 

        assertNotNull(e3);

        // start to do list with non null values
        ToDoListImpl todo2 = new ToDoListImpl();

        assertEquals(63, todo2.add(63, lc, "HOME OFFICE", null).getID());

        // raise exception because null id cannot be passed after non null values
        // has been passed
        Exception e4 = assertThrows(
            IllegalStateException.class,
            () -> { todo2.add(null, lc, "HOME OFFICE", null);
        }); 
        assertNotNull(e4);

        // raise exception because the id is present in the todo list
        Exception e5 = assertThrows(
            IllegalArgumentException.class,
            () -> { todo2.add(63, lc, "HOME OFFICE", null);
        }); 
        assertNotNull(e5);
    }

    @Test 
    public void removeTest() {
        ToDoListImpl todo = new ToDoListImpl();

        todo.add(12, LocalDateTime.now(), "HOME OFFICE", "clean");
        
        assertFalse(todo.remove(13));
        assertTrue(todo.remove(12));
        assertFalse(todo.remove(12));
    }

    @Test 
    public void findOneTest() {
        ToDoListImpl todo = new ToDoListImpl();

        todo.add(12, LocalDateTime.now(), "HOME OFFICE", "clean");
        
        assertNull(todo.findOne(13));
        assertEquals(12, todo.findOne(12).getID());
        todo.remove(12);
        assertNull(todo.findOne(12));
    }

    @Test 
    public void findAllTest() {
        ToDoListImpl todo = new ToDoListImpl();

        List<Task> task = new ArrayList<>();

        todo.add(12, LocalDateTime.now(), "HOME OFFICE", "clean");
        todo.add(19, LocalDateTime.now(), "HOME OFFICE", "clean");

        // changes to the list obtained from findAll()
        // will not reflected to the original list in
        // ToDolist
        assertEquals(2, todo.findAll().size());

        List<Task> t = todo.findAll();
        assertEquals(2, t.size());
        t.remove(1);
        t.remove(0);
        assertEquals(0, t.size());
        assertEquals(2, todo.findAll().size());

        // changes to task will affect the task saved in todo list
        // we all know task id 12 has location home office but we change it to
        // mobile and then check if its actually reflected in the todo list
        Task taskOne = todo.findOne(12);
        taskOne.setLocation("MOBILE");
        assertEquals("MOBILE", todo.findAll().get(0).getLocation());
    }

    @Test 
    public void findAllBooleanTest() {
        ToDoListImpl todo = new ToDoListImpl();

        todo.add(12, LocalDateTime.now(), "HOME OFFICE", "clean");
        todo.add(2, LocalDateTime.now(), "HOME OFFICE", "clean");
        todo.add(10, LocalDateTime.now(), "HOME OFFICE", "clean");
        
        assertEquals(3, todo.findAll(false).size());
        assertEquals(0, todo.findAll(true).size());
        todo.findOne(2).complete();
        assertEquals(2, todo.findAll(false).size());
        assertEquals(1, todo.findAll(true).size());
        
    }

    @Test 
    public void findAllWithFromToCompletedTest() {
        ToDoListImpl todo = new ToDoListImpl();
        
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = from.minusDays(2);

        Exception e4 = assertThrows(
            IllegalArgumentException.class,
            () -> { todo.findAll(from, to, false);
        }); 
        assertNotNull(e4);

        
        LocalDateTime to2 = from.plusDays(2);
        LocalDateTime to3 = to2.plusDays(3);
        LocalDateTime to4 = to3.plusDays(5);

        
        Task t1 = todo.add(12, from, "HOME OFFICE", "clean");
        Task t2 = todo.add(2, to2, "HOME OFFICE", "clean");
        Task t3 = todo.add(10, to3, "HOME OFFICE", "clean");
        
        assertEquals(3, todo.findAll(from.minusDays(1), to3.plusDays(2), false).size());
        assertEquals(3, todo.findAll(from.minusDays(1), null, false).size());
        assertEquals(3, todo.findAll(null, to3.plusDays(2), false).size());

        assertEquals(3, todo.findAll(from, to4, false).size());

        assertEquals(2, todo.findAll(from, to2, false).size());

        // completed == true
        assertEquals(0, todo.findAll(from, to2, true).size());

        // completed == null
        assertEquals(2, todo.findAll(from, to2, null).size());

        // completed == null and to is null
        assertEquals(3, todo.findAll(from, null, null).size());

        // completed == null and from is null
        assertEquals(2, todo.findAll(null, to2, null).size());

        // completed == null and from is null and to is null
        assertEquals(3, todo.findAll(null, null, null).size());

        // completed == false and from is null and to is null
        assertEquals(3, todo.findAll(null, null, false).size());

        t1.complete();
        assertEquals(1, todo.findAll(null, null, true).size());
        assertEquals(2, todo.findAll(null, null, false).size());
        
    }

    @Test 
    public void findAllWithAndSearchTest() {
        ToDoListImpl todo = new ToDoListImpl();
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to2 = from.plusDays(2);
        LocalDateTime to3 = to2.plusDays(3);
        LocalDateTime to4 = to3.plusDays(5);

        Map<Task.Field, String> params = new HashMap<>();
        params.put(Task.Field.LOCATION, "HOME OFFICE");

        // to is before from
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> { todo.findAll(params, from, from.minusDays(3), false, true);
        }); 
        assertNotNull(e);

        // value of map cannot be null
        params.clear();
        params.put(Task.Field.LOCATION, null);

        Exception e4 = assertThrows(
            IllegalArgumentException.class,
            () -> { todo.findAll(params, from, from.plusDays(3), false, true);
        }); 
        assertNotNull(e4);

        // check return
        todo.add(12, from, "HOME OFFICE", "clean");
        todo.add(2, to2, "HOME OFFICE", "clean");
        todo.add(10, to3, "HOME OFFICE", "clean");
        
        params.clear();
        params.put(Task.Field.LOCATION, "HOME OFFICE");


        assertEquals(3, todo.findAll(params, from, null, false, true).size());
        assertEquals(3, todo.findAll(params, from, null, false, false).size());
        assertEquals(3, todo.findAll(params, from, null, true, false).size());
        assertEquals(0, todo.findAll(params, from, null, true, true).size());

        assertEquals(3, todo.findAll(params, from.minusDays(1), to3.plusDays(2), false, true).size());
        assertEquals(3, todo.findAll(params, from.minusDays(1), null, false, true).size());
        assertEquals(3, todo.findAll(params, null, to3.plusDays(2), false, true).size());
        // andSearch is true so all tasks must satisfy all requirements
        // all 3 satisfy the params but only the first and second one 
        // satisfy the time stamp.
        assertEquals(2, todo.findAll(params, null, to2, false, true).size());
        assertEquals(3, todo.findAll(params, null, to2, true, false).size());
        assertEquals(0, todo.findAll(params, null, to2, true, true).size());

        // andSearch is false so all tasks only need to satisfy one of requirements
        // even though the third one does not satisfy the time stamp, its still be
        // included because it satisfies the params
        assertEquals(3, todo.findAll(params, null, to2, false, false).size());
        assertEquals(3, todo.findAll(params, null, null, false, false).size());
        assertEquals(3, todo.findAll(params, null, null, true, false).size());

        // no tasks has been finished so no tasks returned
        assertEquals(0, todo.findAll(params, null, null, true, true).size());

        todo.findOne(12).complete();

        assertEquals(1, todo.findAll(params, null, null, true, true).size());

        params.clear();
        params.put(Task.Field.LOCATION, "MOBILE");

        todo.findOne(2).setLocation("MOBILE");
        todo.findOne(10).setLocation("MOBILE");

        assertEquals(0, todo.findAll(params, null, null, true, true).size());
        assertEquals(1, todo.findAll(params, from, to2, false, true).size());
        assertEquals(0, todo.findAll(params, from, to2, true, true).size());
        assertEquals(0, todo.findAll(params, null, from, false, true).size());
        assertEquals(3, todo.findAll(params, null, from, true, false).size());
        assertEquals(3, todo.findAll(params, to2, to3, true, false).size());

        assertEquals(1, todo.findAll(null, null, from, true, false).size());
        assertEquals(1, todo.findAll(null, null, from, null, false).size());
        assertEquals(1, todo.findAll(null, null, from, null, true).size());
        assertEquals(3, todo.findAll(null, null, null, null, true).size());
        assertEquals(2, todo.findAll(null, null, null, false, true).size());
        assertEquals(0, todo.findAll(null, null, from, false, true).size());
    }
    

    @Test 
    public void clearTest() {
        ToDoListImpl todo = new ToDoListImpl();

        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to2 = from.plusDays(2);
        LocalDateTime to3 = to2.plusDays(3);
        LocalDateTime to4 = to3.plusDays(5);

        todo.add(12, from, "HOME OFFICE", "clean");
        todo.add(2, to2, "HOME OFFICE", "clean");
        todo.add(10, to3, "HOME OFFICE", "clean");

        
        assertEquals(3, todo.findAll().size());
        todo.clear();
        assertEquals(0, todo.findAll().size());
    }
}