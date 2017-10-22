package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class RightClickFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JTextField urlField;
	private static JTextField fileField;
	private JLabel save = new JLabel("Save");
	private JLabel delete = new JLabel("Delete");
	private JLabel cancel = new JLabel("Cancel");
	private JLabel print = new JLabel("Print");
	private JPanel textPanel = new JPanel();
	private JPanel savePanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private BufferedImage img;
	private ImageIcon imgIcon;
	private Font chalkBoardFont = new Font("Dialog", Font.BOLD, 16);
	private LineBorder blackline = new LineBorder(Color.black, 1);

	public RightClickFrame(JLabel label, String url, String file, int mouseX, int mouseY, int state){
		super("Edit");
		try{
			img = ImageIO.read(Main.class.getResource("/WoodBackgroundRightClick.jpg"));
			imgIcon = new ImageIcon(ImageIO.read(Main.class.getResource("/ChefIcon.png")));
		}catch(IOException e){
			
		}
		setSize(360, 135);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(imgIcon.getImage());
		
		setLayout(new BorderLayout());
		textPanel.setLayout(new FlowLayout());
		savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
		buttonPanel.setLayout(new FlowLayout());
		
		urlField = new urlField();
		fileField = new fileField();
		
		
		MouseListener mouse = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent component) {}

			@Override
			public void mouseEntered(MouseEvent component) {
				if(component.getSource().equals(save)){
					save.setForeground(darken(Color.white, 0.25F));
				}
				if(component.getSource().equals(delete)){
					delete.setForeground(darken(Color.white, 0.25F));
				}
				if(component.getSource().equals(cancel)){
					cancel.setForeground(darken(Color.white, 0.25F));
				}
				if(component.getSource().equals(print)){
					print.setForeground(darken(Color.white, 0.25F));
				}
			}

			@Override
			public void mouseExited(MouseEvent component) {
				if(component.getSource().equals(save)){
					save.setForeground(Color.white);
				}
				if(component.getSource().equals(delete)){
					delete.setForeground(Color.white);
				}
				if(component.getSource().equals(cancel)){
					cancel.setForeground(Color.white);
				}
				if(component.getSource().equals(print)){
					print.setForeground(Color.white);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent component) {
				if(component.getSource().equals(save)){
					boolean condition1 = false;
					boolean condition2 = false;
					String newFileText = fileField.getText();
					newFileText = newFileText.replace("\"", "");
					fileField.setText(newFileText);
					String urlText = urlField.getText();
					String fileText = fileField.getText();
					String empty = new String("");
					String saved = new String("Saved...");
					String addUrl = new String("Add A URL Link...");
					String addFile = new String("Add A File Path...");
					String urlWork = new String("This URL Does Not Work...");
					String pathNot = new String("This File Was Not Found...");
					
					if(!urlText.equals(empty) && !urlText.equals(urlWork) && !urlText.equals(addUrl) && !urlText.equals(saved)){
						if(testURL(urlText)){
							condition1 = true;
						}else{
							urlField.setText(urlWork);
						}
					}else if(!fileText.equals(empty) && !fileText.equals(pathNot) && !fileText.equals(addFile) && !fileText.equals(saved)){
						if(testFile(fileText)){
							condition1 = true;
						}else{
							fileField.setText(pathNot);
						}
					}else{
						condition1 = true;
					}
					
					
					if((!urlText.equals(empty) && !urlText.equals(urlWork) && !urlText.equals(addUrl) && !urlText.equals(saved)) && (!fileText.equals(empty) && !fileText.equals(pathNot) && !fileText.equals(addFile) && !fileText.equals(saved))){
						new DecisionFrame(false);
						if(!testFile(fileText)){
							fileField.setText(pathNot);
						}
					}else{
						condition2 = true;
					}
					
					if(condition1 && condition2){
						if(!urlText.equals(empty) && !urlText.equals(urlWork) && !urlText.equals(addUrl) && !urlText.equals(saved)){
							save(label, 0);
						}else if(!fileText.equals(empty) && !fileText.equals(pathNot) && !fileText.equals(addFile) && !fileText.equals(saved)){
							save(label, 1);
						}else{
							save(label, 2);
						}
					}
				}
				if(component.getSource().equals(cancel)){
					dispose();
				}
				if(component.getSource().equals(delete)){
					doubleCheck(label);
				}
				if(component.getSource().equals(urlField)){
					if(urlField.getText().equals(new String("Add A URL Link...")) || urlField.getText().equals(new String("This URL Does Not Work...")) || urlField.getText().equals(new String("Saved..."))){
						urlField.setText("");
					}
				}
				if(component.getSource().equals(fileField)){
					if(fileField.getText().equals(new String("Add A File Path...")) || fileField.getText().equals(new String("This File Was Not Found...")) || fileField.getText().equals(new String("Saved..."))){
						fileField.setText("");
					}
				}
				if(component.getSource().equals(print)){
					print();
				}
			}
			
		};
		urlField.addMouseListener(mouse);
		fileField.addMouseListener(mouse);
		save.addMouseListener(mouse);
		delete.addMouseListener(mouse);
		cancel.addMouseListener(mouse);
		print.addMouseListener(mouse);
		
		urlField.setForeground(Color.white);
		fileField.setForeground(Color.white);
		save.setForeground(Color.white);
		delete.setForeground(Color.white);
		cancel.setForeground(Color.white);
		print.setForeground(Color.white);
		
		urlField.setBorder(blackline);
		fileField.setBorder(blackline);
		save.setFont(chalkBoardFont);
		delete.setFont(chalkBoardFont);
		cancel.setFont(chalkBoardFont);
		print.setFont(chalkBoardFont);
		
		textPanel.setPreferredSize(new Dimension(260, 120));
		save.setPreferredSize(new Dimension(100, 120));
		
		textPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		buttonPanel.setOpaque(false);
		textPanel.setOpaque(false);
		savePanel.setOpaque(false);
		
		textPanel.add(urlField);
		textPanel.add(fileField);
		
		savePanel.add(Box.createRigidArea(new Dimension(0, 12)));
		savePanel.add(save);

		if(state == 1){
			//buttonPanel.add(print);
			//buttonPanel.add(Box.createRigidArea(new Dimension(12,0)));
		}
		buttonPanel.add(delete);
		buttonPanel.add(Box.createRigidArea(new Dimension(8,36)));
		buttonPanel.add(cancel);
		
		add(textPanel, BorderLayout.LINE_START);
		add(savePanel, BorderLayout.LINE_END);
		add(buttonPanel, BorderLayout.PAGE_END);
		
		if(state == 0){
			urlField.setText(url);
			fileField.setText("Add A File Path...");
		}else if(state == 1){
			urlField.setText("Add A URL Link...");
			fileField.setText(file);
		}else{
			urlField.setText("Add A URL Link...");
			fileField.setText("Add A File Path...");
		}
		
		setVisible(true);
		setResizable(false);

		setLocation(mouseX, mouseY);
	}
	private void doubleCheck(JLabel label){
		new DoubleCheckFrame(this, label);
	}
	private Color darken(Color color, double percentage){
		
		int red = (int) Math.round(Math.max(0, color.getRed() - (255 * percentage)));
		int green = (int) Math.round(Math.max(0, color.getGreen() - (255 * percentage)));
		int blue = (int) Math.round(Math.max(0, color.getBlue() - (255 * percentage)));
		
		int alpha = color.getAlpha();
		
		return new Color(red, green, blue, alpha);
		
	}
	public static void changeFields(boolean remove){
		if(remove){
			fileField.setText("Add A File Path...");
		}else{
			urlField.setText("Add A URL Link...");
		}
	}
	private boolean testFile(String file){
		if(Files.exists(Paths.get(file))) { 
		    return true;
		}
		return false;
	}
	private boolean testURL(String url){
		try {
		      HttpURLConnection.setFollowRedirects(false);
		      HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		      con.setRequestMethod("HEAD");
		      con.connect();
		      return true;
		    }
		    catch (Exception e) {
		       return false;
		   }
	}
	private void print(){
		try{
			
		}catch(Exception e){
			new PrintFrame();
		}
	}
	public void delete(String delete){
		for(int i = 0; i < Main.recipeList.size(); i++){
			if(delete.equals(Main.recipeList.get(i).getText())){
				Main.recipeList.remove(i);
				Main.urlList.remove(i);
				Main.fileList.remove(i);
			}
		}
		Main.saveFirst = true;
		Main.update = true;
		Main.updateSearch = true;
	}
	public void save(JLabel label, int num){
		for(int i = 0; i < Main.recipeList.size(); i++){
			if(label.getText().equals(Main.recipeList.get(i).getText())){
				if(num == 0){
					Main.urlList.set(i, urlField.getText());
					Main.fileList.set(i, new String("None"));
				}else if(num == 1){
					Main.urlList.set(i, new String("None"));
					Main.fileList.set(i, fileField.getText());
				}else{
					Main.urlList.set(i, new String("None"));
					Main.fileList.set(i, new String("None"));
				}
			}
		}
		if(num == 0){
			fileField.setText("Saved...");
			buttonPanel.removeAll();
			buttonPanel.add(delete);
			buttonPanel.add(Box.createRigidArea(new Dimension(8,36)));
			buttonPanel.add(cancel);
			buttonPanel.revalidate();
			buttonPanel.repaint();
		}else if(num == 1){
			urlField.setText("Saved...");
			buttonPanel.removeAll();
			//buttonPanel.add(print);
			//buttonPanel.add(Box.createRigidArea(new Dimension(12,0)));
			buttonPanel.add(delete);
			buttonPanel.add(Box.createRigidArea(new Dimension(8,36)));
			buttonPanel.add(cancel);
			buttonPanel.revalidate();
			buttonPanel.repaint();
		}else{
			urlField.setText("Saved...");
			fileField.setText("Saved...");
			buttonPanel.removeAll();
			buttonPanel.add(delete);
			buttonPanel.add(Box.createRigidArea(new Dimension(8,36)));
			buttonPanel.add(cancel);
			buttonPanel.revalidate();
			buttonPanel.repaint();
		}
		Main.saveFirst = true;
		Main.update = true;
		Main.updateSearch = true;
	}
	private class urlField extends JTextField {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public urlField() {
            super(20);
            try {
                image = ImageIO.read(Main.class.getResource("/ChalkBoardSearchURLRightClick.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public boolean isOpaque() {
            return false;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            if (image != null) {
                g2d.drawImage(image, 0, 0, this);    
            }
            super.paintComponent(g2d);
            g2d.dispose();
        }

    }
	private class fileField extends JTextField {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public fileField() {
            super(20);
            try {
                image = ImageIO.read(Main.class.getResource("/ChalkBoardSearchFileRightClick.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public boolean isOpaque() {
            return false;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            if (image != null) {
                g2d.drawImage(image, 0, 0, this);    
            }
            super.paintComponent(g2d);
            g2d.dispose();
        }

    }
	
}
