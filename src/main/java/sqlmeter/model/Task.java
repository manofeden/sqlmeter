package sqlmeter.model;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Leonid Ivanov
 */
public class Task {
    String task_ID;
    String task_Name;
    String task_SQL;
    
    public Task() {};

    public Task(String task_ID, String name, String task_SQL) {
        this.task_ID = task_ID;
        this.task_Name = name;
        this.task_SQL = task_SQL;
    }
    
    public Task(String name, String task_SQL) {
        this.task_ID = UUID.randomUUID().toString();
        this.task_Name = name;
        this.task_SQL = task_SQL;
    }

    public String getTask_ID() {
        return task_ID;
    }

    public void setTask_ID(String task_ID) {
        this.task_ID = task_ID;
    }

    public String getTask_Name() {
        return task_Name;
    }

    public void setTask_Name(String name) {
        this.task_Name = name;
    }

    public String getTask_SQL() {
        return task_SQL;
    }

    public void setTask_SQL(String task_SQL) {
        this.task_SQL = task_SQL;
    }

    @Override
    public String toString() {
        return "Task{" + "task_ID=" + task_ID + ", task_Name=" + task_Name + ", task_SQL=" + task_SQL + '}';
    }       

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.task_ID);
        hash = 47 * hash + Objects.hashCode(this.task_Name);
        hash = 47 * hash + Objects.hashCode(this.task_SQL);
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
        final Task other = (Task) obj;
        if (!Objects.equals(this.task_ID, other.task_ID)) {
            return false;
        }
        if (!Objects.equals(this.task_SQL, other.task_SQL)) {
            return false;
        }
        return true;
    }
              
    
}
