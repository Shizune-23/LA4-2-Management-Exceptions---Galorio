package Management_Exc;

import java.util.List;
import java.util.NoSuchElementException;

public class Main {

    /**
     * TODO this implementation
     * @param persons the list of persons
     * @param manager the manager to give the salary
     * @param employee the employee to receive the raise
     * @param salary the salary increase to be given
     * @throws ClassCastException when manager or employee is not a manager or employee
     * @throws IllegalArgumentException when salary is invalid
     * @throws NoSuchElementException when given manager or employee does not exist in the list of persons
     */
    public static void giveRaise(List<Person> persons, String manager, String employee, double salary)  {
            Manager m = null;
            Employee e = null;
            for (Person p : persons) {
                if (p.getName().equals(manager)) {
                    if(p instanceof Manager) {
                        m = (Manager) p;
                        break;
                    } else {
                        throw new ClassCastException(p.getName() + " is not a manager");
                    }
                }
            }
            for (Person p : persons) {
                if (p.getName().equals(employee)) {
                    if(!(p instanceof Employee)) {
                        throw new ClassCastException(p.getName() + " is not an employee");
                    }
                    e = (Employee) p;
                    break;
                }
            }

            if(salary < 0) {
                throw new IllegalArgumentException("Raise must be non-negative");
            }

            if(m == null ) {
                throw new NoSuchElementException(manager + " does not exist");
            }

            if(e == null ) {
                throw new NoSuchElementException(employee + " does not exist");
            }

            m.giveRaise(e, salary);
    }

    /**
     * TODO this implementation
     * @param persons the list of persons
     * @param developer the developer to be assigned
     * @param manager the manager assigned to the dev
     * @throws ClassCastException when manager or developer is not a manager or employee
     * @throws NoSuchElementException when given manager or developer does not exist in the list of persons
     * @throws IllegalStateException when developer already has a manager
     */
    public static void assignPM(List<Person> persons, String developer, String manager) {
            Developer d = null;
            Manager m = null;
            
            for(Person p: persons) {
                if(p.getName().equals(developer)) {
                    if(p instanceof Developer) {
                        d = (Developer) p;
                    } else {
                        throw new ClassCastException(p.getName() + " is not a developer");
                    }
                }
                if(p.getName().equals(manager)) {
                    if(p instanceof Manager) {
                        m = (Manager) p;
                    } else {
                        throw new ClassCastException(p.getName() + " is not a manager");
                    }
                }
            }

            if(m == null) {
                throw new NoSuchElementException(manager + " does not exist");
            }

            if(d == null) {
                throw new NoSuchElementException(developer + " does not exist");
            }

            if(d.getProjectManager() != null) {
                throw new IllegalStateException(d.getName() + " already has a manager: " + d.getProjectManager().getName());
            }

            d.setProjectManager(m);
    }

    /**
     * TODO this implementation
     * @param persons the list of persons
     * @param customer the customer to speak to the employee
     * @param employee the employee to be spoken to
     * @return the dialogue of the customer to the employee
     * @throws IllegalArgumentException when given customer or employee is not what they are
     * @throws NoSuchElementException when given customer or employee is not in the list of persons
     */
    public static String customerSpeak(List<Person> persons, String customer, String employee) {
        Customer c = null;
        Employee e = null;

        for(Person p: persons) {
            if (p.getName().equals(customer)) {
                if (p instanceof Customer) {
                    c = (Customer) p;
                } else {
                    throw new ClassCastException(p.getName() + " is not a customer");
                }
            }
        }

        for(Person p: persons) {
            if(p.getName().equals(employee)) {
                if(p instanceof Employee) {
                    e = (Employee) p;
                } else {
                    throw new ClassCastException(p.getName() + " is not an employee");
                }
            }
        }

        if(c == null) {
            throw new NoSuchElementException(customer + " does not exist");
        }

        if(e == null) {
            throw new NoSuchElementException(employee + " does not exist");
        }

        String speak = c.speak(e);
        return speak;
    }
}
