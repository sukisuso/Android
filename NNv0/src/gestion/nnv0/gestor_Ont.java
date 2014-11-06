
/*****************************************************************************
 * Source code information
 * -----------------------
 * Original author    Jesus Juan Aguilar
 * Package            gestion.nnv0
 * Created            10-01-2014
 * Filename           #gestor_Ont.java
 *
 *****************************************************************************/
// Package
///////////////
package gestion.nnv0;
//Imports
///////////////
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import android.os.Environment;


public class gestor_Ont {
	
	private static File f_ont = new File(Environment.getExternalStorageDirectory()+"/nonet" + "/ontologia.xml");
	private static final String NS = "http://www.semanticweb.org/NotesNetOnt#";
	private static Namespace rdfNs = Namespace.getNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	private static Namespace owlNs = Namespace.getNamespace("owl", "http://www.w3.org/2002/07/owl#");
	private static Namespace rdfsNs = Namespace.getNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
	
	/**
     * Metodo que devuelve una lista con las clases de dicho "directorio".
     * @param diretorio: Clase padre de la que listar las subclases.
     * @return 
	 * @throws IOException 
	 * @throws JDOMException 
     */
	public static Vector<String> getDirectorio(String directorio) throws JDOMException, IOException{

        Vector<String> miVector = new Vector<String>();
		
		//Se crea un SAXBuilder para poder parsear el archivo
	    SAXBuilder builder = new SAXBuilder();
	    
	        //Se crea el documento a traves del archivo
	        Document document = (Document) builder.build( f_ont );
	        Element rootNode = document.getRootElement();
	 
	        //Se obtiene la lista de hijos de la raiz 'Class'
	        List list = rootNode.getChildren( "Class", owlNs );
	 
	        if(directorio.equals("")){
	        //Se recorre la lista de hijos de 'Class'
	        for ( int i = 0; i < list.size(); i++ )
	        {
	 	      Element Clase = (Element) list.get(i);
	          //Se obtiene la lista de hijos del tag 'tabla'
	 	     Element clase2 = (Element) Clase.getChild("subClassOf",rdfsNs );
	 	     if(clase2 == null){
	          String nombre = Clase.getAttributeValue("about",rdfNs ,"Null");
	          nombre = nombre.substring(39); 
	          miVector.add(nombre);
	 	     }
	        }
	        }else{
	        	for ( int i = 0; i < list.size(); i++ )
		        {
	        	 Element Clase = (Element) list.get(i);
	        	 Element clase2 = (Element) Clase.getChild("subClassOf",rdfsNs );
		 	     if(clase2 != null){
		 	     if(clase2.getAttributeValue("resource",rdfNs ,"Null").substring(39).equals(directorio)){
		            String nombre = Clase.getAttributeValue("about",rdfNs ,"Null");
		            nombre = nombre.substring(39); 
		            miVector.add(nombre);
		 	     }}}
	        }
	        

	        
		return miVector;
	}
	
	/**
     * Metodo para eliminar una clase de la ontologia
     * @param Class: Clase que se desea eliminar.
	 * @throws IOException 
	 * @throws JDOMException 
     */
	public static String deleteClass(String Class) throws JDOMException, IOException{
		
		String padre = "";
		//Primero eliminamos la clase.
		//Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();    
		//Se crea el documento a traves del archivo
		Document document = (Document) builder.build( f_ont );
		Element rootNode = document.getRootElement();
		
		List list = rootNode.getChildren( "Class" , owlNs);
   	    Iterator iter= list.iterator();
  
   	    while(iter.hasNext()){
   	    	
	    	Element clase = (Element) iter.next();
	    	if(clase.getAttributeValue("about",rdfNs ,"Null").equals(NS + Class)){
	    		Element clase2 = (Element) clase.getChild("subClassOf",rdfsNs );
	    		if(clase2 != null)
	    			padre = clase2.getAttributeValue("resource",rdfNs ,"Null").substring(39);
	    		
	    		iter.remove();
	    		
	    			//Se escribe le archivo xml
	       	        XMLOutputter outputter = new XMLOutputter();
	       	        outputter.setFormat(Format.getPrettyFormat());
	       	        try{
	       	            outputter.output ( document, new FileOutputStream
	       	            		(Environment.getExternalStorageDirectory()+"/nonet" + "/ontologia.xml") );
	       	        } catch (Exception e){ e.getMessage(); }
	       	    
	    	}else{//Segundo eliminamos sus subClases.
	    		
	    		Element clase2 = (Element) clase.getChild("subClassOf",rdfsNs );
	    		if(clase2 != null)
	    		if(clase2.getAttributeValue("resource",rdfNs ,"Null").equals(NS + Class)){
	    			String x = clase.getAttributeValue("about",rdfNs ,"Null").substring(39);
		    		deleteClass(x);
	    			
 
		    		}  
	    	}
	    }
   	    //Se escribe le archivo xml
		return padre;
   	    
	}
	
	/**
     * Metodo para añadir una clase de la ontologia
     * @param Class: Clase que se desea añadir.
     * @param padre: padre de la clase que se desea añadir. 
	 * @throws IOException 
	 * @throws JDOMException 
     */
	public static void addClass(String Class, String padre) throws JDOMException, IOException{
		
		//Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();
    
        //Se crea el documento a traves del archivo
        Document document = (Document) builder.build( f_ont );
        
        //EXIST
        if(!existClass(Class)){
		if(padre.equals("")){ //No tiene padre
	
			Element clase = new Element( "Class", owlNs);
			clase.setAttribute("about" ,NS + Class, rdfNs);		
			document.getRootElement().addContent(clase);
			
		}else{// es una sub-clase
			
			Element clase = new Element( "Class", owlNs);
			clase.setAttribute("about" ,NS + Class, rdfNs);
			
			Element subclaseof = new Element( "subClassOf", rdfsNs);
			subclaseof.setAttribute("resource" ,NS + padre, rdfNs);
			
			clase.addContent( subclaseof );
	        
	        document.getRootElement().addContent(clase);
		}	
		
		//Se escribe le archivo xml
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        try{
            outputter.output ( document, new FileOutputStream
            		(Environment.getExternalStorageDirectory()+"/nonet" + "/ontologia.xml") );
        } catch (Exception e){ e.getMessage(); }
        }
   }
	
	/**
     * Metodo para comprobar si existe una clase de la ontologia
     * @param Class: Clase que se desea comprobar.
	 * @throws IOException 
	 * @throws JDOMException 
     */
	public static Boolean existClass(String Class) throws JDOMException, IOException{
		
		Boolean ok = false;
		
		//Primero eliminamos la clase.
		//Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();
		    
		//Se crea el documento a traves del archivo
		Document document = (Document) builder.build( f_ont );
		Element rootNode = document.getRootElement();
		
		List list = rootNode.getChildren( "Class" , owlNs);
   	    Iterator iter= list.iterator();
  
   	    while(iter.hasNext()){
   	    	
	    	Element clase = (Element) iter.next();
	    	if(clase.getAttributeValue("about",rdfNs ,"Null").equals(NS + Class)){
	    		iter.remove();
	    		ok = true;
	    	}}
		return ok;
	}
}
















