import static spark.Spark.get;
import static spark.Spark.post;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class Main {
	public static void main(String[] args) {
		get("/impressoras", (req, res) -> {
			return "callback(\'"+writeListToJsonArray(ImpressoraUtils.listarImpressoras())+"\');";
		});
		post("/impressao", (request, response) -> {
			 final ObjectMapper mapper = new ObjectMapper();
			 JsonNode root2 = mapper.readTree(request.queryParams().toString());
			 RequisicaoImpressao requisicaoImpressao = new RequisicaoImpressao();
			 root2.forEach((r) -> {
				 requisicaoImpressao.setNome(r.get("nome").asText());
				 requisicaoImpressao.setTexto(r.get("texto").asText());
			 });
			 ImpressoraUtils impressao = new ImpressoraUtils();
			 impressao.mandarImprimir(requisicaoImpressao);
			 return "callback();";
		});
	}
	
	public static String writeListToJsonArray(List<Impressora> list) throws IOException {  
	    final ByteArrayOutputStream out = new ByteArrayOutputStream();
	    final ObjectMapper mapper = new ObjectMapper();

	    mapper.writeValue(out, list);

	    final byte[] data = out.toByteArray();
	    return new String(data);
	}
	
//	public static void main(String[] args) throws IOException 
//	{
//	    ObjectMapper objectMapper = new ObjectMapper();
//	    String json= "{\"insertDataRequest\":{\"id\":98, \"name\":\"Mercer Island\",\"age\":12,\"designation\":\"SEE\"}}";
//	    JsonNode root2 = objectMapper.readTree(json);
//	    for (JsonNode rootnode : root2.get("insertDataRequest")) {
//	      if (rootnode.has("id")) {
//	          int id = rootnode.get("id").intValue();
//	          System.out.println(id);
//	      }     
//	      if (rootnode.has("name")) {
//	          String name = rootnode.get("name").toString().replace("\"", "");
//	          System.out.println(name);
//	      }   
//
//	      if (rootnode.has("age")) {
//	          int age = rootnode.get("age").intValue();
//	          System.out.println(age);
//	      }
//
//	      if (rootnode.has("designation")) {
//	          String designation = rootnode.get("designation").toString().replace("\"", "");
//	          System.out.println(designation);
//	      }    
//	    }
//	} 

	
}
