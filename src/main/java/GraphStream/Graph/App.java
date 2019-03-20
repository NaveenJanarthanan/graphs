package GraphStream.Graph;


import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.Prim;
import org.graphstream.algorithm.Dijkstra;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;


//import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import java.lang.Object;
import org.graphstream.graph.implementations.AbstractElement;
import org.graphstream.graph.implementations.AbstractGraph;

import org.graphstream.algorithm.APSP;
import org.graphstream.algorithm.APSP.APSPInfo;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.util.swing.*;
import org.graphstream.ui.swingViewer.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class App  {
	static Graph graph = new MultiGraph("Tut", false, true);
	static Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "weight");
	
	static APSP apsp = new APSP();
	
	static JSplitPane splitPane;
	
	static JFrame frame = new JFrame();
	
    static JPanel panel = new JPanel(new GridLayout()){
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(640, 480);
        }
    };
	
    
    private static boolean isFirstClick = true;
    private static boolean isSecondClick = false;
	
    private static boolean isFirst = true;
    
	public static void main(String args[]) {
		System.setProperty("org.gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer"); 
		
		
		BufferedReader reader;
	
		  
		  JButton buttonPrims = new JButton("Prims");
		  buttonPrims.addActionListener(
				  new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  String css = "edge .notintree {size:1px;fill-color:black;} " +
					   				 "edge .intree {size:3px;fill-color:green;}";
					   		
					    		graph.addAttribute("ui.stylesheet", css);
					    		Prim prim = new Prim("ui.class", "intree", "notintree");
					    		
					    		prim.init(graph);
					    		prim.compute(); 
					  }
				  });
		 
		  
		  JButton buttonDijkastras = new JButton("Djikastras");
		  
		  buttonDijkastras.addActionListener(
				  new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  	
						  	if(isFirst) {
						  	String node1 = JOptionPane.showInputDialog(null, "Enter Source Node:");
						  	String node2 = JOptionPane.showInputDialog(null, "Enter Target Node:");
						  	dijkstra.init(graph);
							dijkstra.setSource(graph.getNode(node1));
							dijkstra.compute();
							/*for (Edge edge : dijkstra.getTreeEdges())
								edge.addAttribute("ui.style", "fill-color: blue; size: 3px;"); */
							Node inputSecond = graph.getNode(node2);
							Node input = graph.getNode(node1);
					
							
							for (Edge node : dijkstra.getPathEdges(inputSecond)) { 
								node.addAttribute("ui.style", "fill-color: purple; size: 3px;");
								
							}
							
							isFirst= false; 
						  	
						  
						  	}
						  	else {
							  	String node1 = JOptionPane.showInputDialog(null, "Enter Source Node:");
							  	String node2 = JOptionPane.showInputDialog(null, "Enter Target Node:");
							  	dijkstra.init(graph);
								dijkstra.setSource(graph.getNode(node1));
								dijkstra.compute();
								/*for (Edge edge : dijkstra.getTreeEdges())
									edge.addAttribute("ui.style", "fill-color: blue; size: 3px;"); */
								Node inputSecond = graph.getNode(node2);
								Node input = graph.getNode(node1);
						
								
								for (Edge node : dijkstra.getPathEdges(inputSecond)) { 
									node.addAttribute("ui.style", "fill-color: purple; size: 3px;");
									
								}
								isFirst= true;
						  	}
				    		
					  }
				  });
		  
		  JButton buttonClear = new JButton("Original");
		  buttonClear.addActionListener(
				  new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						 String css = "edge .notintree {size:1px;fill-color:black;} " +
					   				 "edge .intree {size:1px;fill-color:black;}";
					   		
					    		graph.addAttribute("ui.stylesheet", css);
					    	for (Edge edge : dijkstra.getTreeEdges())
								edge.addAttribute("ui.style", "fill-color: black; size: 1px;"); 
					    	for(Edge ed : graph.getEachEdge())
					    		ed.addAttribute("ui.style", "fill-color: black; size: 1px");
					    	
					    	
						  
					  }
				  });
		  
		  
		  JButton buttonLayout = new JButton("Layout");
		  buttonLayout.addActionListener(
				  new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  if(isFirstClick) {
						  graph.addAttribute("ui.stylesheet", "edge { fill-color: pink; }node { fill-color: red;}");
						  isFirstClick = false;
						  isSecondClick = true;
						  }
						  else if(isSecondClick) {
							  graph.addAttribute("ui.stylesheet", "edge { fill-color: orange; }node { fill-color: purple;}");
							  
							  isSecondClick = false;
						  }
						  else {
							  graph.addAttribute("ui.stylesheet", "edge { fill-color: black; }node { fill-color: black;}");
							  isFirstClick = true;
						  }
					  }
				  });
		  
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        splitPane = new JSplitPane();
	        
	      //  getContentPane().setLayout(new Gridlayout());
	        
	        
	       /* JPanel panel1 = new JPanel(new GridLayout()){
	            @Override
	            public Dimension getPreferredSize() {
	                return new Dimension(100, 100);
	            }
	        }; */
	        
	        
	        frame.add(buttonLayout);
	        frame.add(buttonDijkastras);
	        frame.add(buttonPrims);
	        frame.add(buttonClear);
	        buttonClear.setBounds(10, 100, 90, 30);
	        buttonDijkastras.setBounds(10, 50, 90, 30);
	        buttonPrims.setBounds(10, 10, 90, 30);
	        buttonLayout.setBounds(10, 150, 90, 30);
	        
	        String filename = null;
	        File file;
	        
	      /*  if(0 < args.length) {
	        	
	        	filename = args[0];
	        	file = new File(filename);
	        }*/
		try {
			  
			
			reader = new BufferedReader(new FileReader("C:\\Users\\navee\\eclipse-workspace\\Graph\\src\\main\\java\\GraphStream\\Graph\\Graph.txt"));
			
			//"C:\\Users\\navee\\eclipse-workspace\\Graph\\src\\main\\java\\GraphStream\\Graph\\Graph.txt"
			String line = reader.readLine();
			
			while(line != null) {
				String st = line.replaceAll("\\s+","");
				
				String[] arrofStr = st.split(",",3);
				String a = arrofStr[0];
				String b = arrofStr[1];
				String c = arrofStr[2];
				
				String edge = a + b;
				
				
				//Creates nodes automatically if needed
				graph.setStrict(false);
				graph.setAutoCreate(true);
				
				
				//graph.addNode(a);
				//graph.addNode(b);
				graph.addEdge(edge, a, b, true).addAttribute("weight", c);
				
				for (Node n: graph.getEachNode()) {
					n.addAttribute("ui.label", "Node " + n.getId());
					n.addAttribute("ui.style", "fill-color: rgba(255,0,0,128); size: 10px; text-alignment: above; text-style: bold; "
							+ "text-background-mode: plain; text-background-color: yellow; text-offset: -5;");
					
				}
				for (Edge e : graph.getEachEdge())
					e.addAttribute("ui.label", "" + (int) e.getNumber("weight"));
				
				graph.addAttribute("ui.quality");
				graph.addAttribute("ui.antialias");
				//Edge e = graph.getEdge(edge);
				
				//e.setAttribute("weight", c);
				
				//double w = e.getNumber("weight");
				//System.out.println(w);
			 
				line = reader.readLine();
				
				
			}
			reader.close();
		}catch(IOException e) {
			e.printStackTrace();
		} 
		
		
		
	

		
		
		//Renders multiple edges between nodes
		System.setProperty("org.graphstream.ui.renderer",
		        "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		
		
		   		panel.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		      
	       
	       
		   		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		        ViewPanel viewPanel = viewer.addDefaultView(false);
		        viewer.enableAutoLayout();
		        panel.add(viewPanel);
		        frame.add(panel);
		        //frame.add(panel);
		        frame.pack();
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);
	        
		
    
	}


	
}


	

	
