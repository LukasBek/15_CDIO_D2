package data.daointerface;

import java.util.List;

import data.dto.RaavareBatchDTO;

public interface RaavareBatchDAO {
	
	RaavareBatchDTO getRaavareBatch(int rbId) throws DALException;
	List<RaavareBatchDTO> getRaavareBatchList( int rbId) throws DALException;
	void createRaavareBatch(RaavareBatchDTO rb) throws DALException;
	void updateRaavareBatch(RaavareBatchDTO rb) throws DALException;

}
