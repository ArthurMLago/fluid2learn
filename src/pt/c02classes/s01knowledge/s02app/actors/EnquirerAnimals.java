package pt.c02classes.s01knowledge.s02app.actors;

import java.util.HashMap;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IDeclaracao;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerAnimals implements IEnquirer {

	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		IObjetoConhecimento obj;
//		Carregar base de conhecimento:
        IBaseConhecimento bc = new BaseConhecimento();
//      Carregar os nomes de todos os animais:
        int Tentativa = 0;
        String nameList[] = bc.listaNomes();
//		Chutar que o animal desejado eh o primeiro:
		obj = bc.recuperaObjeto(nameList[Tentativa]);
//		Obter a primeira pergunta:
		IDeclaracao decl = obj.primeira();
		
		HashMap<String, String> map = new HashMap<String, String>();
		

//      Enquanto houverem perguntas:
		while (decl != null) {
//			Recolher a pergunta:
			String pergunta = decl.getPropriedade();
//			Variavel para armazenar a resposta:
			String resposta;
//			Se a pergunta ja foi respondida:
			if (map.containsKey(pergunta)){
//				A resposta eh oque ja foi respondido:
				resposta = map.get(pergunta);
			}else{
//				Se nao, perguntar:`	
				resposta = responder.ask(pergunta);
//				E guardar a resposta
				map.put(pergunta,resposta);
			}
			
//			A nossa resposta esperada para o animal que estamos chutando
			String respostaEsperada = decl.getValor();
//			
			if (resposta.equalsIgnoreCase(respostaEsperada)){
				decl = obj.proxima();
			}else{
				Tentativa++;
				obj = bc.recuperaObjeto(nameList[Tentativa]);
				decl = obj.primeira();
			}
			
				
		}
		
		boolean acertei = responder.finalAnswer(nameList[Tentativa]);
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");
		
		return acertei;

	}

}
