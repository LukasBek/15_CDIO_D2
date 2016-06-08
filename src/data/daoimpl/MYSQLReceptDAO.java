package data.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.daointerface.DALException;
import data.daointerface.ReceptDAO;
import data.database.Connector;
import data.dto.ReceptDTO;
import data.dto.ReceptKomponentDTO;


public class MYSQLReceptDAO implements ReceptDAO{
	private Connector connector;
	
	public MYSQLReceptDAO() {
		try {
			connector = new Connector();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ReceptDTO getRecept(int receptId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM recept WHERE recept_id = " + receptId);
		try{
			if (!rs.first()) throw new DALException("Recepten " + receptId + " findes ikke");
			ReceptDTO recDTO = new ReceptDTO();
			recDTO.setReceptId(rs.getInt("recept_id"));
			recDTO.setReceptName(rs.getString("recept_navn"));
			ReceptDTO result = recDTO;
			return result;
		} catch(SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<ReceptDTO> getReceptList() throws DALException {
		List<ReceptDTO> list = new ArrayList<ReceptDTO>();
		ResultSet rs = connector.doQuery("SELECT * FROM recept");
		try{
			while (rs.next()){
				ReceptDTO recDTO = new ReceptDTO();
				recDTO.setReceptId(rs.getInt("recept_id"));
				recDTO.setReceptName(rs.getString("recept_navn"));
				list.add(recDTO);
			}
		} catch(SQLException e){
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createRecept(ReceptDTO rec) throws DALException {
		connector.doUpdate(
				"INSERT INTO recept(recept_id, recept_navn) VALUES "
						+"(" + rec.getReceptId() + ", '" + rec.getReceptName() + ")"
				);		
	}

	@Override
	public void updateRecept(ReceptDTO rec) throws DALException {
		connector.doUpdate(
				"UPDATE recept SET recept_id = '" + rec.getReceptId() + "', recept_navn = '" + rec.getReceptName()
				 + " WHERE recept_id = " + rec.getReceptId()			
				);		
	}
	
	@Override
	public ReceptKomponentDTO getReceptKomp(int receptId, int raavareId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM receptkomponent WHERE recept_id = " + receptId + " AND raavare_id = " + raavareId);
	    try {
	    	if (!rs.first()) throw new DALException("Receptkomponent " + receptId + " findes ikke");
	    	return new ReceptKomponentDTO (rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<ReceptKomponentDTO> getReceptKompList(int receptId) throws DALException {
		List<ReceptKomponentDTO> list = new ArrayList<ReceptKomponentDTO>();
		ResultSet rs = connector.doQuery("SELECT * FROM receptkomponent WHERE recept_id = " + receptId);
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptKomponentDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<ReceptKomponentDTO> getReceptKompList() throws DALException {
		List<ReceptKomponentDTO> list = new ArrayList<ReceptKomponentDTO>();
		ResultSet rs = connector.doQuery("SELECT * FROM receptkomponent");
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptKomponentDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createReceptKomp(ReceptKomponentDTO receptkomponent) throws DALException {
		connector.doUpdate(
				"INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES " +
				"(" + receptkomponent.getRecept_Id() + ", '" + receptkomponent.getRaavare_id() + "', '" + receptkomponent.getNom_netto() + "', '" + 
				receptkomponent.getTolerance() + "')"
			);	
	}

	@Override
	public void updateReceptKomp(ReceptKomponentDTO receptkomponent) throws DALException {
		connector.doUpdate(
				"UPDATE receptkomponent SET nom_netto = '" + receptkomponent.getNom_netto() + "', tolerance = '" + receptkomponent.getTolerance()
				+ "' WHERE raavare_id = " + receptkomponent.getRaavare_id() + " AND recept_id = " + receptkomponent.getRecept_Id()
		);
	}

}
