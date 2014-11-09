package Parser;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

//@author A0090971Y
public class DateTimeParser {
    private List<Date> dates = null;
    private String matchingValue = null;

    //@author A0090971Y
    /**
     * constructor for DateTimeParser class
     * @param userInput
     */
    public DateTimeParser(String userInput){
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(userInput);
        {
            for (DateGroup group : groups) {
                dates = group.getDates();
                matchingValue = group.getText();
            }
        }
    }

    //@author A0090971Y
    public Date getStartDateTime() {
        Date startDateTime;
        if (dates != null) {
            startDateTime = dates.get(0);
        }
        else {
            startDateTime = null;
        }
        return startDateTime;
    }

    //@author A0090971Y
    public Date getEndDateTime() {
        Date endDateTime;
        if ((dates!= null)&& (dates.size() >=2)) {
            endDateTime = dates.get(1);
        }
        else {
            endDateTime = null;
        }
        return endDateTime;
    }
}