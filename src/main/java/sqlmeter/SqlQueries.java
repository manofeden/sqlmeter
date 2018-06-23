package sqlmeter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import sqlmeter.model.Schedule;
import sqlmeter.model.Task;

/**
 * 
 * @author Leonid Ivanov
 */
public class SqlQueries {
	static String connectionUrl = getConnectionUrl();
	static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	static String getConnectionUrl() {
		Properties prop = Main.getProperty();
		return "jdbc:sqlserver://" + prop.getProperty("host") + ":" + prop.getProperty("port") + ";" + "databaseName=" + prop.getProperty("dbname") + ";user=" + prop.getProperty("username") + ";password=" + prop.getProperty("pass");
	}

	public static String getConnectionUrl(Properties prop) {
		return "jdbc:sqlserver://" + prop.getProperty("host") + ":" + prop.getProperty("port") + ";" + "databaseName=" + prop.getProperty("dbname") + ";user=" + prop.getProperty("username") + ";password=" + prop.getProperty("pass");
	}

	public static java.sql.Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DB_DRIVER);
			con = java.sql.DriverManager.getConnection(connectionUrl);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error Trace in getConnection() : " + e);
		}
		return con;
	}

	public static java.sql.Connection getConnection(Properties prop) {
		Connection con = null;
		String connectionUrl = getConnectionUrl(prop);
		try {
			Class.forName(DB_DRIVER);
			con = java.sql.DriverManager.getConnection(connectionUrl);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error Trace in getConnection() : " + e);
		}
		return con;
	}

	public static List<Task> selectAllFromTasks() {
		List<Task> list = new ArrayList<>();
		String sql = "SELECT * FROM Tasks ORDER BY task_Name";
		try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Task task = new Task(rs.getString("task_ID"), rs.getString("task_Name"), rs.getString("task_SQL"));
				list.add(task);
			}

			return list;
		} catch (Exception e) {
			System.out.println("Exception in selectAllFromTasks : " + e);
			return null;
		}
	}

	public static List<Schedule> selectAllFromSchedule() {
		List<Schedule> list = new ArrayList<>();
		String sql = "SELECT * FROM Schedules ORDER BY schd_StartTime";
		try (Connection con = getConnection(); Statement stmt = con.createStatement()) {

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Schedule s = new Schedule();
				s.setSchd_ID(rs.getString("schd_ID"));
				s.setSchd_Name(rs.getString("schd_Name"));
				s.setSchd_task_ID(rs.getString("schd_task_ID"));
				s.setSchd_Time(rs.getString("schd_Time"));
				s.setSchd_InputParams(rs.getString("schd_InputParams"));
				s.setSchd_StartTime(rs.getString("schd_StartTime"));
				list.add(s);
			}

			return list;
		} catch (Exception e) {
			System.out.println("Exception in selectAllFromSchedule : " + e);
			return null;
		}
	}

	public static Task getTask(Object id) {
		String sql = "SELECT * FROM Tasks WHERE task_ID=?";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setObject(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Task task = new Task(rs.getString("task_ID"), rs.getString("task_Name"), rs.getString("task_SQL"));
				return task;
			}

			return null; // not found
		} catch (Exception e) {
			System.out.println("Exception in getTask: " + e);
			return null;
		}
	}

	public static Schedule getSchedule(Object id) {
		String sql = "SELECT * FROM Schedules WHERE schd_ID=?";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setObject(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Schedule sch = new Schedule(rs.getString("schd_ID"), rs.getString("schd_Name"), rs.getString("schd_task_ID"), rs.getString("schd_Time"), rs.getString("schd_InputParams"), rs.getString("schd_StartTime"));
				return sch;
			}

			return null; // not found
		} catch (Exception e) {
			System.out.println("Exception in getSchedule: " + e);
			return null;
		}
	}

	public static boolean updateTask(Task task) {
		String sql = "UPDATE Tasks SET task_Name=? , task_SQL=? WHERE task_ID=?";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setObject(1, task.getTask_Name());
			pstmt.setObject(2, task.getTask_SQL());
			pstmt.setObject(3, task.getTask_ID());

			int executeUpdate = pstmt.executeUpdate();

			if (executeUpdate > 0) {
				System.out.println("Изменена Задача: " + task);
				return true;
			}
		} catch (Exception e) {
			System.out.println("Exception in updateTask: " + e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
		}

		return false;
	}

	public static boolean updateSchedule(Schedule sch) {
		String sql = "UPDATE Schedules SET schd_Name=? , schd_task_ID=?, schd_Time=?, schd_InputParams=?, schd_StartTime=? WHERE schd_ID=?";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setObject(1, sch.getSchd_Name());
			pstmt.setObject(2, sch.getSchd_task_ID());
			pstmt.setObject(3, sch.getSchd_Time());
			pstmt.setObject(4, sch.getSchd_InputParams());
			pstmt.setObject(5, sch.getSchd_StartTime());
			pstmt.setObject(6, sch.getSchd_ID());

			int executeUpdate = pstmt.executeUpdate();

			if (executeUpdate > 0) {
				System.out.println("Изменено Расписание: " + sch);
				return true;
			}
		} catch (Exception e) {
			System.out.println("Exception in updateSchedule: " + e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
		}

		return false;
	}

	public static boolean insertTask(Task task) {
		String sql = "INSERT INTO Tasks (task_ID, task_Name, task_SQL) VALUES (?, ?, ?)";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setObject(1, task.getTask_ID());
			pstmt.setObject(2, task.getTask_Name());
			pstmt.setObject(3, task.getTask_SQL());

			int executeUpdate = pstmt.executeUpdate();

			if (executeUpdate > 0) {
				System.out.println("Добавлена Задача: " + task);
				return true;
			}
		} catch (Exception e) {
			System.out.println("Exception in insertTask: " + e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
		}

		return false;
	}

	public static boolean insertSchedule(Schedule sch) {
		System.out.println("Расписание: " + sch);
		String sql = "INSERT INTO Schedules (schd_ID, schd_Name, schd_task_ID, schd_Time, schd_InputParams, schd_StartTime) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setObject(1, sch.getSchd_ID());
			pstmt.setObject(2, sch.getSchd_Name());
			pstmt.setObject(3, sch.getSchd_task_ID());
			pstmt.setObject(4, sch.getSchd_Time());
			pstmt.setObject(5, sch.getSchd_InputParams());
			pstmt.setObject(6, sch.getSchd_StartTime());

			int executeUpdate = pstmt.executeUpdate();

			if (executeUpdate > 0) {
				System.out.println("Добавлено Расписание: " + sch);
				return true;
			}
		} catch (Exception e) {
			System.out.println("Exception in insertSchedule: " + e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
		}

		return false;
	}

	public static boolean deleteTask(Object id) {
		String sql = "DELETE FROM Tasks WHERE task_ID=?";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setObject(1, id);

			int executeUpdate = pstmt.executeUpdate();

			if (executeUpdate > 0) {
				System.out.println("Удалена Задача: " + id);
				return true;
			}
		} catch (Exception e) {
			System.out.println("Exception in deleteTask: " + e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
		}

		return false;
	}

	public static boolean deleteSchedule(Object id) {
		String sql = "DELETE FROM Schedules WHERE schd_ID=?";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setObject(1, id);

			int executeUpdate = pstmt.executeUpdate();

			if (executeUpdate > 0) {
				System.out.println("Удалено Расписание: " + id);
				return true;
			}
		} catch (Exception e) {
			System.out.println("Exception in deleteSchedule: " + e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
		}

		return false;
	}

}
