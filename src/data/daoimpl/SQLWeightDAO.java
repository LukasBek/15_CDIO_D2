package data.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.daointerface.DALException;
import data.daointerface.WeightDAO;
import data.database.Connector;
import data.dto.WeightDTO;

public class SQLWeightDAO implements WeightDAO {
	private Connector connector;
	
	public SQLWeightDAO(){
		try {
			connector = new Connector();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addWeight(WeightDTO w) throws DALException {
		connector.doUpdate(
				"INSERT INTO weight(weight, opr_id) VALUES "
						+"(" + w.getMS() + ", " + w.getopID() + ")"
				);
	}

	@Override
	public List<WeightDTO> getWeightList() throws DALException {
		List<WeightDTO> list = new ArrayList<WeightDTO>();
		ResultSet rs = connector.doQuery("SELECT*FROM weight");
		try{
			while (rs.next()){
				WeightDTO weightDTO = new WeightDTO();
				weightDTO.setMS(rs.getDouble("weight"));
				weightDTO.setopID(rs.getInt("opr_id"));
				weightDTO.setWID(rs.getInt("w_id"));
				list.add(weightDTO);
			}
		} catch(SQLException e){
			throw new DALException(e);
		}
		return list;

	}

}
