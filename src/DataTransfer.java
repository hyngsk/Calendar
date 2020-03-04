public class DataTransfer {

    int eventDay, eventMonth, eventYear;
    String eventName;
    String timeset;

    //    constructor
    public DataTransfer() {
        eventName = "no events entered.";
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public String getTimeset() {
        return timeset;
    }

    public void setTimeset(String timeset) {
        this.timeset = timeset;
    }

    public String intToSt(int yyyy, int mm, int dd) {

        return String.format("%d%02d%02d", yyyy, mm, dd);
    }

    public String intToSthp(int yyyy, int mm, int dd) {

        return String.format("%d-%02d-%02d", yyyy, mm, dd);
    }

    public int getEventDay() {
        return eventDay;
    }

    public void setEventDay(int day) {
        this.eventDay = day;
    }

    public int getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(int month) {
        this.eventMonth = month;
    }

    public int getEventYear() {
        return eventYear;
    }

    public void setEventYear(int year) {
        this.eventYear = year;
    }

    @Override
    public String toString() {
        return "DataTransfer{" +
                "eventName=" + eventName +
                ", timeset='" + timeset + '\'' +
                '}';
    }


}
