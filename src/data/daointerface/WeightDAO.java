package data.daointerface;

import java.util.List;

import data.dto.WeightDTO;

public interface WeightDAO {

	void addWeight (WeightDTO w) throws DALException;
	List<WeightDTO> getWeightList() throws DALException;
}
