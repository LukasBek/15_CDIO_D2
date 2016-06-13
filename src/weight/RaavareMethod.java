package weight;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import data.daoimpl.SQLProduktBatchDAO;
import data.daoimpl.SQLRaavareBatchDAO;
import data.daointerface.DALException;

public class RaavareMethod {

	SQLProduktBatchDAO pbdao = new SQLProduktBatchDAO();
	SQLRaavareBatchDAO rdao = new SQLRaavareBatchDAO();



	public int getNextRaavare(int batchNumber){

		List<Integer> rvMax = new ArrayList<Integer>();
		List<Integer> rvDone = new ArrayList<Integer>();
		List<Integer> rvNeeded = new ArrayList<Integer>();
		try {		
			rvMax = pbdao.getProduktBatchRaavareList(batchNumber);
			rvDone = pbdao.getProduktBatchDoneRaavare(batchNumber);
		} catch (DALException e) {
			System.out.println("Error getting raavarelist");
		}

		for(int i = 0; i<rvMax.size(); i++){
			rvNeeded.add(0);
		}
		
		//rv = liste over raavare der skal måles
		for(int i = 0; i<rvDone.size(); i++){
			for(int j = 0; j<rvMax.size(); j++){
				System.out.println("i: " + i + ", j: "+ j);
				if(rvDone.get(i) == rvMax.get(j)){
					rvNeeded.set(j, 1);
				}
			}
		}
		for(int i = 0; i<rvNeeded.size(); i++){
			if(rvNeeded.get(i) == 0){
				System.out.println(rvMax.get(i));
				return rvMax.get(i);
			}
		}
		return -1;
	}
	
	public int measureMethod(Scanner sc, int raavareID){
		int raavareBatch;
		try{
		raavareBatch = sc.nextInt();
		}catch(InputMismatchException e){
			raavareBatch = -1;
		}
		
		try {
			if(rdao.getRaavareBatch(raavareBatch).getRaavareId()!=raavareID){
				System.out.println(raavareID);
				System.out.println(rdao.getRaavareBatch(raavareBatch).getRaavareId());
				raavareBatch = -2;
			}
		} catch (DALException e) {
			e.printStackTrace();
		}
		return raavareBatch;
	}

}
