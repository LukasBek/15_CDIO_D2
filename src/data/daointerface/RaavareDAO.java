package data.daointerface;

import java.util.List;

import data.dto.RaavareDTO;

public interface RaavareDAO {
	void addRaavare (RaavareDTO w) throws DALException;
	RaavareDTO getRaavare(int id) throws DALException;
	List<RaavareDTO> getRaavareList() throws DALException;
}
