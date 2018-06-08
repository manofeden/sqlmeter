package sqlmeter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import sqlmeter.ui.schedule.JPanelSchedules;
import sqlmeter.ui.task.JPanelTasks;

/**
 * @author Leonid Ivanov
 */
public class StartJFrame extends javax.swing.JFrame {
	private JTabbedPane jTabbedPane1;

	public StartJFrame() {
		setNimbus();
		initComponents();

		JPanelTasks paTasks = new JPanelTasks();
		JPanelSchedules paSchedules = new JPanelSchedules();

		jTabbedPane1.add("Задачи", paTasks);
		jTabbedPane1.add("Расписание", paSchedules);

		this.setSize(800, 500);
		this.setLocationRelativeTo(null);
		this.setTitle("***********");

		setVisible(true);
	}

	private void setNimbus() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
			Logger.getLogger(StartJFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void initComponents() {
		jTabbedPane1 = new JTabbedPane();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(jTabbedPane1).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(jTabbedPane1).addContainerGap()));

		pack();
	}

}
