package sqlmeter.ui.schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;

import sqlmeter.SQL;
import sqlmeter.dotask.JPanelDoTask;
import sqlmeter.model.Schedule;
import sqlmeter.model.Task;

/**
 * @author Leonid Ivanov
 */
public class JPanelSchedules extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	private JButton jButton1, jButton2, jButton3, jButton4;
	private JScrollPane jScrollPane1;
	private JTable table;

	public JPanelSchedules() {
		initComponents();

		refresh();
	}

	private void refresh() {
		SWk_TableSchedule swk = new SWk_TableSchedule(this);
		swk.execute();
	}

	public void makeModel(final List<Schedule> schedules) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TableModelSchedules model = new TableModelSchedules(schedules);
				table.setModel(model);
				model.fireTableDataChanged();
			}
		});
	}

	private void edit() {
		int row = table.getSelectedRow();

		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Не выбрана строка для редактирования", "Предупреждение!", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Object valueAt = table.getValueAt(row, 0);
		Schedule sch = SQL.getSchedule(valueAt);

		if (sch == null) {
			JOptionPane.showMessageDialog(null, "Записи не найдено!", "Предупреждение!", JOptionPane.WARNING_MESSAGE);
			return;
		}

		JPanelScheduleCard card = new JPanelScheduleCard();
		card.setData(sch);
		card.getDialog().setVisible(true);

		if (card.isUpdate) {
			refresh();
		}
	}

	private void insert() {
		JPanelScheduleCard card = new JPanelScheduleCard();
		card.getDialog().setVisible(true);

		// если вставка прошла успеншно, то обновляем таблицу задач
		if (card.isUpdate) {
			refresh();
		}
	}

	private void delete() {
		int row = table.getSelectedRow();

		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Не выбрана строка для удаления!", "Предупреждение!", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Object valueAt = table.getValueAt(row, 0);
		boolean deleteTask = SQL.deleteSchedule(valueAt);

		// если удаление успеншно, то обновляем таблицу задач
		if (deleteTask) {
			refresh();
		}
	}

	private void startTask() {
		int row = table.getSelectedRow();

		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Не выбрана строка!", "Предупреждение!", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Object id_task = table.getValueAt(row, 2);
		Task task = SQL.getTask(id_task);
		if (task == null) {
			JOptionPane.showMessageDialog(null, "Задачи " + id_task + " не найдено!", "Предупреждение!", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String sql = task.getTask_SQL();

		Schedule sch = new Schedule();
		sch.setSchd_ID((String) table.getValueAt(row, 0));
		sch.setSchd_Name((String) table.getValueAt(row, 1));
		sch.setSchd_task_ID((String) table.getValueAt(row, 2));
		sch.setSchd_InputParams((String) table.getValueAt(row, 4));
		sch.setSchd_StartTime((String) table.getValueAt(row, 5));

		Action ac = new RefreshAction();
		String dialog_name = sch.getSchd_ID() + " ~ " + sch.getSchd_Name();
		JPanelDoTask pa = new JPanelDoTask(dialog_name);
		pa.show_and_start(sch, sql, ac);
	}

	private class RefreshAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			Schedule sch = (Schedule) event.getSource();
			SQL.updateSchedule(sch);

			refresh();
		}
	}

	private void initComponents() {

		jScrollPane1 = new JScrollPane();
		table = new JTable();
		TableModelSchedules model = new TableModelSchedules();
		table.setModel(model);
		jScrollPane1.setViewportView(table);

		jButton1 = new JButton();
		jButton2 = new JButton();
		jButton3 = new JButton();
		jButton4 = new JButton();

		jButton1.setIcon(new ImageIcon(getClass().getResource("/images/plus24.png"))); // NOI18N
		jButton1.setToolTipText("Добавить");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				insert();
			}
		});

		jButton2.setIcon(new ImageIcon(getClass().getResource("/images/edit24.png"))); // NOI18N
		jButton2.setToolTipText("Изменить");
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				edit();
			}
		});

		jButton3.setIcon(new ImageIcon(getClass().getResource("/images/delete24.png"))); // NOI18N
		jButton3.setToolTipText("Удалить");
		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				delete();
			}
		});

		jButton4.setIcon(new ImageIcon(getClass().getResource("/images/play24.png"))); // NOI18N
		jButton4.setText("Запустить");
		jButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				startTask();
			}
		});

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(jButton1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton2)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton4)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jButton2, GroupLayout.Alignment.TRAILING).addComponent(jButton3).addComponent(jButton1)).addComponent(jButton4))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane1).addContainerGap()));
	}

}
