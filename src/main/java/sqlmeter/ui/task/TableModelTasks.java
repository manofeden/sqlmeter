package sqlmeter.ui.task;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import sqlmeter.model.Task;

/**
 *
 * @author Leonid Ivanov
 */
public class TableModelTasks extends AbstractTableModel{
   
	private static final long serialVersionUID = 1L;
	
	private List<Task> tasks;
    
    public TableModelTasks() {
        this.tasks = new ArrayList<Task>();
    }

    public TableModelTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int getRowCount() {
       return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Название";
            case 2:
                return "Запрос";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task task = tasks.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return task.getTask_ID();
            case 1:
                return task.getTask_Name();
            case 2:
                return task.getTask_SQL();
            default:
                return null;
        }
    }
    
}
