package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.Scanner;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		
		Scanner in = new Scanner(System.in); 
		while (true)
		{
			String event = in.nextLine();
			switch(event)
			{
			case "start":
				s.raiseStart();
				s.runCycle();
				print(s);
				break;
			case "white":
				s.raiseWhite();
				s.runCycle();
				print(s);
				break;
			case "black":
				s.raiseBlack();
				s.runCycle();
				print(s);
				break;
			case "ping":
				s.raisePing();
				s.runCycle();
				print(s);
				break;
case "exit":
				System.exit(0);
				break;
			default:
				System.out.println("undefined");
				break;
			}
		}
	}

	public static void print(IExampleStatemachine s) {
System.out.println("WhiteTime= " + s.getSCInterface().getWhiteTime());
System.out.println("BlackTime= " + s.getSCInterface().getBlackTime());
System.out.println("Pong= " + s.getSCInterface().getPong());
}
}
