package sqlmeter.ui;

import javax.swing.JDialog;

/**
 *
 * @author Leonid Ivanov
 */
public class APanelCard extends javax.swing.JPanel {

	private static final long serialVersionUID = -5658083484781900452L;
	
	public JDialog dialog;
    public boolean isUpdate;

    public APanelCard() {
        dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);
    }   
   
    public JDialog getDialog() {
        return dialog;
    }               
        
    public boolean IsUpdate() {
        return isUpdate;
    }
}
