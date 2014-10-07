package application;

import java.util.Date;

public class DeadlineTask extends Task {
    
    private Date deadline;

    public DeadlineTask() {
        // TODO Auto-generated constructor stub
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

}
