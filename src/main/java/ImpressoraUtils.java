import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

public class ImpressoraUtils {
	
	private static PrintService impressora;
	private String printer_name;
	private String texto;
	
	
	
	
	public static List<Impressora> listarImpressoras() {
		ArrayList<Impressora> listaImpressoras = new ArrayList<Impressora>();
		Arrays.asList(PrinterJob.lookupPrintServices()).forEach(
				(p) -> listaImpressoras.add(new Impressora(p.getName())));
		return listaImpressoras;
	}

	public void mandarImprimir(RequisicaoImpressao impressora) {
		detectaImpressoras(impressora);
	}
	
	
	
	public  void detectaImpressoras(RequisicaoImpressao requisicaoImpressao) {

		try {
			printer_name = requisicaoImpressao.getNome();
			if(printer_name==null){
				printer_name = "\\\\cd0001si200\\DF7436PR002";
				System.out.println("não recuperou parametro printer_name");
			}else{
				System.out.println("recuperou parametro printer_name");
			}
			texto = requisicaoImpressao.getTexto();
			if(texto==null){
				texto = "\\\\cd0001si200\\DF7436PR002 texto";
				System.out.println("não recuperou parametro texto");
			}else{
				System.out.println("recuperou parametro texto");
			}
			
			
			DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
			PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);
			for (PrintService p : ps) {

				System.out.println("Impressora encontrada: " + p.getName());

				if (p.getName().contains(printer_name)) {

					System.out.println("Impressora Selecionada: " + p.getName());
					impressora = p;
					break;

				}

			}
			if(imprime(texto)){
				System.out.println("Imprimiu com sucesso");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public synchronized boolean imprime(String texto) {

		// se nao existir impressora, entao avisa usuario
		// senao imprime texto
		if (impressora == null) {

			String msg = "Nennhuma impressora foi encontrada. Instale uma impressora padrão \r\n(Generic Text Only) e reinicie o programa.";
			System.out.println(msg);

		} else {

			try {
				System.out.println("Chegou aqui: "+ impressora.getName() );
				DocPrintJob dpj = impressora.createPrintJob();
				System.out.println("Chegou aqui no texto: "+ texto );
				InputStream stream = new ByteArrayInputStream(texto.getBytes());

				DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
				Doc doc = new SimpleDoc(stream, flavor, null);
				System.out.println("Chegou aqui no doc: "+ stream.toString() + " / " + flavor.toString() );
				System.out.println("doc: "+doc );
				dpj.print(doc, null);

				return true;

			} catch (PrintException e) {

				e.printStackTrace();

			}

		}

		return false;

	}
}
