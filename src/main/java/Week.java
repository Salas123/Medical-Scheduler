import java.util.HashMap;

public class Week
{
    static int weekNum;
    static HashMap<Integer,Day> days;
    static HashMap<String, Integer> hoursWorkingThisWeek;

    public Week(int weekNum)
    {
        this.weekNum = weekNum;

        days = new HashMap<>();
        hoursWorkingThisWeek = new HashMap<>();
    }

    public int getWeekNum() { return weekNum; }
    public void addDayToWeek(Day day) {


        days.put(days.size() + 1, day);

    }
}
