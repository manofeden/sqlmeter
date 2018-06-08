package sqlmeter.ui.schedule;

import java.util.List;
import javax.swing.SwingWorker;
import sqlmeter.SQL;
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
        listSchedules = SQL.selectAllFromSchedule();       
        return null;
    }
    
    @Override
    protected void done() {
        ui.makeModel(listSchedules);
    }

 }
