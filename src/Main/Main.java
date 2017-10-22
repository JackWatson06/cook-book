package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Main extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	File file = new File("Recipes.txt");
	public static ArrayList<JLabel> recipeList = new ArrayList<JLabel>();
	public static ArrayList<String> urlList = new ArrayList<String>();
	public static ArrayList<String> fileList = new ArrayList<String>();
	public static boolean update = true;
	public static boolean saveFirst = false;
	private JTextField search;
	private JPanel area;
	private JLabel addLabel = new JLabel("Add");
	private JLabel deleteLabel = new JLabel("Delete");
	private JLabel closeLabel = new JLabel("Exit");
	private JPanel buttonPanel = new JPanel();
	private JPanel textPanel = new JPanel(new BorderLayout());
	private JScrollPane scroll;
	private boolean running = false;
	private BufferedImage img;
	private LineBorder blackline = new LineBorder(Color.black, 1);
	private Font chalkBoardFont = new Font("Dialog", Font.BOLD, 16);
	private Font RecipeFont = new Font("Times New Roman", Font.BOLD, 20);
	private ImageIcon imgIcon;
	private mouseListener mouseListener = new mouseListener();
	public static boolean updateSearch = true;
	private String lastSearch = new String("Search...");
	
	/**
	 * 
	 */
	public Main(){
		super("Recipe Ideas");
		try {
			img = ImageIO.read(Main.class.getResource("/WoodBackground.jpg"));
			imgIcon = new ImageIcon(ImageIO.read(Main.class.getResource("/ChefIcon.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setSize(600, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new windowListener());
		
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(imgIcon.getImage());
		
		FlowLayout flo = new FlowLayout();
		setLayout(flo);
		
		area = new textArea();
		search = new textField();
		scroll = new JScrollPane(area);
		
		scroll.setBorder(null);
		area.setLayout(new BoxLayout(area,BoxLayout.Y_AXIS));
		search.setForeground(Color.white);
		addLabel.setForeground(Color.white);
		deleteLabel.setForeground(Color.white);
		closeLabel.setForeground(Color.white);
		area.setForeground(Color.white);
		
		search.setText("Search...");
		addLabel.setFont(chalkBoardFont);
		addLabel.addMouseListener(mouseListener);
		deleteLabel.setFont(chalkBoardFont);
		deleteLabel.addMouseListener(mouseListener);
		closeLabel.setFont(chalkBoardFont);
		closeLabel.addMouseListener(mouseListener);
		search.addMouseListener(mouseListener);
        textPanel.setPreferredSize(new Dimension(380, 600));
		
		search.setBorder(blackline);
		area.setBorder(blackline);
		
		buttonPanel.setOpaque(false);
		textPanel.setOpaque(false);
		
		buttonPanel.add(search);
		buttonPanel.add(addLabel);
		buttonPanel.add(deleteLabel);
		buttonPanel.add(closeLabel);
		textPanel.add(scroll, BorderLayout.CENTER);
		add(buttonPanel);
		add(textPanel);
		

		setVisible(true);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2,dim.height / 2 - this.getSize().height / 2);

	}
	private void resetLabelColor(){
		for(int i = 0; i < recipeList.size(); i++){
				if(!urlList.get(i).equals(new String("None"))){
					recipeList.get(i).setForeground(Color.green);
				}else if(!fileList.get(i).equals(new String("None"))){
					recipeList.get(i).setForeground(Color.orange);
				}else{
					recipeList.get(i).setForeground(Color.white);
				}
		}
	}
	private Color darken(Color color, double percentage){
		
		int red = (int) Math.round(Math.max(0, color.getRed() - (255 * percentage)));
		int green = (int) Math.round(Math.max(0, color.getGreen() - (255 * percentage)));
		int blue = (int) Math.round(Math.max(0, color.getBlue() - (255 * percentage)));
		
		int alpha = color.getAlpha();
		
		return new Color(red, green, blue, alpha);
		
	}
	
	private void start(){
		running = true;
		Thread start = new Thread(this);
		start.start();
	}
	
	public void run() {
		while(running){
			if(update){
				try{
					update = false;
					if(!file.exists()){
						FileWriter fw = new FileWriter(file);
						fw.close();
					}
					if(saveFirst){
						saveData();
						saveFirst = false;
					}
					loadData();
					for(int i = 0; i < recipeList.size(); i++){
						JLabel currentLabel = recipeList.get(i);
						if(!urlList.get(i).equals(new String("None"))){
							currentLabel.setForeground(Color.green);
						}else if(!fileList.get(i).equals(new String("None"))){
							currentLabel.setForeground(Color.orange);
						}else{
							currentLabel.setForeground(Color.white);
						}
						currentLabel.setFont(RecipeFont);
						currentLabel.addMouseListener(mouseListener);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(!(search.getText().equals(lastSearch))){
				lastSearch = search.getText();
				updateSearch = true;
			}
			
			if(updateSearch){
				updateSearch = false;
				search();
			}
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}
	private void saveData() throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for(int i = 0; i < recipeList.size(); i++){
			bw.append(recipeList.get(i).getText() + "}");
			bw.append(urlList.get(i) + "}");
			bw.append(fileList.get(i) + "}");
			bw.append("\n");
		}
		bw.close();
	}
	private void loadData() throws IOException{
		int lineCount = 0;
		BufferedReader brl = new BufferedReader(new FileReader(file));
		BufferedReader br = new BufferedReader(new FileReader(file));
		while(brl.readLine() != null){
			lineCount++;
		}
		recipeList = new ArrayList<JLabel>();
		urlList = new ArrayList<String>();
		fileList = new ArrayList<String>();
		for(int i = 0; i < lineCount; i++){
			String currentLine = br.readLine();
			int lastCharacter = 0;
			int currentSection = 0;
			for(int j = 0; j < currentLine.length(); j++){
				if(currentLine.charAt(j) == '}'){
					String currentString = currentLine.substring(lastCharacter, j);
					if(currentSection == 0){
						recipeList.add(new JLabel(currentString));
					}else if(currentSection == 1){
						urlList.add(currentString);
					}else{
						fileList.add(currentString);
					}
					currentSection++;
					lastCharacter = j + 1;
				}
			}
		}
		brl.close();
		br.close();
	}
	
	private void search(){
		ArrayList<JLabel> searchList = new ArrayList<JLabel>();
		for(int i = 0; i < recipeList.size(); i++){
			searchList.add(recipeList.get(i));
		}
		
		if(!search.getText().equals(new String("Search..."))){
			int remove = 0;
			String searchString = search.getText();
			for(int i = 0; i < searchList.size() + remove; i++){
				if(!(searchList.get(i - remove).getText().startsWith(searchString))){
					searchList.remove(i - remove);
					remove++;
				}
			}
		}
		
		
		area.removeAll();
		for(int i = 0; i < searchList.size(); i++){
			area.add(searchList.get(i));
		}
		area.revalidate();
		area.repaint();
	}	
	public static void main(String[] args){
		Main main = new Main();
		main.start();
		
	}
	private class windowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent arg0) {}

		@Override
		public void windowClosing(WindowEvent arg0) {}

		@Override
		public void windowDeactivated(WindowEvent window) {
			resetLabelColor();
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {}

		@Override
		public void windowIconified(WindowEvent arg0) {}

		public void windowOpened(WindowEvent arg0) {}
		
	}
	private class mouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent component) {}
		@Override
		public void mouseEntered(MouseEvent component) {
			if(component.getSource().equals(addLabel)){
				addLabel.setForeground(darken(Color.white, 0.25F));
			}
			if(component.getSource().equals(deleteLabel)){
				deleteLabel.setForeground(darken(Color.white, 0.25F));
			}
			if(component.getSource().equals(closeLabel)){
				closeLabel.setForeground(darken(Color.white, 0.25F));
			}
			for(int i = 0; i < recipeList.size(); i++){
				if(component.getSource().equals(recipeList.get(i))){
					if(!urlList.get(i).equals(new String("None"))){
						recipeList.get(i).setForeground(darken(Color.green, 0.25F));
					}else if(!fileList.get(i).equals(new String("None"))){
						recipeList.get(i).setForeground(darken(Color.orange, 0.25F));
					}else{
						recipeList.get(i).setForeground(darken(Color.white, 0.25F));
					}
				}
			}
		}
		@Override
		public void mouseExited(MouseEvent component) {
			if(component.getSource().equals(addLabel)){
				addLabel.setForeground(Color.white);
			}
			if(component.getSource().equals(deleteLabel)){
				deleteLabel.setForeground(Color.white);
			}
			if(component.getSource().equals(closeLabel)){
				closeLabel.setForeground(Color.white);
			}
			for(int i = 0; i < recipeList.size(); i++){
				if(component.getSource().equals(recipeList.get(i))){
					if(!urlList.get(i).equals(new String("None"))){
						recipeList.get(i).setForeground(Color.green);
					}else if(!fileList.get(i).equals(new String("None"))){
						recipeList.get(i).setForeground(Color.orange);
					}else{
						recipeList.get(i).setForeground(Color.white);
					}
				}
			}
		}
		@Override
		public void mousePressed(MouseEvent component) {}
		@Override
		public void mouseReleased(MouseEvent component) {
			if(component.getButton() == 1){
				if(component.getSource().equals(addLabel)){
					new AddFrame();
				}
				if(component.getSource().equals(deleteLabel)){
					new DeleteFrame();
				}
				if(component.getSource().equals(closeLabel)){
					System.exit(0);
				}
				if(component.getSource().equals(search)){
					if(search.getText().equals(new String("Search..."))){
						search.setText("");
					}
				}
				for(int i = 0; i < recipeList.size(); i++){
					if(component.getSource().equals(recipeList.get(i))){
						if(!urlList.get(i).equals(new String("None"))){
							Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
							if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
								try {
									URL currentURL = new URL(urlList.get(i));
									desktop.browse(currentURL.toURI());
								} catch (Exception e) {
									new DoesNotWork(true);
								}
							}
						}
						if(!fileList.get(i).equals(new String("None"))){
							try {
								if (Desktop.isDesktopSupported()) {
									Desktop.getDesktop().open(new File(fileList.get(i)));
								}
							} catch(Exception e) {
								new DoesNotWork(false);
							}
						}
					}
				}
			}
			if(component.getButton() == 3){
				for(int i = 0; i < recipeList.size(); i++){
					if(component.getSource().equals(recipeList.get(i))){
						if(!urlList.get(i).equals(new String("None"))){
							new RightClickFrame(recipeList.get(i), urlList.get(i), fileList.get(i), component.getXOnScreen(), component.getYOnScreen(), 0);
						}else if(!fileList.get(i).equals(new String("None"))){
							new RightClickFrame(recipeList.get(i), urlList.get(i), fileList.get(i), component.getXOnScreen(), component.getYOnScreen(), 1);
						}else{
							new RightClickFrame(recipeList.get(i), urlList.get(i), fileList.get(i), component.getXOnScreen(), component.getYOnScreen(), 2);
						}

					}
				}
			}
		}
		
	}
    private class textArea extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public textArea() {
            super();
            try {
                image = ImageIO.read(Main.class.getResource("/ChalkBoard.jpg"));
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
    private class textField extends JTextField {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public textField() {
            super(20);
            try {
                image = ImageIO.read(Main.class.getResource("/ChalkBoardSearch.jpg"));
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
