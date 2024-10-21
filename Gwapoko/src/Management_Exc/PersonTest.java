package Management_Exc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    List<Person> persons;
    @BeforeEach
    void setUp() {
        persons = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        persons.clear();
    }

    @Test
    void testAgeValid() {
        assertDoesNotThrow(()-> {
            persons.add(new Developer("Jea", 25, 47500));
            persons.add(new Manager("Vince", 28, 32700));
            persons.add(new Employee("Jeshiel", 21, 35000));
            persons.add(new Manager("Mary", 24, 50200));
        });
        assertEquals(persons.get(3).getAge(), 24);
    }

    @Test
    void testAgeInvalid() {
        persons.add(new Manager("Vince", 28, 32700));
        assertThrows(IllegalArgumentException.class, () -> persons.add(new Manager("Jea", -25, 47500)), "Age cannot be negative");
        try {
            persons.add(new Manager("Jea", -25, 47500));
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Age must be non-negative.");
        }
        assertEquals(persons.size(), 1);
    }

    @Test
    void testSalaryInvalid() {
        persons.add(new Manager("Vince", 28, 32700));
        assertThrows(IllegalArgumentException.class, () -> persons.add(new Manager("Jea", -25, 47500)), "Age cannot be negative");
        try {
            persons.add(new Manager("Jea", 25, 27500));
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Salary must be greater than or equal to 30000");
        }
        assertEquals(persons.size(), 1);
    }

    @Test
    void testAssignPMValid() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Claire");
        });
        Developer vince = (Developer) persons.get(1);
        Developer jea = (Developer) persons.get(0);
        assertEquals(vince.getProjectManager(), persons.get(3));
        assertEquals(jea.getProjectManager(), persons.get(4));
    }

    @Test
    void testAssignPMNotManager() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertThrows(ClassCastException.class, ()-> {
            Main.assignPM(persons, "Vince", "Jeshiel");
        });
        try {
            Main.assignPM(persons, "Vince", "Jeshiel");
        } catch (ClassCastException e) {
            assertEquals(e.getMessage(), "Jeshiel is not a manager");
        }
    }

    @Test
    void testAssignPMNotListed() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertThrows(NoSuchElementException.class, ()-> {
            Main.assignPM(persons, "Vince", "Scott");
        });
        try {
            Main.assignPM(persons, "Vince", "Scott");
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "Scott does not exist");
        }
    }

    @Test
    void testAssignPMAlreadyHas() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
        });
        assertThrows(IllegalStateException.class, ()-> {
            Main.assignPM(persons, "Vince", "Claire");
        });
        try {
            Main.assignPM(persons, "Vince", "Claire");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Vince already has a manager: Mary");
        }
    }

    @Test
    void testGiveRaiseValid() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Mary", "Vince", 140.30);
        });
        Employee vince = (Employee) persons.get(1);
        Employee mary = (Employee) persons.get(3);
        assertEquals(vince.getSalary(), 32840.30);
        assertEquals(mary.getSalary(), 50270.15);
    }
    @Test
    void testGiveRaiseInvalid1() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Mary", "Vince", 140.30);
        });
        assertThrows(ClassCastException.class, ()->Main.giveRaise(persons, "Felix", "Jeshiel", 2500));
        try {
            Main.giveRaise(persons, "Felix", "Jeshiel", 2500);
        } catch (ClassCastException e) {
            assertEquals(e.getMessage(), "Felix is not a manager");
        }
        Employee jeshiel = (Employee) persons.get(2);
        assertEquals(jeshiel.getSalary(), 35000);
    }
    @Test
    void testGiveRaiseInvalid2() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Mary", "Vince", 140.30);
        });
        assertThrows(ClassCastException.class, ()->Main.giveRaise(persons, "Felix", "Jeshiel", 2500));
        try {
            Main.giveRaise(persons, "Claire", "Felix", 2500);
        } catch (ClassCastException e) {
            assertEquals(e.getMessage(), "Felix is not an employee");
        }
        Employee claire = (Employee) persons.get(4);
        assertEquals(claire.getSalary(), 52600);
    }
    @Test
    void testGiveRaiseInvalid3() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Claire", "Vince", 140.30);
        });
        assertThrows(IllegalArgumentException.class, ()->Main.giveRaise(persons, "Mary", "Jeshiel", -2500));
        try {
            Main.giveRaise(persons, "Mary", "Jeshiel", -2500);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Raise must be non-negative");
        }
        Employee mary = (Employee) persons.get(3);
        Employee jeshiel = (Employee) persons.get(2);
        assertEquals(mary.getSalary(), 50200);
        assertEquals(jeshiel.getSalary(), 35000);
    }
    @Test
    void testGiveRaiseInvalid4() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Mary", "Vince", 140.30);
        });
        assertThrows(NoSuchElementException.class, ()->Main.giveRaise(persons, "Alaera", "Jeshiel", 2500));
        try {
            Main.giveRaise(persons, "Alaera", "Jeshiel", 2500);
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "Alaera does not exist");
        }
        try {
            Main.giveRaise(persons, "Claire", "Frieren", 2500);
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "Frieren does not exist");
        }
    }

    @Test
    void testCustomerSpeakValid() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Customer("Jewel", 38));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()->{
           Main.customerSpeak(persons, "Felix", "Jeshiel");
           Main.customerSpeak(persons, "Jewel", "Jea");
        });

        assertEquals(Main.customerSpeak(persons, "Felix", "Jeshiel"), "Oh, hello, Jeshiel. Can you assist me?");
        assertEquals(Main.customerSpeak(persons, "Jewel", "Jea"), "Oh, hello, Jea. Can you assist me?");
        Main.assignPM(persons, "Jea", "Mary");

        assertEquals(Main.customerSpeak(persons, "Felix", "Jea"), "Can I see your manager Mary?");
    }

    @Test
    void testCustomerSpeakInvalid() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Customer("Jewel", 38));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertThrows(ClassCastException.class, ()->{
            Main.customerSpeak(persons, "Mary", "Jeshiel");
            Main.customerSpeak(persons, "Jewel", "Felix");
        });
        try {
            Main.customerSpeak(persons, "Mary", "Jeshiel");
        } catch (ClassCastException e) {
            assertEquals(e.getMessage(), "Mary is not a customer");
        }
        try {
            Main.customerSpeak(persons, "Jewel", "Felix");
        } catch (ClassCastException e) {
            assertEquals(e.getMessage(), "Felix is not an employee");
        }
    }


    @Test
    void testCustomerSpeakInvalid2() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Customer("Jewel", 38));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertThrows(NoSuchElementException.class, ()->{
            Main.customerSpeak(persons, "Travis", "Jeshiel");
            Main.customerSpeak(persons, "Jewel", "Travis");
        });
        try {
            Main.customerSpeak(persons, "Jewel", "Travis");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Travis does not exist");
        }
    }
}