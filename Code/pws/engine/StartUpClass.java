
package pws.engine;

import javax.swing.*;
import java.awt.Toolkit;
import pws.J1.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Container;
import java.util.concurrent.TimeUnit;

public class StartUpClass {

	public static void main(String[] args) {
		Window window = new Window();
		MainAlgoritme alg = new MainAlgoritme(window);
	}

}

class Window extends JFrame{

	Window(){
		initUI();
		setVisible(true);
		double time = System.currentTimeMillis();
		System.out.println(time);
		
		boolean upwards = true, leftwards = true;	
		while(!upwards){
			try{TimeUnit.MILLISECONDS.sleep(3);}catch(Throwable e){System.out.println(e);}
			
			if(getX() == 0) {leftwards = false;setLocation(1,getY());}
			if(getY() == 0) {upwards = false;setLocation(getX(),1);}
			if(getX() == Toolkit.getDefaultToolkit().getScreenSize().getWidth()-300) {leftwards = true;setLocation(getX()-1,getY());};
			if(getY() == Toolkit.getDefaultToolkit().getScreenSize().getHeight()-80) {upwards = true;setLocation(getX(),getY()-1);};
			
			
			if(upwards == true && leftwards == true)  setLocation(getX()-1, getY()-1);
			if(upwards == true && leftwards == false) setLocation(getX()+1, getY()-1);
			if(upwards == false && leftwards == true) setLocation(getX()-1, getY()+1);
			if(upwards == false && leftwards == false)setLocation(getX()+1, getY()+1);
		}
		
	}
	
	private void initUI(){
		ImageIcon ding = new ImageIcon("air_plane_airport.png");
		setIconImage(ding.getImage());
		
		setUndecorated(false);
		setTitle("hey boy");
		setLocationRelativeTo(rootPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setResizable(true);
		setSize(300,300);
		
		JButton button = new JButton("kms");
		button.addActionListener((ActionEvent event) -> {
            System.exit(0);
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
		fullscreen.addActionListener((ActionEvent e) -> {setSize(1920,1080);setLocation(2,3);});
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

