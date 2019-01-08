import javax.swing.SwingUtilities;

public class Launcher {

	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	View view = new View();
	        		view.setVisible(true);
	            }
	        });
	}

}
