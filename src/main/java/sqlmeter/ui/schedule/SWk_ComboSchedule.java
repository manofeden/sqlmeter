package sqlmeter.ui.schedule;

import java.util.List;
import javax.swing.SwingWorker;
import sqlmeter.SQL;
import sqlmeter.model.Task;

/**
 *
 * @author Leonid Ivanov
 */
class SWk_ComboSchedule extends SwingWorker<Void, Void>{
    
    private JPanelScheduleCard ui;
    private List<Task> listTasks;    

    public SWk_ComboSchedule(JPanelScheduleCard ui) {
        this.ui=ui;
    }

    @Override
    protected Void doInBackground() throws Exception {
        listTasks = SQL.selectAllFromTasks();
        return null;
    }

    @Override
    protected void done() {      
        if (listTasks == null || ui.isComboFinish()) {
            return;
        }

        ui.makeComboModel(listTasks);
    }
}
