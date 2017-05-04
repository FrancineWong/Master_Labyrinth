package tests;

import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.Test;
import code.Pawn;

public class PawnTests {
	
	public PawnTests(){
		
	}
	
	public Pawn genericTestCode(){
		Random r=new Random();
		int x =r.nextInt(4);
		Pawn pawn=new Pawn("Maxwell",x);
		return pawn;
		
	}
	
	@Test public void Test00(){
		Pawn pawn=genericTestCode();
		String actual=pawn.get_name();
		assertTrue("the expected value was Maxwell but the actual value was "+actual, actual=="Maxwell");
	}
	@Test public void Test01(){
		Pawn pawn=genericTestCode();
		Random r=new Random();
		int expected=r.nextInt();
		pawn.set_wandCount(expected);
		int actual=pawn.get_wandCount();
		assertTrue("The expected output was "+expected+" but the actual output was "+actual,actual==expected);
	}
	@Test public void Test02(){
		Pawn pawn=genericTestCode();
		Random r=new Random();
		int expected=r.nextInt(7);
		pawn.set_currentX(expected);
		int actual=pawn.get_currentX();
		assertTrue("The expected output was "+expected+" but the actual output was "+actual,actual==expected);
	}
	@Test public void Test03(){
		Pawn pawn=genericTestCode();
		Random r=new Random();
		int expected=r.nextInt(7);
		pawn.set_currentY(expected);
		int actual=pawn.get_currentY();
		assertTrue("The expected output was "+expected+" but the actual output was "+actual,actual==expected);
	}
	@Test public void Test04(){
		Pawn pawn=genericTestCode();
		Random r=new Random();
		int expected=r.nextInt();
		pawn.add_score(expected);;
		int actual=pawn.get_score();
		assertTrue("The expected output was "+expected+" but the actual output was "+actual,actual==expected);
	}
	
}
