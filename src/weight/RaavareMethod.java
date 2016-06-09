package weight;

import java.util.ArrayList;
import java.util.List;

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

		System.out.println(rvMax.toString());
		System.out.println(rvNeeded.toString());
		System.out.println(rvDone.toString());
		
		//rv = liste over raavare der skal måles
		for(int i = 0; i<rvDone.size(); i++){
			for(int j = 0; j<rvMax.size(); j++){
				System.out.println("i: " + i + ", j: "+ j);
				if(rvDone.get(i) == rvMax.get(j)){
					rvNeeded.set(j, 1);
				}
			}
		}
		System.out.println(rvMax.toString());
		System.out.println(rvNeeded.toString());
		System.out.println(rvDone.toString());
		for(int i = 0; i<rvNeeded.size(); i++){
			if(rvNeeded.get(i) == 0){
				System.out.println(rvMax.get(i));
				return rvMax.get(i);
			}
		}
		rvMax.get(27);
		return -1;
	}
}
