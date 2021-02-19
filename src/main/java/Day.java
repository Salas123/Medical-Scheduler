import java.util.HashMap;

public class Day {
    static HashMap<String, Departments> departments;
    static int dayNum, numEmployeesInBlue, numEmployeesInRed, numEmployeesInOrange,
            numEmployeesInGreen, numEmployeesInBrown;

    public Day(int dayNum){
        this.dayNum = dayNum;
        numEmployeesInBlue = 0;
        numEmployeesInGreen = 0;
        numEmployeesInRed = 0;
        numEmployeesInOrange = 0;
        numEmployeesInBrown = 0;

        departments = new HashMap<>();

    }
    public int getNumEmployeesInBlue() {
        return numEmployeesInBlue;
    }

    public int getNumEmployeesInRed() {
        return numEmployeesInRed;
    }

    public int getNumEmployeesInBrown() {
        return numEmployeesInBrown;
    }

    public int getNumEmployeesInGreen() {
        return numEmployeesInGreen;
    }

    public int getNumEmployeesInOrange() {
        return numEmployeesInOrange;
    }

    public void setDepartment(String key, Departments departments){
        this.departments.put(key, departments);
    }

    public Departments getDepartment(String key)
    {
        if(!departments.containsKey(key))
            return null;

        return departments.get(key);
    }
}
