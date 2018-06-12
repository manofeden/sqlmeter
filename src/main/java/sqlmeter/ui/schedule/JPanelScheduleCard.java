package sqlmeter.ui.schedule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import sqlmeter.SQL;
import sqlmeter.model.Schedule;
import sqlmeter.model.Task;
import sqlmeter.ui.APanelCard;

/**
 * @author Leonid Ivanov
 */
public class JPanelScheduleCard extends APanelCard {
	private static final long serialVersionUID = 1L;

	private JButton jButton1, jButton2;
	private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel_ID;
	private JComboBox<TaskShow> jComboBox_task_ID;
	private JFormattedTextField jFormattedTextField_StartTime;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea_Params;
	private JTextField jTextField_Name;

	private String selected_schd_task_ID;
	private String schd_Time;
	private SWk_ComboSchedule swk;
	private volatile boolean isComboFinish;

	public JPanelScheduleCard() {
		initComponents();
		addToDialog();

		swk = new SWk_ComboSchedule(this);
		swk.execute();
	}

	public boolean isComboFinish() {
		return isComboFinish;
	}

	// из полученных из БД данных создаем модель для jComboBox с нужным для нас
	// отображением элементов
	// и вставляем эту модель в jComboBox, после отрисовки интерфейса
	public synchronized void makeComboModel(List<Task> listTasks) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				int selected_combo_ix = -1;
				TaskShow[] taskshow = new TaskShow[listTasks.size()];
				for (int i = 0; i < taskshow.length; i++) {
					taskshow[i] = new TaskShow(listTasks.get(i));

					if (selected_schd_task_ID != null && listTasks.get(i).getTask_ID().equals(selected_schd_task_ID)) {
						selected_combo_ix = i;
					}
				}

				DefaultComboBoxModel<TaskShow> defaultComboBoxModel = new DefaultComboBoxModel<TaskShow>(taskshow);
				jComboBox_task_ID.setModel(defaultComboBoxModel);
				jComboBox_task_ID.setSelectedIndex(selected_combo_ix);
			}
		});
	}

	private void addToDialog() {
		dialog.add(this);
		dialog.setTitle("Карточка Расписания");

		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				// флаг фонововому потоку о том что больше не требуется обновлять диалоговое
				// окно
				isComboFinish = true;
				dialog.setVisible(false);
			}
		});
		dialog.pack();
	}

	public void setData(Schedule sch) {
		if (sch == null)
			return;

		jLabel_ID.setText(sch.getSchd_ID());
		jTextField_Name.setText(sch.getSchd_Name());
		jTextArea_Params.setText(sch.getSchd_InputParams());
		schd_Time = sch.getSchd_Time();

		Date date = getDateFromTimestamp(sch.getSchd_StartTime());
		jFormattedTextField_StartTime.setValue(date);

		selected_schd_task_ID = sch.getSchd_task_ID();
	}

	Date getDateFromTimestamp(String strDate) {
		if (strDate == null) {
			return null;
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(strDate);
		} catch (ParseException ex) {
			System.out.println("Возможно неправильный формат Даты (" + strDate + "). Ошибка " + ex);
			return null;
		}
	}

	public Schedule getData() {
		Schedule sch = new Schedule();
		sch.setSchd_ID(jLabel_ID.getText());
		sch.setSchd_Name(jTextField_Name.getText());
		sch.setSchd_InputParams(jTextArea_Params.getText());
		sch.setSchd_Time(schd_Time);

		// если в jComboBox есть выбранный элемент, то заполняем поле schd_task_ID
		if (jComboBox_task_ID.getSelectedIndex() > -1) {
			TaskShow selected_taskshow = (TaskShow) jComboBox_task_ID.getSelectedItem();
			String task_ID = selected_taskshow.task.getTask_ID();
			sch.setSchd_task_ID(task_ID);
		}

		/*
		 * Если дата задана в поле, то конвертируем дату в строку имеющую вид yyyy-MM-dd
		 * H:mm:ss В таком виде это значение можно вставить в поле БД типа datetime
		 */
		Object value = jFormattedTextField_StartTime.getValue();
		if (value != null) {
			Date date = (Date) jFormattedTextField_StartTime.getValue();
			String timestamp = getTimestamp(date);
			sch.setSchd_StartTime(timestamp);
		}

		return sch;
	}

	public static String getTimestamp(Date d) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(d);
	}

	private void save() {
		// получаем заполненные данные из карточки
		Schedule sch = getData();

		// если ID отсутствует то добавляем запись
		if (sch.getSchd_ID().isEmpty()) {
			sch.setSchd_ID(UUID.randomUUID().toString());
			isUpdate = SQL.insertSchedule(sch);
		}

		// если есть ID то редактируем
		isUpdate = SQL.updateSchedule(sch);

		if (isUpdate) {
			dialog.setVisible(false);
		}
	}

	private void initComponents() {

		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jTextField_Name = new JTextField();
		jLabel3 = new JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea_Params = new javax.swing.JTextArea();
		jLabel_ID = new JLabel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jLabel4 = new JLabel();
		jComboBox_task_ID = new javax.swing.JComboBox<>();
		jLabel5 = new JLabel();
		jFormattedTextField_StartTime = new javax.swing.JFormattedTextField();

		jLabel1.setText("ID:");
		jLabel2.setText("Название:");
		jLabel3.setText("Параметры:");
		jLabel4.setText("Задание:");
		jLabel5.setText("Время запуска в формате yyyy-MM-dd H:mm:ss:");

		jTextArea_Params.setColumns(20);
		jTextArea_Params.setRows(5);
		jScrollPane1.setViewportView(jTextArea_Params);

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

		jFormattedTextField_StartTime.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
				new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd H:mm:ss"))));

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jTextField_Name)
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel_ID,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addComponent(jButton1)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jButton2))
						.addComponent(jComboBox_task_ID, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(jLabel2).addComponent(jLabel3).addComponent(jLabel4)
												.addComponent(jLabel5))
										.addGap(0, 0, Short.MAX_VALUE))
						.addComponent(jFormattedTextField_StartTime))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel1)
								.addComponent(jLabel_ID))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jTextField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel4)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jComboBox_task_ID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel5)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jFormattedTextField_StartTime, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel3)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jButton1)
								.addComponent(jButton2))
						.addContainerGap()));
	}

}
