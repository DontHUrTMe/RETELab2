package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.Scanner;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		System.out.print("public static void main(String[] args) throws IOException {\r\n" + 
				"		ExampleStatemachine s = new ExampleStatemachine();\r\n" + 
				"		s.setTimer(new TimerService());\r\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
				"		s.init();\r\n" + 
				"		s.enter();\r\n" + 
				"		\r\n" + 
				"		Scanner in = new Scanner(System.in); \r\n" + 
				"		while (true)\r\n" + 
				"		{\r\n" + 
				"			String event = in.nextLine();\r\n" + 
				"			switch(event)\r\n" + 
				"			{\r\n" );
		
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof EventDefinition)
			{
				EventDefinition e = (EventDefinition) content;
				System.out.print(
						"			case \""+e.getName()+"\":\r\n" + 
						"				s.raise"+capitalize(e.getName())+"();\r\n" + 
						"				s.runCycle();\r\n" + 
						"				print(s);\r\n" + 
						"				break;\r\n");
			}			
		}
		System.out.print("case \"exit\":\r\n" + 
		"				System.exit(0);\r\n" + 
		"				break;\r\n" + 
		"			default:\r\n" + 
		"				System.out.println(\"undefined\");\r\n" + 
		"				break;\r\n" + 
		"			}\r\n" + 
		"		}\r\n" + 
		"	}\r\n" + 
		"\r\n" + 
		"	public static void print(IExampleStatemachine s) {\r\n");
		iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof VariableDefinition)
			{
				VariableDefinition v = (VariableDefinition) content;
				System.out.println("System.out.println(\""+capitalize(v.getName())+"= \" + s.getSCInterface().get"+capitalize(v.getName())+"());");
			}			
		}
		System.out.print("}");
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
	public static String capitalize(String str)
	{
		String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
		return cap;
	}
}
