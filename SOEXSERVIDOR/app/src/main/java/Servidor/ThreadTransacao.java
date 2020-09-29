package Servidor;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadTransacao extends Thread{

private int min;
private int max;
private int id;
private int tipo;
private int tempoBanco;
private Random tempoCalculo;
private Semaphore banco;

public ThreadTransacao(int num, Semaphore banco){
	this.id = num;
	this.banco = banco;
	this.tipo = id % 3;
	
	switch (tipo){
		case 1:
	this.min = 200;
	this.max = 1000;
	this.tempoBanco = 1000;
		break;
		case 2:
	this.min=500;
	this.max=1500;
	this.tempoBanco = 1500;
		break;
		case 0:
	this.min=1000;
	this.max=1500;
	this.tempoBanco = 1500;
		break;
	}
	System.out.println("Thread "+
		id+
		" do tipo"+
		tipo+
		" criada"+
	".");
}


@Override
public void run(){
	
	for (int i=tipo;i>=0;i--){
		calcula(tempoCalculo.nextInt(min-max)+ min);
		System.out.println("ajdb");
		try {
			banco.acquire();
			transacao();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
		finally {
			banco.release();
		}
	}
}

private void calcula(int tempo){
		System.out.println("ajdb");
		try{
			Thread.sleep(tempo);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	System.out.println("# "+
		id +
		" finalizou um calculo de "+
		(tempo/1000)+
		" segundos"+
	".");
	
}


private void transacao(){

	try {
		Thread.sleep(tempoBanco);
		System.out.println("#"+
			id +
			" fez uma transação no banco"+
		".");
	}
	catch(InterruptedException e){
		e.printStackTrace();
	}
	
	
}

}
