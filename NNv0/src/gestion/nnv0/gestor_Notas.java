
/*****************************************************************************
 * Source code information
 * -----------------------
 * Original author    Jesus Juan Aguilar
 * Package            gestion.nnv0
 * Created            23-12-2013
 * Filename           #gestor_notas.java
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
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import android.os.Environment;
import android.util.Log;

public class gestor_Notas {
	private static File f_notas = new File(Environment.getExternalStorageDirectory()+"/nonet" + "/notas.xml");
	

    /**
     * Metodo para guardar una nota 
     * @param nota: nota a añadir en el fichero xml
     */
	public static Boolean crearNota(Nota nota) throws IOException, JDOMException{
			Boolean ok = true;
			//Se crea un SAXBuilder para poder parsear el archivo
			SAXBuilder builder = new SAXBuilder();
	    if(!existNote(nota.getTitulo().trim())){
	        //Se crea el documento a traves del archivo
	        Document document = (Document) builder.build( f_notas );
	 
	        Element nota1 = new Element( "nota" );
	        Element titulo = new Element ( "titulo" );
	        titulo.setText(nota.getTitulo());
	        Element texto = new Element ( "texto" );
	        texto.setText(nota.getTexto());
		  	
	        
	        nota1.addContent( titulo );
	        nota1.addContent( texto );
	        
	        document.getRootElement().addContent(nota1);
	        
	      //Se escribe le archivo xml
	        XMLOutputter outputter = new XMLOutputter();
	        outputter.setFormat(Format.getPrettyFormat());
	        try{
	            outputter.output ( document, new FileOutputStream
	            		(Environment.getExternalStorageDirectory()+"/nonet" + "/notas.xml") );
	        } catch (Exception e){ e.getMessage(); }
	        
	    }else{
	    	ok = false;
	    }
	        
			return ok;
	}

	
	/**
     * Metodo para borrar una nota 
     * @param titulo: Titulo de la nota que se desea eliminar
     */
	public static void borrarNota(String titulo) throws JDOMException, IOException{
				
		//Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();
    
        //Se crea el documento a traves del archivo
        Document document = (Document) builder.build( f_notas );
        Element rootNode = document.getRootElement();
        
        List list = rootNode.getChildren( "nota" );
   	    Iterator iter= list.iterator();
        //Se recorre la lista de hijos de 'notas'
   	    while(iter.hasNext()){
   	    	Element nota = (Element) iter.next();
   	    	if(nota.getChildTextTrim("titulo").equals(titulo)){
   	    		iter.remove();Log.e("MSJ","1");}
   	    }
   	    
      //Se escribe le archivo xml
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        try{
            outputter.output ( document, new FileOutputStream
            		(Environment.getExternalStorageDirectory()+"/nonet" + "/notas.xml") );
        } catch (Exception e){ e.getMessage(); }
	    
	}
	
	
	
	/**
     * Metodo para modificar una nota 
     * @param titulo_vieja: Titulo de la nota que se desea eliminar
     * @param nueva: Nota que reemplazar a la anterior en el fichero xml 
     */
	public static void modificarNota(String titulo_vieja, Nota nueva){
		
		try {
			borrarNota(titulo_vieja);
			crearNota(nueva);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	/**
     * Metodo que devuelve una nota concreta
     * @param titulo: Titulo de la nota que se desea.
     * @return nota: Nota que se buscaba
     */
	public static Nota getNota(String titulo){
		
		Nota nota = new Nota("","");
		//Se crea un SAXBuilder para poder parsear el archivo
	    SAXBuilder builder = new SAXBuilder();
	    
	    try
	    {
	        //Se crea el documento a traves del archivo
	        Document document = (Document) builder.build( f_notas );
	 
	        //Se obtiene la raiz 'notas' 
	        Element rootNode = document.getRootElement();
	 
	        //Se obtiene la lista de hijos de la raiz 'notas'
	        List list = rootNode.getChildren( "nota" );
	 
	        //Se recorre la lista de hijos de 'notas'
	        for ( int i = 0; i < list.size(); i++ )
	        {
	            //Se obtiene el elemento 'nota'
	            Element notas = (Element) list.get(i);
	          //Se obtiene la lista de hijos del tag 'nota'
	            String nombre = notas.getChildTextTrim("titulo");
	            if(nombre.equals(titulo)){
	               nota.setTitulo(nombre);	
	               String texto = notas.getChildTextTrim("texto");
	               nota.setTexto(texto);
	               i = list.size();
	            }
	            
	        }
	    }catch ( IOException io ) {
	        System.out.println( io.getMessage() );
	    }catch ( JDOMException jdomex ) {
	        System.out.println( jdomex.getMessage() );
	    }

		
		return nota;
}

	
	/**
     * Metodo para devolver una lista del nombre de todas las nota  
     * @return miVector: Vector de Strings con todos los titulos de las notas.
     */
	public static Vector<String> getListaNotas(){
		
		Vector<String> miVector = new Vector<String>();
		
		//Se crea un SAXBuilder para poder parsear el archivo
	    SAXBuilder builder = new SAXBuilder();
	    
	    try
	    {
	        //Se crea el documento a traves del archivo
	        Document document = (Document) builder.build( f_notas );
	 
	        //Se obtiene la raiz 'notas' 
	        Element rootNode = document.getRootElement();
	 
	        //Se obtiene la lista de hijos de la raiz 'notas'
	        List list = rootNode.getChildren( "nota" );
	 
	        //Se recorre la lista de hijos de 'notas'
	        for ( int i = 0; i < list.size(); i++ )
	        {
	            //Se obtiene el elemento 'nota'
	            Element nota = (Element) list.get(i);
	          //Se obtiene la lista de hijos del tag 'tabla'
	            String nombre = nota.getChildTextTrim("titulo");
	            miVector.add(nombre);
	        }
	    }catch ( IOException io ) {
	        System.out.println( io.getMessage() );
	    }catch ( JDOMException jdomex ) {
	        System.out.println( jdomex.getMessage() );
	    }

		
		return miVector;
	}
	
	
	
	protected static Boolean existNote(String titulo){
		
		Vector<String> miVector = getListaNotas();
		Boolean ok = false;
		
		for(int i = 0; i< miVector.size(); i++)
			if(miVector.elementAt(i).equals(titulo))
				ok = true;
		
		
		return ok;

	}


	public static Vector<String> getListaNotasbyEt(String etiquetas) {
		// TODO Auto-generated method stub
		Vector<String> totalNotas =  getListaNotas();
		Vector<String> res =  new Vector();
		Nota nota = new Nota();
		
		for(int i = 0; i< totalNotas.size(); i++){
			
			nota = getNota(totalNotas.elementAt(i));
			if(gestor_Strings.haveAllEt(etiquetas, nota.getTexto())){
				res.add(nota.getTitulo());
			}
			
		}
		
		
		return res;
	}

}


