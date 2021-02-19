public class Employee
{
    private String firstName, lastName, standing, concentration, rank;
    private int employee_id;
    static int numOfShifts;

    public Employee(String firstName, String lastName, String standing, String concentration, String rank)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.standing = standing;
        this.concentration = concentration;
        this.rank = rank;

        if(this.rank.equals("Rank 1"))
            numOfShifts = 19;
        else if(this.rank.equals("Rank 2"))
            numOfShifts = 18;
        else if(this.rank.equals("Rank 3"))
            numOfShifts = 16;
        else if(this.rank.equals("Rank 4"))
            numOfShifts = 15;
        else
            numOfShifts = 14;

    }
    //get functions
    public String getFirstName(){return firstName;}
    public String getLastName() {return lastName;}
    public String getConcentration() {return concentration;}
    public String getStanding() {return standing;}
    public String getRank() {return rank;}
    public int getEmployee_id() {return employee_id;}
    public int getNumOfShifts(){return numOfShifts;}
    //set functions
    public void setNumOfShifts(int numOfShifts){this.numOfShifts = numOfShifts;}
    public void setEmployee_id(int employee_id) {this.employee_id = employee_id;}

}
