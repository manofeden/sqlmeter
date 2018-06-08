package sqlmeter.ui.schedule;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.swing.Action;
import javax.swing.SwingWorker;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import sqlmeter.SQL;
import sqlmeter.dotask.JPanelDoTask;
import sqlmeter.dotask.Parse;
import sqlmeter.model.Schedule;

/**
 *
 * @author Leonid Ivanov
 */
public class SWk_TaskShow extends SwingWorker<Void, String> {

    private Schedule sch;
    private String sql;
    private JPanelDoTask ui;
    private String swk_name;
    private Action ac;
    private Long resultTime;

    public SWk_TaskShow(Schedule sch, String sql, Action ac, JPanelDoTask ui) {
        this.sch = sch;
        this.swk_name = sch.getSchd_ID() + " ~ " + sch.getSchd_Name();
        this.sql = sql;
        this.ac = ac;
        this.ui=ui;
    }

    @Override
    protected Void doInBackground() throws Exception {

        ui.addText("Соединение...");

        Date date1 = new Date();
        publish("Старт запроса: " + date1);

//                    Thread.sleep(3500);

        if (ui.isFinish()) {
            return null;
        }

        Properties propConnection = parseParam();

        if (ui.isFinish()) {
            return null;
        }

        publish(sql);

        if (sql != null && !sql.isEmpty()) {
            if (propConnection == null) {
                if (!doSQL()) {
                    return null;
                }

            } else {
                if (!doSQL(propConnection)) {
                    return null;
                }
            }
        } else {
            publish("Ошибка: Пустое значение SQL запроса!");
            return null;
        }

        Date date2 = new Date();
        publish("Финишь  запроса: " + date2);

        resultTime = date2.getTime() - date1.getTime();
        publish("Время выпонения запроса: " + resultTime + " ms");

        sch.setSchd_Time(getFormatTimeFromLong(resultTime));
        ActionEvent event = new ActionEvent(sch, 1, "refresh");
        ac.actionPerformed(event);

        publish("Завершение!");

        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        for (String text : chunks) {
            ui.addText(text);
        }
    }

    @Override
    protected void done() {
        if (ui.isFinish()) {
            publish("Задача прервана!");
        }

        ui.setDispose();
        ui.setCancelDisable();
    }
   
    private Properties parseParam() {
        if (sch.getSchd_InputParams() == null || sch.getSchd_InputParams().isEmpty()) {
            return null;
        }

        try {
            Parse parse = new Parse(sch.getSchd_InputParams(), sql);
            sql = parse.getSql();
            return parse.getPropConnection();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            publish("Ошибка: " + ex);
            return null;
        }
    }
    
    private boolean doSQL() {
        try (Connection con = SQL.getConnection(); Statement stmt = con.createStatement();) {
            stmt.execute(sql);
            publish("Запрос успешно выполнен.");
            return true;
        } catch (SQLException e) {
            System.out.println(swk_name+" - Exception in doSQL:" + e);
            publish("Ошибка:" + e.getMessage());
            return false;
        }
    }

    private boolean doSQL(Properties propConnection) {
        try (Connection con = SQL.getConnection(propConnection); Statement stmt = con.createStatement();) {
            stmt.execute(sql);
            publish("Запрос успешно выполнен.");
            return true;
        } catch (SQLException e) {
            System.out.println(swk_name+" - Exception in doSQL:" + e);
            publish("Ошибка:" + e.getMessage());
            return false;
        }
    }

    private String getFormatTimeFromLong(Long t) {
        if (t == null || t < 0) {
            return null;
        }

        long hours = TimeUnit.MILLISECONDS.toHours(t);
        t -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(t);
        t -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(t);

        t = t - seconds * 1000;

        StringBuilder sb = new StringBuilder();
        sb.append(hours);
        sb.append(":");
        sb.append(minutes);
        sb.append(":");
        sb.append(seconds);
        sb.append(".");
        sb.append(String.format("%03d", t));

        return (sb.toString());
    }
}

    

