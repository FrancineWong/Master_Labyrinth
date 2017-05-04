package code;
/**
 * This class contains the definition for the pawn, the piece that represents each player for the Master Labyrinth game. 
 * It holds all information specific to each player. see instance variables for what data is held by pawn. 
 * Pawn requires two arguments at its instantiation: String name and int playerNumber
 * 
 */
import java.awt.Color;
import java.util.ArrayList;

public class Pawn implements Comparable<Pawn>{
	//the following instance variables are player dependent and do not change throughout the game. 
	//some must be set at the instantiation of each pawn (arguments).
	//some of the following variables are set by an argument: player number.
	//others are simply instantiated.
	//this class includes only getters for each.
	private String _name;
	private Card _card;
	private Color _color;
	
	//the following instance variables hold data that is common to every pawn.
	//their values are instantiated in the constructor.
	//this class includes both getters and setters for each.
	private int _wandCount;
	private int _score;
	private int _currentX;
	private int _currentY;
	private ArrayList<Token> _tokens;
	private ArrayList<ArrayList<Integer>> _possiblePath;
	


	public Pawn(String name,int playerNumber) {
		_wandCount=3;
		_score=0;
		initialize_currentX(playerNumber); 	//this sets the initial location of each pawn based on a player number. 
		initialize_currentY(playerNumber);	//The first player is 0, second player is 1 etc.
											//X=0 & Y=0 are in upper left hand corner of board, 
											//Player 0 will have location X=2,Y=2, player 1 will have location X=4,Y=2.
											//Player 2 will have location X=2,Y=4, player 3 will have location X=4,Y=4.
		_tokens= new ArrayList<Token>();
		
		
		_name=name;
		initialize_color(playerNumber);		//this sets the color of each pawn based on player number.
		_card=new Card();
	}
	
	public int get_wandCount() {return _wandCount;}
	public void set_wandCount(int x) {_wandCount = x;}
	
	public String get_name() {return _name;}
	
	public Color get_color() {return _color;}
	private void initialize_color(int x){
		if(x==0){
			_color=Color.WHITE;
		}
		if(x==1){
			_color=Color.RED;
		}
		if(x==2){
			_color=Color.BLUE;
		}
		if(x==3){
			_color=Color.GRAY;
		}
	}
	
	public int get_score() {return _score;}
	public void add_score(int x) {_score += x;}
	
	public int get_currentX() {return _currentX;}
	public void set_currentX(int x){
			_currentX=x;
	}
	private void initialize_currentX(int x) {
		if (x==0||x==2){
		_currentX = 2;
		}
		else{
			_currentX = 4;
		}
	}
	
	public int get_currentY() {return _currentY;}
	public void set_currentY(int x) {
			_currentY=x;
	}
	private void initialize_currentY(int x) {
		if (x==0||x==1){
			_currentY = 2;
			}
		else{
			_currentY = 4;
		}
	}
	

	public ArrayList<Token> get_tokens() {return _tokens;}

	public void add_token(Token t) {_tokens.add(t);}

	public Card get_card() {return _card;}

	public ArrayList<ArrayList<Integer>> get_possiblePath() {return _possiblePath;}
	public void set_possiblePath(ArrayList<ArrayList<Integer>> path) {_possiblePath = path;}

	@Override
	public int compareTo(Pawn o) {
		return Integer.compare(_score,o.get_score());
	}

	
	
	
	

}
