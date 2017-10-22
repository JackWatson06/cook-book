package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
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

public class AddFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private static JTextField urlField;
	private static JTextField fileField;
	private JLabel addLabel = new JLabel("Add");
	private JLabel cancelLabel = new JLabel("Cancel");
	private BufferedImage woodBack;
	private LineBorder blackline = new LineBorder(Color.black, 1);
	private Font chalkBoardFont = new Font("Dialog", Font.BOLD, 16);
	private JPanel buttonPanel = new JPanel();
	private JPanel textPanel = new JPanel();
	private ImageIcon imgIcon;
	
	public AddFrame(){
		super("Add A Recipe");
		try{
			woodBack = ImageIO.read(Main.class.getResource("/WoodBackgroundAdd.jpg"));
			imgIcon = new ImageIcon(ImageIO.read(Main.class.getResource("/ChefIcon.png")));
		}catch(IOException e){
			
		}
		setSize(360, 120);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(new JLabel(new ImageIcon(woodBack)));
		setIconImage(imgIcon.getImage());
		
		FlowLayout flo = new FlowLayout();
		BorderLayout bor = new BorderLayout();
		setLayout(bor);
		textPanel.setLayout(flo);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		textField = new textField();
		urlField = new urlField();
		fileField = new fileField();
		
		
		MouseListener mouse = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent component) {}
			@Override
			public void mouseEntered(MouseEvent component) {
				if(component.getSource().equals(addLabel)){
					addLabel.setForeground(darken(Color.white, 0.25F));
				}
				if(component.getSource().equals(cancelLabel)){
					cancelLabel.setForeground(darken(Color.white, 0.25F));
				}
			}
			@Override
			public void mouseExited(MouseEvent component) {
				if(component.getSource().equals(addLabel)){
					addLabel.setForeground(Color.white);
				}
				if(component.getSource().equals(cancelLabel)){
					cancelLabel.setForeground(Color.white);
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent component) {
				if(component.getSource().equals(addLabel)){
					String newFileText = fileField.getText();
					newFileText = newFileText.replace("\"", "");
					fileField.setText(newFileText);
					boolean condition1 = false;
					boolean condition2 = false;
					boolean condition3 = false;
					String text = textField.getText();
					String urlText = urlField.getText();
					String fileText = fileField.getText();
					String empty = new String("");
					String addRecipe = new String("Add A Recipe...");
					String recipeAlready = new String("Recipe Already Added...");
					String addUrl = new String("Add A URL Link...");
					String addFile = new String("Add A File Path...");
					String urlWork = new String("This URL Does Not Work...");
					String pathNot = new String("This File Was Not Found...");
					
					if(!recipeAlreadyAdded() && !text.equals(empty)){
						if(!text.equals(addRecipe) && !text.equals(recipeAlready)){
							condition1 = true;
						}
					}else if(text.equals(empty)){
						textField.setText("Add A Recipe...");
					}else{
						textField.setText("Recipe Already Added...");
					}
					
					if(!urlText.equals(empty) && !urlText.equals(urlWork) && !urlText.equals(addUrl)){
						if(testURL(urlText)){
							condition2 = true;
						}else{
							urlField.setText(urlWork);
						}
					}else if(!fileText.equals(empty) && !fileText.equals(pathNot) && !fileText.equals(addFile)){
						if(testFile(fileText)){
							condition2 = true;
						}else{
							fileField.setText(pathNot);
						}
					}else{
						condition2 = true;
					}
					
					
					if((!urlText.equals(empty) && !urlText.equals(urlWork) && !urlText.equals(addUrl)) && (!fileText.equals(empty) && !fileText.equals(pathNot) && !fileText.equals(addFile))){
						new DecisionFrame(true);
						if(!testFile(fileText)){
							fileField.setText(pathNot);
						}
					}else{
						condition3 = true;
					}
					
					if(condition1 && condition2 && condition3){
						if(!urlText.equals(empty) && !urlText.equals(urlWork) && !urlText.equals(addUrl)){
							addString(0);
						}else if(!fileText.equals(empty) && !fileText.equals(pathNot) && !fileText.equals(addFile)){
							addString(1);
						}else{
							addString(2);
						}

						dispose();
					}
					
				}
				if(component.getSource().equals(cancelLabel)){
					dispose();		
				}
				if(component.getSource().equals(textField)){
					if(textField.getText().equals(new String("Add A Recipe...")) || textField.getText().equals(new String("Recipe Already Added..."))){
						textField.setText("");
					}
				}
				if(component.getSource().equals(urlField)){
					if(urlField.getText().equals(new String("Add A URL Link...")) || urlField.getText().equals(new String("This URL Does Not Work..."))){
						urlField.setText("");
					}
				}
				if(component.getSource().equals(fileField)){
					if(fileField.getText().equals(new String("Add A File Path...")) || fileField.getText().equals(new String("This File Was Not Found..."))){
						fileField.setText("");
					}
				}
			}
			
		};
		
		buttonPanel.setOpaque(false);
		textPanel.setOpaque(false);
		
		textField.setText("Add A Recipe...");
		textField.setForeground(Color.white);
		textField.addMouseListener(mouse);
		textField.setBorder(blackline);
		
		urlField.setText("Add A URL Link...");
		urlField.setForeground(Color.white);
		urlField.addMouseListener(mouse);
		urlField.setBorder(blackline);
		
		fileField.setText("Add A File Path...");
		fileField.setForeground(Color.white);
		fileField.addMouseListener(mouse);
		fileField.setBorder(blackline);
		
		addLabel.setFont(chalkBoardFont);
		addLabel.setForeground(Color.white);
		addLabel.addMouseListener(mouse);
		
		cancelLabel.setFont(chalkBoardFont);
		cancelLabel.setForeground(Color.white);
		cancelLabel.addMouseListener(mouse);
		
		addLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		textPanel.setPreferredSize(new Dimension(260, 120));
		buttonPanel.setPreferredSize(new Dimension(100, 120));
		
		textPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
		buttonPanel.add(addLabel);
		buttonPanel.add(cancelLabel);
		
		textPanel.add(textField);
		textPanel.add(urlField);
		textPanel.add(fileField);
		
		
		add(textPanel, BorderLayout.LINE_START);
		add(buttonPanel, BorderLayout.LINE_END);

		setVisible(true);
		
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2,dim.height / 2 - this.getSize().height / 2);
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
	private boolean recipeAlreadyAdded(){
		for(int i = 0; i < Main.recipeList.size(); i++){
			if(textField.getText().equals(Main.recipeList.get(i).getText())){
				return true;
			}
		}
		return false;
	}
	private void addString(int add){
		if(add == 0){
			Main.recipeList.add(new JLabel(textField.getText()));
			Main.urlList.add(urlField.getText());
			Main.fileList.add(new String("None"));
		}else if(add == 1){
			Main.recipeList.add(new JLabel(textField.getText()));
			Main.urlList.add(new String("None"));
			Main.fileList.add(fileField.getText());
		}else{
			Main.recipeList.add(new JLabel(textField.getText()));
			Main.urlList.add(new String("None"));
			Main.fileList.add(new String("None"));
		}
		Main.saveFirst = true;
		Main.update = true;
		Main.updateSearch = true;

	}
	
	
    public class textField extends JTextField {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public textField() {
            super(20);
            try {
                image = ImageIO.read(Main.class.getResource("/ChalkBoardSearchAdd.jpg"));
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
    public class urlField extends JTextField {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public urlField() {
            super(20);
            try {
                image = ImageIO.read(Main.class.getResource("/ChalkBoardSearchAddURL.jpg"));
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
    public class fileField extends JTextField {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public fileField() {
            super(20);
            try {
                image = ImageIO.read(Main.class.getResource("/ChalkBoardSearchAddFile.jpg"));
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
