import java.util.Comparator;

public class SortEvent implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {
        int dayCompare = Integer.valueOf(o1.eventDay).compareTo(Integer.valueOf(o2.eventDay));
        int monthCompare = Integer.valueOf(o1.eventMonth).compareTo(Integer.valueOf(o2.eventMonth));
        int yearCompare = Integer.valueOf(o1.eventYear).compareTo(Integer.valueOf(o2.eventYear));

        if (yearCompare == 0) {
            if (monthCompare == 0) {
                return dayCompare;
            } else {
                return monthCompare;
            }
        } else {
            return yearCompare;
        }

    }
}
