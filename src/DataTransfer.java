import java.util.Vector;

public class DataTransfer {

	int eventDay, eventMonth, eventYear;

	String eventName;
	String timeset;

	Vector<String> DatehasEvent;

	//    constructor
	public DataTransfer() {
		eventName = "no events entered.";
	}

	public Vector<String> getDatehasEvent() {
		return DatehasEvent;
	}

	public void setDatehasEvent(Vector<String> datehasEvent) {
		DatehasEvent = datehasEvent;
	}

	@Override
	public String toString() {
		return "DataTransfer{" +
				"eventDay=" + eventDay +
				", eventMonth=" + eventMonth +
				", eventYear=" + eventYear +
				", eventName='" + eventName + '\'' +
				", timeset='" + timeset + '\'' +
				'}';
	}

	public String getYearMonth() {

		int yyyy = this.eventYear;
		int mm = this.eventMonth;

		return String.format("%d%02d", yyyy, mm);
	}

	public int getEventDay() {
		return eventDay;
	}

	public void setEventDay(int eventDay) {
		this.eventDay = eventDay;
	}

	public int getEventMonth() {
		return eventMonth;
	}

	public void setEventMonth(int eventMonth) {
		this.eventMonth = eventMonth;
	}

	public int getEventYear() {
		return eventYear;
	}

	public void setEventYear(int eventYear) {
		this.eventYear = eventYear;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getTimeset() {
		return timeset;
	}

	public void setTimeset(String timeset) {
		this.timeset = timeset;
	}

}
