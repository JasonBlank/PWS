
package pws.engine;

import javax.swing.*;
import java.awt.Toolkit;
import pws.algoritme.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Container;
import java.util.concurrent.TimeUnit;

public class StartUpClass {

	public static void main(String[] args){
		Window window = new Window();
	}

}

class Window extends JFrame{

	Window(){
		initUI();
		setVisible(true);
		double time = System.currentTimeMillis();
	}
	
	private void initUI(){
		ImageIcon ding = new ImageIcon("air_plane_airport.png");
		setIconImage(ding.getImage());
		
		setUndecorated(false);
		setTitle("Algoritme");
		setLocationRelativeTo(rootPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setResizable(true);
		setSize(300,300);
		
		JButton button = new JButton("START TIME");
		button.addActionListener((ActionEvent event) -> {
            MainAlgoritme alg = new MainAlgoritme(this);
            alg.start();
        }); 
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener((ActionEvent e) -> {System.exit(3);});
		file.add(exit);
		menubar.add(file);
		
		JMenu view = new JMenu("View");
		JMenuItem small = new JMenuItem("Small");
		small.addActionListener((ActionEvent e) -> {setSize(200,200);});
		view.add(small);
		JMenuItem normal = new JMenuItem("Normal");
		normal.addActionListener((ActionEvent e) -> {setSize(500,300);});
		view.add(normal);
		JMenuItem fullscreen = new JMenuItem("Full Screen");
		fullscreen.addActionListener((ActionEvent e) -> {setSize(Toolkit.getDefaultToolkit().getScreenSize());setLocation(2, 3);});
		view.add(fullscreen);
		menubar.add(view);
		
		setJMenuBar(menubar);
		
		createLayout(button);
		
		
	}
	
	private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        pane.setBackground(Color.WHITE);
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        
        gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(arg[0]));

        gl.setVerticalGroup(gl.createSequentialGroup().addComponent(arg[0]));
    }
}

