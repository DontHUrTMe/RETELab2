package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
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
		int nc = 0;
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		System.out.println("public static void print(IExampleStatemachine s) {");
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			
			
			if(content instanceof VariableDefinition)
			{
				VariableDefinition v = (VariableDefinition) content;
				System.out.println("System.out.println(\""+v.getName()+"= \" + s.getSCInterface().get"+v.getName()+"());");
			}
			else if (content instanceof EventDefinition)
			{
				EventDefinition e = (EventDefinition) content;
				System.out.println("System.out.println(\""+e.getName()+"= \" + s.getSCInterface().get"+e.getName()+"());");
			}
			

//			if(content instanceof State) {
//				State state = (State) content;
//				System.out.println(state.getName());
//				for (Transition e : state.getOutgoingTransitions())
//				{
//					State kifele = (State) e.getTarget();
//					System.out.println(state.getName() + " -> " + kifele.getName());
//				}
//				if (state.getOutgoingTransitions().size() == 0) {
//					System.out.println(state.getName()+" csapda");
//				}
//				
//				if (state.getName() == "") {
//					nc++;
//					state.setName("State" + nc);
//					System.out.println("State named: " + "State" + nc);
//				}
//
//			}
			
//			if(content instanceof VariableDefinition)
//			{
//				VariableDefinition v = (VariableDefinition) content;
//				System.out.println(v.getName());
//			}
//			else if (content instanceof EventDefinition)
//			{
//				EventDefinition e = (EventDefinition) content;
//				System.out.println(e.getName());
//			}
			
		}
		System.out.println("}");
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
