public class Employee
{
    private String firstName, lastName, standing, concentration, rank;
    private int employee_id;

    public Employee(String firstName, String lastName, String standing, String concentration, String rank)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.standing = standing;
        this.concentration = concentration;
        this.rank = rank;
    }
    public String getFirstName(){return firstName;}
    public String getLastName() { return lastName;}
    public String getConcentration() { return concentration;}
    public String getStanding() { return standing;}
    public String getRank() { return rank;}
    public void setEmployee_id(int employee_id) { this.employee_id = employee_id;}
    public int getEmployee_id() { return employee_id; }
}
