package pt.c02classes.s01knowledge.s02app.app;

import java.util.Scanner;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.inter.IStatistics;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerMaze;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderMaze;

public class OrchestratorInit 
{
	public static void main(String[] args)
	{	
		/*Variaveis Comuns*/
		IEnquirer enq;
		IResponder resp;
		IStatistics stat;
		
		/*Verifica se o jogador escolheu um desafio*/
		boolean flag = true;
		/*Leitura das entradas*/
		Scanner scanner = new Scanner(System.in);
		/*Recebe nome do Desafio*/
		String desafio;
		
		while(flag)
		{
			System.out.println("Animals ou Maze ?");
			desafio = scanner.nextLine();
			if((desafio.equalsIgnoreCase("Animals")) || (desafio.equalsIgnoreCase("Maze")))
				flag = false;
			
			/*Seleciona Flag*/
			if(flag == false)
			{	
				/*Selecionar Desafio*/
				if(desafio.equalsIgnoreCase("Animals"))
				{	
					IBaseConhecimento base = new BaseConhecimento();
					
					base.setScenario("animals");
					String listaAnimais[] = base.listaNomes();
			        for (int animal = 0; animal < listaAnimais.length; animal++) {
						System.out.println("Enquirer com " + listaAnimais[animal] + "...");
						stat = new Statistics();
						resp = new ResponderAnimals(stat, listaAnimais[animal]);
						enq = new EnquirerAnimals();
						enq.connect(resp);
						enq.discover();
						System.out.println("----------------------------------------------------------------------------------------\n");
			        }
				}
				else if(desafio.equalsIgnoreCase("Maze"))
				{
					System.out.println("Enquirer com Mordor...");
					stat = new Statistics();
					resp = new ResponderMaze(stat, "feec");
					enq = new EnquirerMaze();
					enq.connect(resp);
					enq.discover();
					System.out.println("----------------------------------------------------------------------------------------\n");
				}
				scanner.close();
			}	
		}
	}
}
