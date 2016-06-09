package weight;

import java.util.ArrayList;
import java.util.List;

import data.daoimpl.SQLProduktBatchDAO;
import data.daoimpl.SQLRaavareBatchDAO;
import data.daointerface.DALException;

public class RaavareMethod {

	SQLProduktBatchDAO pbdao = new SQLProduktBatchDAO();
	SQLRaavareBatchDAO rdao = new SQLRaavareBatchDAO();

	List<Integer> rvMax = new ArrayList<Integer>();
	List<Integer> rvDone = new ArrayList<Integer>();
	List<Integer> rvNeeded = new ArrayList<Integer>();

	public int getNextRaavare(int batchNumber){

		System.out.println("Her er 1");
		try {		
			rvMax = pbdao.getProduktBatchRaavareList(batchNumber);
			rvDone = pbdao.getProduktBatchDoneRaavare(batchNumber);
			System.out.println("Her er 2");
		} catch (DALException e) {
			System.out.println("Error getting raavarelist");
		}

		for(int i = 0; i<rvMax.size(); i++){
			System.out.println("Her er mange");
			rvNeeded.add(0);
		}

		//rv = liste over raavare der skal måles
		for(int i = 0; i<rvDone.size(); i++){
			System.out.println("Her er mange 222");
			for(int j = 0; i<rvMax.size(); j++){
				System.out.println("Her er rigtigt mange 11111111111");
				if(rvDone.get(i) == rvMax.get(j)){
					rvNeeded.set(j, 1);
				}
			}
		}
		
		for(int i = 0; i<rvNeeded.size(); i++){
			System.out.println("Her er mange 3333");
			if(rvNeeded.get(i) == 0){
				System.out.println(rvMax.get(i));
				return rvMax.get(i);
			}
		}
		return -1;
	}
}
