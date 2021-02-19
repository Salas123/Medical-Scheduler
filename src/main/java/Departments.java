import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Departments
{

    static String departmentColor;
    static HashMap<Integer, HashSet<Employee>> employeeShifts;
    static boolean isTuesdayOrWednesday;
    static int numOfEmployeesWorking;

    public Departments(boolean isTuesdayOrWednesday, String departmentColor){
       this.isTuesdayOrWednesday = isTuesdayOrWednesday;
       this.departmentColor = departmentColor;
       numOfEmployeesWorking = 0;

        for (int i = 0; i < 23; i++) {
            employeeShifts.put(i, new HashSet<Employee>());
        }
    }

    public void addEmployeeToThisShift(int startTime, int endTime, Employee employee)
    {
        for (int i = startTime; i <= endTime; i++) {
            HashSet<Employee> holder = employeeShifts.get(i);
            holder.add(employee);
            employeeShifts.put(i, holder);
        }
        numOfEmployeesWorking +=1;
    }
    public ArrayList<HashSet<Employee>> getEmployeesWorkingThisShift(int startTime, int endTime)
    {
        ArrayList<HashSet<Employee>> employeeShiftToReturn = new ArrayList<>();
        for(int i = startTime; i <= endTime; i++)
            employeeShiftToReturn.add(employeeShifts.get(i));


        return employeeShiftToReturn;
    }
    public boolean doesEmployeeWorkThisDay(Employee employee)
    {
        for (Map.Entry<Integer, HashSet<Employee>> areas: employeeShifts.entrySet())
        {
            if(areas.getValue().contains(employee))
                return true;
        }

        return false;
    }
}

/*
*
* */
