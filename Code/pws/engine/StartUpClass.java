
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
		//Window window = new Window();
		MainAlgoritme alg = new MainAlgoritme();
		alg.start();
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
		setTitle("v1.0");
		setLocationRelativeTo(rootPane);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setResizable(true);
		setSize(100,100);
		setLocation(400,400);
		JTextArea jtext = new JTextArea(40,40);
		jtext.append("heyy test");
		this.add(jtext);
		JButton button = new JButton("Exit");
		button.addActionListener((ActionEvent event) -> {
			System.exit(WindowConstants.EXIT_ON_CLOSE);
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

		//setJMenuBar(menubar);

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

