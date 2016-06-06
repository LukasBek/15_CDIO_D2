package data.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import data.daointerface.DALException;
import data.daointerface.OperatoerDAO;
import data.database.Connector;
import data.dto.OperatoerDTO;

public class SQLOperatoerDAO implements OperatoerDAO {
	private Connector connector;
	
	public SQLOperatoerDAO(){
		try {
			connector = new Connector();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public OperatoerDTO getOperatoer(int oprId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM operatoer WHERE opr_id = " + oprId);
			try{
			if (!rs.first()) throw new DALException("Brugeren " +oprId+ "findes ikke");
			OperatoerDTO opDTO = new OperatoerDTO();
			opDTO.setOprId(rs.getInt("opr_id"));
			opDTO.setOprNavn(rs.getString("opr_navn"));
			opDTO.setIni(rs.getString("ini"));
			opDTO.setCpr(rs.getString("cpr"));
			opDTO.setPassword(rs.getString("password"));
			opDTO.setAdminStatus(rs.getInt("admin"));
			OperatoerDTO result = opDTO;
			return result;
		} catch(SQLException e) {
			throw new DALException(e);
		}
	}

	
	public List<OperatoerDTO> getOperatoerList() throws DALException {
		List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();
		ResultSet rs = connector.doQuery("SELECT * FROM operatoer");
		try{
			while (rs.next()){
				OperatoerDTO opDTO = new OperatoerDTO();
				opDTO.setOprId(rs.getInt("opr_id"));				
				opDTO.setOprNavn(rs.getString("opr_navn"));
				opDTO.setIni(rs.getString("ini"));
				opDTO.setCpr(rs.getString("cpr"));
				opDTO.setPassword(rs.getString("password"));
				opDTO.setAdminStatus(rs.getInt("admin"));
				list.add(opDTO);
			}
		} catch(SQLException e){
			throw new DALException(e);
		}
		return list;
	}

	public void createOperatoer(OperatoerDTO opr) throws DALException {
		connector.doUpdate(
				"INSERT INTO operatoer(opr_id, opr_navn, ini, cpr, password, admin) VALUES "
						+"(" + opr.getOprId() + ", '" + opr.getOprNavn() + "', '" + opr.getIni() + "', '" + opr.getCpr() + 
						"', '" + opr.getPassword() + "', " + opr.getAdminStatus() + ")"
				);

	}

	public void updateOperatoer(OperatoerDTO opr) throws DALException {
		connector.doUpdate(
				"UPDATE operatoer SET name = '" + opr.getOprNavn() + "', ini = '" + opr.getIni() + "', cpr = '" 
						+ opr.getCpr() + "', password = '" + opr.getPassword() + "', admin = " + opr.getAdminStatus() + " WHERE opr_id = "
						+ 	opr.getOprId()			
				);

	}
}