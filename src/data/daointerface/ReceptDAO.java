package data.daointerface;

import java.util.List;

import data.daointerface.DALException;
import data.dto.ReceptKomponentDTO;
import data.dto.ReceptDTO;

public interface ReceptDAO {
	ReceptDTO getRecept(int receptId) throws DALException;
	List<ReceptDTO> getReceptList() throws DALException;
	void createRecept(ReceptDTO rec) throws DALException;
	void updateRecept(ReceptDTO rec) throws DALException;
	
	//Methods for "receptkomponent" table access
		ReceptKomponentDTO getReceptKomp(int receptId, int raavareId) throws DALException;
		List<ReceptKomponentDTO> getReceptKompList(int receptId) throws DALException;
		List<ReceptKomponentDTO> getReceptKompList() throws DALException;
		void createReceptKomp(ReceptKomponentDTO receptkomponent) throws DALException;
		void updateReceptKomp(ReceptKomponentDTO receptkomponent) throws DALException;
	
}
