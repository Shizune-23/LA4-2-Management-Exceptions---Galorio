package Management_Exc;

public class Employee extends Person {
    private double salary;

    /**
     * TODO implementation
     * @param name the name of the employee
     * @param age the age of the employee
     * @param salary the salary of the employee
     * @throws IllegalArgumentException when salary does not reach minimum salary of 30,000
     */
    public Employee(String name, int age, double salary)  {
        super(name, age);
        setSalary(salary);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {

        if(salary < 30000) {
            throw  new IllegalArgumentException("Salary must be greater than or equal to 30000");
        }

        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + salary;
    }

    @Override
    public void performTask() {
        System.out.println(getName() + " is working");
    }
}
