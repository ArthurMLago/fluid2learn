package pt.c02classes.s01knowledge.s02app.actors;

import java.util.*;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

//Classe para armazenar uma posicao no mapa:
class MapPoint {
	public int X,Y;
	//Construtor a partir de posicao:
	public MapPoint(int X, int Y){
		this.X = X;
		this.Y = Y;
	}
	//Construtor a partir de um ponto somado a uma posicao:
	public MapPoint(MapPoint point, int X, int Y){
		this.X = point.X + X;
		this.Y = point.Y + Y;
	}
	
	//Obter a distancia de um outro ponto ate este:
	public MapPoint distanceFrom(MapPoint point){	
		return new MapPoint(X - point.X, Y - point.Y);
	}
	
	//Verificar igualdade entre posicoes mpPoint
	public boolean mpIsEqual(MapPoint point)
	{	  
		if((point.X == this.X) && (point.Y == this.Y))		
			return true;
		
		return false;
		
	}
	
}

public class EnquirerMaze implements IEnquirer {

	IResponder responder;
	//Pilha para guardar as posicoes:
	Stack<MapPoint> positionHistory;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		//Inicializar a pilha e adicionar a posicao inicial(0,0):
		positionHistory = new Stack<MapPoint>();
		positionHistory.push(new MapPoint(0,0));
		if(step())
		{	
			System.out.println("HELLO !");
			/*imprimir a pilha*/
			while(positionHistory.empty() == false)
			{
				System.out.println("X: " + positionHistory.peek().X + "Y: "+ positionHistory.peek().Y);
				positionHistory.pop();
			}
			return true;
		}
		else
			return false;
	}
	
	public boolean step(){
		
		/*Verificar se o jogador ja passou por aquela posicao*/
		
		//Recebe os elementos da pilha de posicoes enumerados
		Enumeration<MapPoint> elements = positionHistory.elements();
		/*
		//Comparar o ultimo elemento da pilha com o restante
		while(elements.hasMoreElements())
		{	
			//Se ja passou por alguma posicao, descartamos esse caminho
			if(positionHistory.peek().mpIsEqual(elements.nextElement()))
				return false;
		}*/

		//Verificar se chegmaos a saida:
		if (responder.ask("aqui").equals("saida")){
			return true;
		}
		//Verificar disponibilidade de caminho na ordem: oeste,norte,leste,sul:
		if (responder.ask("oeste").equals("passagem")){
			//Mover para oeste:
			responder.move("oeste");
			//Colocar a nova posicao na pilha(1 ponto a esquerda):
			positionHistory.push(new MapPoint(positionHistory.peek(),-1,0));
			//Chamar a recursão, e se encontrar a saida, terminar retornar essa chamada recursiva tambem:
			if (step() == true){
				return true;
			}else{
				//Se não encontrou saida, entao o caminho nao eh indo para este lado, volta um movimento:
				responder.move("leste");
				positionHistory.pop();
			}
			
		}else if(responder.ask("norte").equals("passagem")){
			//Mover para norte:
			responder.move("norte");
			//Colocar a nova posicao na pilha(1 ponto acima):
			positionHistory.push(new MapPoint(positionHistory.peek(),0,1));
			//Chamar a recursão, e se encontrar a saida, terminar retornar essa chamada recursiva tambem:
			if (step() == true){
				return true;
			}else{
				//Se não encontrou saida, entao o caminho nao eh indo para este lado, volta um movimento:
				responder.move("sul");
				positionHistory.pop();
			}
			
		}else if(responder.ask("leste").equals("passagem")){
			//Mover para leste:
			responder.move("leste");
			//Colocar a nova posicao na pilha(1 ponto a direita):
			positionHistory.push(new MapPoint(positionHistory.peek(),1,0));
			//Chamar a recursão, e se encontrar a saida, terminar retornar essa chamada recursiva tambem:
			if (step() == true){
				return true;
			}else{
				//Se não encontrou saida, entao o caminho nao eh indo para este lado, volta um movimento:
				responder.move("oeste");
				positionHistory.pop();
			}
		}else if(responder.ask("sul").equals("passagem")){
			//Mover para sul:
			responder.move("sul");
			//Colocar a nova posicao na pilha(1 ponto abaixo):
			positionHistory.push(new MapPoint(positionHistory.peek(),0,-1));
			//Chamar a recursão, e se encontrar a saida, terminar retornar essa chamada recursiva tambem:
			if (step() == true){
				return true;
			}else{
				//Se não encontrou saida, entao o caminho nao eh indo para este lado, volta um movimento:
				responder.move("norte");
				positionHistory.pop();
			}
		}
		return false;
	}
	
	
//	public boolean discover() {
//		Scanner scanner = new Scanner(System.in);
//		
//		System.out.print("(P)ergunta, (M)ovimento ou (F)im? ");
//		String tipo = scanner.nextLine();
//		while (!tipo.equalsIgnoreCase("F")) {
//		   System.out.print("  --> ");
//		   String pc = scanner.nextLine();
//		   switch (tipo.toUpperCase()) {
//		      case "P": String resposta = responder.ask(pc);
//		                System.out.println("  Resposta: " + resposta);
//		                break;
//		      case "M": boolean moveu = responder.move(pc);
//		                System.out.println((moveu)?"  Movimento executado!":"N�o � poss�vel mover");
//		                break;
//		   }
//			System.out.print("(P)ergunta, (M)ovimento ou (F)im? ");
//			tipo = scanner.nextLine();
//		}
//		
//		if (responder.finalAnswer("cheguei"))
//			System.out.println("Voc� encontrou a saida!");
//		else
//			System.out.println("Fu�m fu�m fu�m!");
//		
//		scanner.close();
//		
//		return true;
//	}
	
}
