package sqlmeter.dotask;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;

import sqlmeter.model.Schedule;
import sqlmeter.ui.schedule.SWk_TaskShow;

/**
 * @author Leonid Ivanov
 */
public class JPanelDoTask extends JPanel {

	private JDialog dialog;
	private volatile boolean isFinish;

	private JButton jButtonCancel;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;

	public JPanelDoTask(String title) {
		initComponents();

		dialog = new JDialog();
		dialog.setTitle(title);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setModal(false);
		dialog.setLocationRelativeTo(null);

		addToDialog();
	}	

    public void show_and_start(Schedule sch, String sql, Action ac) {
        dialog.setVisible(true);
        SWk_TaskShow swk = new SWk_TaskShow(sch, sql, ac, this);
        swk.execute();
    }
        
    private void addToDialog() {
        dialog.add(this);          
        dialog.pack();
    }
    
    public JDialog getDialog() {
        return dialog;
    } 
    
    public boolean isFinish(){
        return isFinish;
    }
    
    public void setDispose(){
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    public void setCancelDisable(){
        jButtonCancel.setEnabled(false);
    }
    
    public synchronized void addText(String text){
        String body = jTextArea1.getText();
        if (!body.isEmpty()){
            body=body+System.lineSeparator();
        }
        
       jTextArea1.setText(body+text);
    }

	private void finish() {
		isFinish = true;
		setDispose();
	}

	private void initComponents() {
		jScrollPane1 = new JScrollPane();
		jTextArea1 = new JTextArea();
		jButtonCancel = new JButton();

		jTextArea1.setEditable(false);
		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);

		jButtonCancel.setIcon(new ImageIcon(getClass().getResource("/images/stop24.png")));
		jButtonCancel.setText("Остановить");
		jButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				finish();
			}
		});

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
								.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE).addComponent(jButtonCancel)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButtonCancel)
						.addContainerGap()));
	}

}
