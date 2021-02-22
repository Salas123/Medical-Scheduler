import java.util.HashMap;

public class Month
{
    static HashMap<Integer, Week> weekHashMap;
    static HashMap<Integer, Day> dayHashMap;
    static int numOfDays;

    public Month(){
        weekHashMap = new HashMap<>();
        dayHashMap = new HashMap<>();
    }

    public void addWeekToMonth(Week week){ weekHashMap.put(weekHashMap.size() + 1, week); }

    public void addDayToMonth(Day day) { dayHashMap.put(dayHashMap.size() + 1, day); }

    public Day getDayFromMonth(int dayNum){return dayHashMap.get(dayNum); }

    public int getNumOfDays(){
        numOfDays = dayHashMap.size();
        return numOfDays;
    }
}
