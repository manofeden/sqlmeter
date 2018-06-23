package sqlmeter.ui.schedule;

import java.util.List;
import javax.swing.SwingWorker;
import sqlmeter.Sql;
import sqlmeter.model.Schedule;

/**
 *
 * @author Leonid Ivanov
 */
class SWk_TableSchedule extends SwingWorker<Void, Void> {

    private JPanelSchedules ui;
    private List<Schedule> listSchedules;

    public SWk_TableSchedule(JPanelSchedules ui) {
        this.ui=ui;
    }

    @Override
    protected Void doInBackground() throws Exception {        
        listSchedules = Sql.selectAllFromSchedule();       
        return null;
    }
    
    @Override
    protected void done() {
        ui.makeModel(listSchedules);
    }

 }
