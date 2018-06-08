package sqlmeter.model;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Leonid Ivanov
 */
public class Schedule {

    private String schd_ID;
    private String schd_Name;
    private String schd_task_ID;
    private String schd_Time;
    private String schd_InputParams;
    private String schd_StartTime;
    
    public Schedule(){};

    public Schedule(String schd_ID, String schd_Name, String schd_task_ID, String schd_Time, String schd_InputParams, String schd_StartTime) {
        this.schd_ID = schd_ID;
        this.schd_Name = schd_Name;
        this.schd_task_ID = schd_task_ID;
        this.schd_Time = schd_Time;
        this.schd_InputParams = schd_InputParams;
        this.schd_StartTime = schd_StartTime;
    }

    public Schedule(String schd_Name, String schd_task_ID, String schd_Time, String schd_InputParams, String schd_StartTime) {
        this.schd_ID = UUID.randomUUID().toString();
        this.schd_Name = schd_Name;
        this.schd_task_ID = schd_task_ID;
        this.schd_Time = schd_Time;
        this.schd_InputParams = schd_InputParams;
        this.schd_StartTime = schd_StartTime;
    }   
    
    public String getSchd_ID() {
        return schd_ID;
    }

    public void setSchd_ID(String schd_ID) {
        this.schd_ID = schd_ID;
    }

    public String getSchd_Name() {
        return schd_Name;
    }

    public void setSchd_Name(String schd_Name) {
        this.schd_Name = schd_Name;
    }

    public String getSchd_task_ID() {
        return schd_task_ID;
    }

    public void setSchd_task_ID(String schd_task_ID) {
        this.schd_task_ID = schd_task_ID;
    }

    public String getSchd_Time() {
        return schd_Time;
    }

    public void setSchd_Time(String schd_Time) {
        this.schd_Time = schd_Time;
    }

    public String getSchd_InputParams() {
        return schd_InputParams;
    }

    public void setSchd_InputParams(String schd_InputParams) {
        this.schd_InputParams = schd_InputParams;
    }

    public String getSchd_StartTime() {
        return schd_StartTime;
    }

    public void setSchd_StartTime(String schd_StartTime) {
        this.schd_StartTime = schd_StartTime;
    }

    @Override
    public String toString() {
        return "Schedule{" + "schd_ID=" + schd_ID + ", schd_Name=" + schd_Name + ", schd_task_ID=" + schd_task_ID + ", schd_Time=" + schd_Time + ", schd_InputParams=" + schd_InputParams + ", schd_StartTime=" + schd_StartTime + '}';
    }
        
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.schd_ID);
        hash = 61 * hash + Objects.hashCode(this.schd_Name);
        hash = 61 * hash + Objects.hashCode(this.schd_task_ID);
        hash = 61 * hash + Objects.hashCode(this.schd_Time);
        hash = 61 * hash + Objects.hashCode(this.schd_InputParams);
        hash = 61 * hash + Objects.hashCode(this.schd_StartTime);
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
        final Schedule other = (Schedule) obj;
        if (!Objects.equals(this.schd_ID, other.schd_ID)) {
            return false;
        }
        if (!Objects.equals(this.schd_Name, other.schd_Name)) {
            return false;
        }
        if (!Objects.equals(this.schd_task_ID, other.schd_task_ID)) {
            return false;
        }
        if (!Objects.equals(this.schd_InputParams, other.schd_InputParams)) {
            return false;
        }
        if (!Objects.equals(this.schd_Time, other.schd_Time)) {
            return false;
        }
        if (!Objects.equals(this.schd_StartTime, other.schd_StartTime)) {
            return false;
        }
        return true;
    }
    
    
}
