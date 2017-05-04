package code;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

public class GameApplet extends JFrame implements Runnable, KeyListener {

	// Game states
	protected static enum GameState {
		STARTUP, SLIDESELECT, DIRECTION, MOVESELECT, MOVE, MENU
	}

	protected GameState currentState = GameState.STARTUP;

	// Logic variables
	private int currentToken; // the game ends when token 25 is picked up,
								// currentToken allows such a functionality to
								// be represented here
	private int currentPlayer; // playerTurn keeps track of who's turn it is
	private String[] _playerNames; // numberOfPlayers allows a number of players
									// other than 4 (2-4)
	public Pawn[] players;
	private Board board;
	// End Logic vars

	// GUI variables
	protected Tile extra = null;
	protected JPanel info;
	protected MapPanel map;
	protected ExtraPanel extraPanel;
	public boolean canClose = false;
	private JPanel card;
	private JLabel cardTitle;
	private JLabel extraLabel;
	private JLabel first;
	private JLabel firstNum;
	private JLabel playerName;
	private JLabel score;
	private JLabel sec;
	private JLabel secNum;
	private JLabel third;
	private JLabel thirdNum;
	private JLabel wands;
	// End GUI

	/**
	 * Main Constructor
	 */
	public GameApplet(String[] playerNames) {
		// Logic setup
		currentState = GameState.STARTUP;
		currentToken = 1;
		currentPlayer = 0;
		_playerNames = playerNames;
		players = playerUp(_playerNames);
		board = new Board();
		// GUI Setup
		initComponents("Magic Labyrinth");
		this.setVisible(true);
		addKeyListener(this);
		startup();
	}

	private void startup() {
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Initializes the Pawns from the array of player names
	 */
	private Pawn[] playerUp(String[] playerNames) {
		Pawn[] output = new Pawn[playerNames.length];
		for (int i = 0; i < output.length; i++) {
			Pawn cur = new Pawn(playerNames[i], i);
			output[i] = cur;
		}
		return output;
	}

	/**
	 * Set up the JFrame
	 */
	private void initComponents(String title) {
		map = new MapPanel(this);// new MapPanel(this);
		info = new JPanel();
		extraLabel = new JLabel();
		extraPanel = new ExtraPanel();
		playerName = new JLabel();
		card = new JPanel();
		cardTitle = new JLabel();
		first = new JLabel();
		firstNum = new JLabel();
		sec = new JLabel();
		secNum = new JLabel();
		third = new JLabel();
		thirdNum = new JLabel();
		score = new JLabel();
		wands = new JLabel();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle(title);
		setCursor(new java.awt.Cursor(Cursor.DEFAULT_CURSOR));
		map.setBackground(new Color(0, 255, 0));
		GroupLayout mapLayout = new GroupLayout(map);
		map.setLayout(mapLayout);
		mapLayout.setHorizontalGroup(
				mapLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 450, Short.MAX_VALUE));
		mapLayout.setVerticalGroup(
				mapLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 450, Short.MAX_VALUE));

		info.setBackground(new java.awt.Color(153, 153, 153));

		playerName.setBackground(new java.awt.Color(153, 153, 153));
		playerName.setText("Player Name");

		card.setBackground(new java.awt.Color(156, 153, 8));
		card.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		cardTitle.setFont(new java.awt.Font("Luminari", 0, 24)); // NOI18N
		cardTitle.setHorizontalAlignment(SwingConstants.CENTER);
		cardTitle.setText("Ingredient List");

		first.setFont(new java.awt.Font("Malayalam MN", 0, 13)); // NOI18N
		first.setHorizontalAlignment(SwingConstants.CENTER);
		first.setText("jLabel2");

		firstNum.setFont(new java.awt.Font("Malayalam MN", 1, 13)); // NOI18N
		firstNum.setHorizontalAlignment(SwingConstants.CENTER);
		firstNum.setText("15");

		sec.setFont(new java.awt.Font("Malayalam MN", 0, 13)); // NOI18N
		sec.setHorizontalAlignment(SwingConstants.CENTER);
		sec.setText("jLabel2");

		secNum.setFont(new java.awt.Font("Malayalam MN", 1, 13)); // NOI18N
		secNum.setHorizontalAlignment(SwingConstants.CENTER);
		secNum.setText("15");

		third.setFont(new java.awt.Font("Malayalam MN", 0, 13)); // NOI18N
		third.setHorizontalAlignment(SwingConstants.CENTER);
		third.setText("jLabel2");

		thirdNum.setFont(new java.awt.Font("Malayalam MN", 1, 13)); // NOI18N
		thirdNum.setHorizontalAlignment(SwingConstants.CENTER);
		thirdNum.setText("15");

		GroupLayout cardLayout = new GroupLayout(card);
		card.setLayout(cardLayout);
		cardLayout.setHorizontalGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(cardLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(cardTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(cardLayout.createSequentialGroup()
								.addComponent(firstNum, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(first, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
						.addGroup(cardLayout.createSequentialGroup()
								.addComponent(secNum, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(sec, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
						.addGroup(cardLayout.createSequentialGroup()
								.addComponent(thirdNum, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(third, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		cardLayout.setVerticalGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(cardLayout.createSequentialGroup().addContainerGap().addComponent(cardTitle)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
						.addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(first, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(firstNum, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(sec, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(secNum, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(third, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(thirdNum, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));

		score.setText("Score:");

		wands.setText("Wands:");

		GroupLayout extraPanelLayout = new GroupLayout(extraPanel);
		extraPanel.setLayout(extraPanelLayout);
		extraPanelLayout.setHorizontalGroup(
				extraPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 55, Short.MAX_VALUE));
		extraPanelLayout.setVerticalGroup(
				extraPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

		extraLabel.setText("Extra Tile");

		GroupLayout infoLayout = new GroupLayout(info);
		info.setLayout(infoLayout);
		infoLayout.setHorizontalGroup(infoLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(infoLayout
				.createSequentialGroup()
				.addGroup(infoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(infoLayout.createSequentialGroup().addContainerGap()
								.addGroup(infoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(card, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(playerName, GroupLayout.Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(infoLayout.createSequentialGroup()
								.addGroup(infoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(infoLayout.createSequentialGroup().addGap(37, 37, 37).addComponent(
												score, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
								.addGroup(infoLayout.createSequentialGroup().addGap(37, 37, 37).addComponent(wands,
										GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
								.addGroup(infoLayout.createSequentialGroup().addContainerGap()
										.addComponent(extraPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18).addComponent(extraLabel, GroupLayout.PREFERRED_SIZE, 96,
												GroupLayout.PREFERRED_SIZE)))
								.addGap(0, 0, Short.MAX_VALUE)))
				.addContainerGap()));
		infoLayout
				.setVerticalGroup(
						infoLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addGroup(infoLayout.createSequentialGroup().addGap(25, 25, 25)
										.addComponent(playerName, GroupLayout.PREFERRED_SIZE, 24,
												GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(score, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(wands, GroupLayout.PREFERRED_SIZE, 39,
										GroupLayout.PREFERRED_SIZE)
				.addGroup(infoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(infoLayout.createSequentialGroup()
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(extraPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
						.addGroup(infoLayout.createSequentialGroup().addGap(24, 24, 24).addComponent(extraLabel)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)))
				.addComponent(card, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(map, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(info, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(map, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(info, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		pack();
		this.setResizable(false);
	}

	/**
	 * Main running loop of the function
	 */
	@Override
	public void run() {
		while (currentToken < 26) {
			switch (currentState) {
			case STARTUP:
				if (board.isSetup()) {
					currentState = GameState.SLIDESELECT;
				}
				break;
			case SLIDESELECT:
				// This is handled buy the map
				break;
			case DIRECTION:
				if (map.isCanSlide()) {
					if (map.getSlideSelect() > 2) {
						board.slideRow(map.getSlideValue(), map.getInvert());
						for (Pawn player : players) {
							if (player.get_currentY() == map.getSlideValue()) {
								if (map.getInvert()) {
									if(player.get_currentX()==0){
										player.set_currentX(6);
									}else{
									player.set_currentX(player.get_currentX() - 1);
									}
								} else {
									if(player.get_currentX()==6){
										player.set_currentX(0);
									}else{
									player.set_currentX(player.get_currentX() + 1);
									}
								}
							}
						}
					} else {
						board.slideCol(map.getSlideValue(), map.getInvert());
						for (Pawn player : players) {
							if (player.get_currentX() == map.getSlideValue()) {
								if (map.getInvert()) {
									if(player.get_currentY()==0){
										player.set_currentY(6);
									}else{
									player.set_currentY(player.get_currentY() - 1);
									}
								} else {
									if(player.get_currentY()==6){
										player.set_currentY(0);
									}else{
									player.set_currentY(player.get_currentY() + 1);
									}
								}
							}
						}
					}
					map.setCanSlide(false);
					currentState = GameState.MOVESELECT;
				}
				break;
			case MOVESELECT:
				if (map.isCanMove()) {
					boolean validPath=board.validPathBetween(players[currentPlayer].get_currentY(), players[currentPlayer].get_currentX(), map.highlightRow, map.highlightCol);
					// TODO: Fix this
					if (validPath) {  // Check if valid path
						System.out.println("successfully moved to "+map.highlightRow+", "+map.highlightCol);
						players[currentPlayer].set_currentX(map.highlightCol);
						players[currentPlayer].set_currentY(map.highlightRow);
						currentState = GameState.MOVE;
					}else{
						System.out.println("falied to move to "+map.highlightRow+", "+map.highlightCol+". Path is not valid");
					}
				map.setCanMove(false);
				}
				break;
			case MOVE:
				// Check if a token is at the current player location
				// If so, collect it and delete it from the tile
				// Then, go onto the next player
				int index = Board.getTileIndex(players[currentPlayer].get_currentY(), players[currentPlayer].get_currentX());
				boolean endGame = false;
				// If a token is on the spot
				if (board.getTile(index).getToken() != null && board.getTile(index).getToken().getKey() == currentToken) {
					// Create new duplicate token then delete the one on the board
					Token t= new Token(board.getTile(index).getToken().getKey());
					board.getTile(index).setToken(null);
					for (Token token : players[currentPlayer].get_card().getTokens()) {
						if (token.getKey() == currentToken) {
							players[currentPlayer].add_score(15);
						} else {
							players[currentPlayer].add_score(5);
						}
					}
					
					players[currentPlayer].add_token(t);
					if(currentToken == 20){
						currentToken = 25;
					}else if (currentToken == 25){
						// Game over
						endGame = true;
					} else {
						currentToken++;
					}
				}
				
				// Keep the game progress going 
				if (endGame) {
					currentState = GameState.MENU;
				} else {
					playerUpdate();
					currentState = GameState.SLIDESELECT;
				}
				break;
			case MENU:
				ArrayList<Pawn> output=new ArrayList<Pawn>();
				for(int i=0;i<players.length;i++){
					output.add(players[i]);
				}
				Collections.sort(output);
				Collections.reverse(output);
				for (int i=0;i<output.size();i++){
					if(i==0){
						System.out.println("First Place: "+output.get(i).get_name()+" with score of "+output.get(i).get_score());
					}
					if(i==1){
						System.out.println("Second Place: "+output.get(i).get_name()+" with score of "+output.get(i).get_score());
					}
					if(i==2){
						System.out.println("Third Place: "+output.get(i).get_name()+" with score of "+output.get(i).get_score());
					}
					if(i==3){
						System.out.println("Fourth Place: "+output.get(i).get_name()+" with score of "+output.get(i).get_score());
					}
				}
				System.exit(0);
				break;
			}
			// Update the panels
			map.update(board.getLayout(), players);
			extraPanel.update(this.board.getExtra());
			// Update info components
			String color="";
			switch (currentPlayer) {
			case 0:
				color = "Red";
				break;
			case 1:
				color = "Blue";
				break;
			case 2:
				color = "Yellow";
				break;
			case 3:
				color = "Green";
				break;
			}
			playerName.setText("Player Turn: " + players[currentPlayer].get_name() + "-- Go "+color);
			score.setText("Score: " + players[currentPlayer].get_score());
			wands.setText("Wands Left: " + players[currentPlayer].get_wandCount());
			ArrayList<Token> tokens = players[currentPlayer].get_card().getTokens();
			first.setText(tokens.get(0).getValue());
			firstNum.setText(tokens.get(0).getKey() + "");
			sec.setText(tokens.get(1).getValue());
			secNum.setText(tokens.get(1).getKey() + "");
			third.setText(tokens.get(2).getValue());
			thirdNum.setText(tokens.get(2).getKey() + "");
			// Run 60 FPS for giggles
			try {
				Thread.sleep(17);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void playerUpdate(){
		if(currentPlayer==_playerNames.length-1){
			currentPlayer=0;
		}else{
			currentPlayer++;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if (currentState == GameState.MOVESELECT) {
				map.moveUp();
			} else if (currentState == GameState.DIRECTION) {
				this.board.getExtra().rotatePath(false);
			}
			break;
		case KeyEvent.VK_DOWN:
			if (currentState == GameState.MOVESELECT) {
				map.moveDown();
			} else if (currentState == GameState.DIRECTION) {
				this.board.getExtra().rotatePath(true);
			}
			break;
		case KeyEvent.VK_LEFT:
			if (currentState == GameState.MOVESELECT) {
				map.moveLeft();
			} else if (currentState == GameState.DIRECTION) {
				map.flipInvert();
			} else {
				map.selectLeft();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (currentState == GameState.MOVESELECT) {
				map.moveRight();
			} else if (currentState == GameState.DIRECTION) {
				map.flipInvert();
			} else {
				map.selectRight();
			}
			break;
		case KeyEvent.VK_ENTER:
			if (currentState == GameState.SLIDESELECT || currentState == GameState.DIRECTION
					|| currentState == GameState.MOVESELECT) {
				map.submitLocation();
			}
			if (currentState == GameState.SLIDESELECT) {
				currentState = GameState.DIRECTION;
			}
			break;
		case KeyEvent.VK_ESCAPE:
			if (currentState == GameState.DIRECTION) {
				currentState = GameState.SLIDESELECT;
			}
			break;
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		default:
			System.out.println("Not a valid key command");
			break;
		}
	}
	
	//below method for test purposes only
	public ArrayList<Integer> scoreUp(){
		ArrayList<Pawn> output=new ArrayList<Pawn>();
		for(int i=0;i<players.length;i++){
			output.add(players[i]);
		}
		Collections.sort(output);
		Collections.reverse(output);
		for (int i=0;i<output.size();i++){
			if(i==0){
				System.out.println("First Place: "+output.get(i).get_name()+" with score of "+output.get(i).get_score());
			}
			if(i==1){
				System.out.println("Second Place: "+output.get(i).get_name()+" with score of "+output.get(i).get_score());
			}
			if(i==2){
				System.out.println("Third Place: "+output.get(i).get_name()+" with score of "+output.get(i).get_score());
			}
			if(i==3){
				System.out.println("Fourth Place: "+output.get(i).get_name()+" with score of "+output.get(i).get_score());
			}
		}
		
		ArrayList<Integer> returner=new ArrayList<Integer>();
		for(int i=0;i<output.size();i++){
			returner.add(output.get(i).get_score());
		}
		return returner;
	}
	
}
