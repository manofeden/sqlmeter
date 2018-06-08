package sqlmeter.ui.schedule;

import java.util.Objects;
import sqlmeter.model.Task;

/**
 *
 * @author Leonid Ivanov
 */
public class TaskShow {

    Task task;

    public TaskShow(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return task.getTask_ID() + " ~ " + task.getTask_Name();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.task);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaskShow other = (TaskShow) obj;
        if (!Objects.equals(this.task, other.task)) {
            return false;
        }
        return true;
    }   
    
}
