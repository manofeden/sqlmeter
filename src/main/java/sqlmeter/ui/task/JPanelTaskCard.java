package sqlmeter.ui.task;

import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import sqlmeter.SQL;
import sqlmeter.model.Task;
import sqlmeter.ui.APanelCard;

/**
 *
 * @author Leonid Ivanov
 */
public class JPanelTaskCard extends APanelCard {

	private JButton jButton1;
	private JButton jButton2;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel_ID;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea_SQL;
	private JTextField jTextField_Name;

	public JPanelTaskCard() {
		initComponents();
		addToDialog();
	}

	private void addToDialog() {
		dialog.add(this);
		dialog.setTitle("Карточка Задания");
		dialog.pack();
	}

	public void setData(Task task) {
		if (task == null)
			return;

		jLabel_ID.setText(task.getTask_ID());
		jTextField_Name.setText(task.getTask_Name());
		jTextArea_SQL.setText(task.getTask_SQL());
	}

	public Task getData() {
		Task task = new Task();
		task.setTask_ID(jLabel_ID.getText());
		task.setTask_Name(jTextField_Name.getText());
		task.setTask_SQL(jTextArea_SQL.getText());
		return task;
	}

	private void save() {
		Task task = getData();
		if (!task.getTask_ID().isEmpty()) {
			isUpdate = SQL.updateTask(task);
		}

		task.setTask_ID(UUID.randomUUID().toString());
		isUpdate = SQL.insertTask(task);

		if (isUpdate)
			dialog.setVisible(false);
	}

	private void initComponents() {

		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jTextField_Name = new JTextField();
		jLabel3 = new JLabel();
		jScrollPane1 = new JScrollPane();
		jTextArea_SQL = new JTextArea();
		jLabel_ID = new JLabel();
		jButton1 = new JButton();
		jButton2 = new JButton();

		jLabel1.setText("ID:");
		jLabel2.setText("Название:");
		jLabel3.setText("Запрос:");

		jTextArea_SQL.setColumns(20);
		jTextArea_SQL.setRows(5);
		jScrollPane1.setViewportView(jTextArea_SQL);

		jButton1.setText("Ok");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				save();
			}
		});

		jButton2.setText("Отмена");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dialog.setVisible(false);
			}
		});

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jTextField_Name)
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel_ID,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(jLabel2).addComponent(jLabel3))
								.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(
								layout.createSequentialGroup().addComponent(jButton1)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jButton2)))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel1)
								.addComponent(jLabel_ID))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jTextField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jLabel3)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jButton1)
								.addComponent(jButton2))
						.addContainerGap()));
	}

}
