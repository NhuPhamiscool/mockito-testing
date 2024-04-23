// package soft3202_A1;
package au.edu.sydney.brawndo.erp.todo;

import au.edu.sydney.brawndo.erp.todo.TaskImpl;
// import soft3202_A1.spfea.SPFEAFacade;
import au.edu.sydney.brawndo.erp.todo.ToDoList;
import au.edu.sydney.brawndo.erp.todo.ToDoListImpl;
import au.edu.sydney.brawndo.erp.todo.Task;
// import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
// import static org.junit.Assert.*;
import java.time.LocalDateTime;
// import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TaskImplTest {

    @Test 
    public void getIDTest() {
        Task test = new TaskImpl(12, LocalDateTime.now(), "HOME OFFICE", "clen");
        assertEquals(12, test.getID());
        
    }

    @Test 
    public void getDateTimeTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");
        assertEquals(lc, test.getDateTime());
        
    }

    @Test 
    public void getLocationTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");
        assertEquals("HOME OFFICE", test.getLocation());
        
    }

    @Test 
    public void getDescriptionTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");
        assertEquals("clen", test.getDescription());

        test.setDescription(null);

        assertNull(test.getDescription());
    }

    @Test 
    public void isCompletedTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");
        assertEquals(false, test.isCompleted());

        test.complete();
        assertEquals(true, test.isCompleted());
        
    }

    @Test 
    public void setDateTimeTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");

        assertEquals(lc, test.getDateTime());

        test.setDateTime(lc.minusDays(1));

        assertEquals(lc.minusDays(1), test.getDateTime()); 
    }

    @Test 
    public void setDateTimeNullTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");

        assertEquals(lc, test.getDateTime());

        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> { test.setDateTime(null);
        }); 

        assertNotNull(e);
    }

    @Test 
    public void setLocationTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");

        assertEquals("HOME OFFICE", test.getLocation());

        test.setLocation("MOBILE");

        assertEquals("MOBILE", test.getLocation()); 
    }

    @Test 
    public void setLocationNullTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");

        assertEquals("HOME OFFICE", test.getLocation());

        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> { test.setLocation(null);
        }); 

        assertNotNull(e);

        Exception e2 = assertThrows(
            IllegalArgumentException.class,
            () -> { test.setLocation("");
        }); 

        assertNotNull(e2);
    }

    @Test 
    public void setLocationMoreThan256Test() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");

        assertEquals("HOME OFFICE", test.getLocation());

        Exception e2 = assertThrows(
            IllegalArgumentException.class,
            () -> { test.setLocation("wnmufdddthpxtkhugtslvlttucwxaahafpiqvntwvaxumbiscxgfiuygwlyrnpwxrqlxkpggwtkcxhuvbwticanuubegdqqavefpwsnhepiekuhadfcdvgjoeijltxrlifcayjiprsgfihsmbbqezqjdwirumolkvsxxrcvkcrtjwldtnbuimmwgcrjyeflysxqrhfbkakbmminkphgxldvjznjmracmcalxxzbelzfuojtrrsfmqmoeekjrlmwdvzbwdlmzibkprmtfus");
        }); 

        assertNotNull(e2);
    }

    @Test 
    public void setDescriptionTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");

        assertEquals("clen", test.getDescription());

        test.setDescription("laundry");

        assertEquals("laundry", test.getDescription()); 
    }

    @Test 
    public void setDescriptionNullTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");

        assertEquals("clen", test.getDescription());

        test.setDescription(null);

        assertNull(test.getDescription()); 
    }

    @Test 
    public void completeTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");
        assertFalse(test.isCompleted()); 
        
        test.complete();

        assertTrue(test.isCompleted()); 
    }

    @Test 
    public void alreadyCompleteExceptionTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");

        test.complete();

        Exception e = assertThrows(
            IllegalStateException.class,
            () -> { test.complete();
        });
        assertNotNull(e);
    }

    @Test 
    public void getFieldTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");
        
        assertEquals("HOME OFFICE", test.getField(Task.Field.LOCATION)); 
        assertEquals("clen", test.getField(Task.Field.DESCRIPTION));
    }

    @Test 
    public void getFieldNullTest() {
        LocalDateTime lc = LocalDateTime.now();
        Task test = new TaskImpl(12, lc, "HOME OFFICE", "clen");

        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> { test.getField(null);
        });
        assertNotNull(e);
    }
}
