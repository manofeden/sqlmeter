package sqlmeter.ui.task;

import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import sqlmeter.SQL;
import sqlmeter.model.Task;

/**
 *
 * @author Leonid Ivanov
 */
public class JPanelTasks extends javax.swing.JPanel {
	private JButton jButton1, jButton2, jButton3;	
	private JScrollPane jScrollPane1;
	private JTable table;

	public JPanelTasks() {
		initComponents();
		
		SWk_SelectAllTasks swk = new SWk_SelectAllTasks();
		swk.execute();
	}

	// Запускаем запрос к БД в фоновом режиме.
	private class SWk_SelectAllTasks extends SwingWorker<Void, Void> {
		private List<Task> result;

		@Override
		protected Void doInBackground() throws Exception {
			result = SQL.selectAllFromTasks();
			return null;
		}

		@Override
		protected void done() {
			if (result == null) {
				return;
			}
			makeModel(result);
		}
	}

	private void makeModel(final List<Task> tasks) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TableModelTasks model = new TableModelTasks(tasks);
				table.setModel(model);
				model.fireTableDataChanged();
			}
		});
	}

	private void edit() {
		int row = table.getSelectedRow();

		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Не выбрана строка для редактирования", "Предупреждение!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Object valueAt = table.getValueAt(row, 0);
		Task task = SQL.getTask(valueAt);

		if (task == null) {
			JOptionPane.showMessageDialog(null, "Записи не найдено!", "Предупреждение!", JOptionPane.WARNING_MESSAGE);
			return;
		}

		JPanelTaskCard card = new JPanelTaskCard();
		card.setData(task);
		card.getDialog().setVisible(true);

		if (card.isUpdate) {
			SWk_SelectAllTasks swk = new SWk_SelectAllTasks();
			swk.execute();
		}
	}

	private void insert() {
		JPanelTaskCard card = new JPanelTaskCard();
		card.getDialog().setVisible(true);

		// если вставка прошла успешно, то обновляем таблицу задач
		if (card.isUpdate) {
			SWk_SelectAllTasks swk = new SWk_SelectAllTasks();
			swk.execute();
		}
	}

	private void delete() {
		int row = table.getSelectedRow();

		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Не выбрана строка для удаления!", "Предупреждение!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Object valueAt = table.getValueAt(row, 0);
		boolean deleteTask = SQL.deleteTask(valueAt);

		// если удаление успешно, то обновляем таблицу задач
		if (deleteTask) {
			SWk_SelectAllTasks swk = new SWk_SelectAllTasks();
			swk.execute();
		}
	}

	private void initComponents() {

		jScrollPane1 = new JScrollPane();
		table = new JTable();
		TableModelTasks model = new TableModelTasks();
		table.setModel(model);
		
		jButton1 = new JButton();
		jButton2 = new JButton();
		jButton3 = new JButton();
		
		jScrollPane1.setViewportView(table);
		
		jButton1.setIcon(new ImageIcon(getClass().getResource("/images/plus24.png"))); // NOI18N
		jButton1.setToolTipText("Добавить");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				insert();
			}
		});

		jButton2.setIcon(new ImageIcon(getClass().getResource("/images/edit24.png"))); // NOI18N
		jButton2.setToolTipText("Изменить");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				edit();
			}
		});

		jButton3.setIcon(new ImageIcon(getClass().getResource("/images/delete24.png"))); // NOI18N
		jButton3.setToolTipText("Удалить");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				delete();
			}
		});

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addComponent(jButton1)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton3)
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 701,
								Short.MAX_VALUE))));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(9, 9, 9)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jButton1)
								.addComponent(jButton2, GroupLayout.Alignment.TRAILING).addComponent(jButton3))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE).addContainerGap()));
	}

}
