package data.daointerface;

import java.util.List;

import data.dto.ProduktBatchDTO;
import data.daointerface.DALException;
import data.dto.ProduktBatchKomponentDTO;

public interface ProduktbatchDAO {

	ProduktBatchDTO getProduktBatch(int pbId) throws DALException;
	List<ProduktBatchDTO> getProduktBatchList() throws DALException;
	void createProduktBatch(ProduktBatchDTO pb) throws DALException;
	void updateProduktBatch(ProduktBatchDTO pb) throws DALException;
	
	// Methods for acces to the produktbatchkomponent table
	ProduktBatchKomponentDTO getProduktBatchKomponent(int pbId, int rbId) throws DALException;
	List<ProduktBatchKomponentDTO> getProduktBatchKomponentList(int pbId) throws DALException;
	List<ProduktBatchKomponentDTO> getProduktBatchKomponentList() throws DALException;
	void createProduktBatchKomponent(ProduktBatchKomponentDTO produktbatchkomponent) throws DALException;
	void updateProduktBatchKomponent(ProduktBatchKomponentDTO produktbatchkomponent) throws DALException;	
}
