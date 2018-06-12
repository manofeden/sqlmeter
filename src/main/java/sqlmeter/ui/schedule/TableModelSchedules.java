package sqlmeter.ui.schedule;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import sqlmeter.model.Schedule;

/**
 *
 * @author Leonid Ivanov
 */
public class TableModelSchedules extends AbstractTableModel{
    
	private static final long serialVersionUID = 1L;
	
	private List<Schedule> schedules;
    
    public TableModelSchedules() {
        this.schedules = new ArrayList<Schedule>();
    }

    public TableModelSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public int getRowCount() {    	
       return schedules.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }
 
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID Расписания";
            case 1:
                return "Название расписания";
            case 2:
                return "ID Задания на выполнение";
            case 3:
                return "Время выполнения";
            case 4:
                return "Входящие параметры для задания";
            case 5:
                return "Время запуска";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Schedule s = schedules.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return s.getSchd_ID();
            case 1:
                return s.getSchd_Name();
            case 2:
                return s.getSchd_task_ID();
            case 3:
                return s.getSchd_Time();
            case 4:
                return s.getSchd_InputParams();
            case 5:
                return s.getSchd_StartTime();
            default:
                return null;
        }
    }

}
