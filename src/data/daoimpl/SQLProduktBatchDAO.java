package data.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.daointerface.DALException;
import data.daointerface.ProduktbatchDAO;
import data.database.Connector;
import data.dto.ProduktBatchDTO;
import data.dto.ProduktBatchKomponentDTO;

public class SQLProduktBatchDAO implements ProduktbatchDAO{
	
	private Connector connector;
	 	
	public SQLProduktBatchDAO() {
		try {
			connector = new Connector();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM produktbatch WHERE pb_id = " + pbId);
		try{
			if (!rs.first()) throw new DALException("Produktbatch " + pbId + " findes ikke");
			ProduktBatchDTO pbDTO = new ProduktBatchDTO();
			pbDTO.setPbId(rs.getInt("pb_id"));
			pbDTO.setStatus(rs.getInt("status"));
			pbDTO.setReceptId(rs.getInt("recept_id"));
			ProduktBatchDTO result = pbDTO;
			return result;
		} catch(SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		List<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
		ResultSet rs = connector.doQuery("SELECT * FROM produktbatch");
		try{
			while (rs.next()){
				ProduktBatchDTO pbDTO = new ProduktBatchDTO();
				pbDTO.setPbId(rs.getInt("pb_id"));
				pbDTO.setStatus(rs.getInt("status"));
				pbDTO.setReceptId(rs.getInt("recept_id"));
				list.add(pbDTO);
			}
		} catch(SQLException e){
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO pb) throws DALException {
		connector.doUpdate(
				"INSERT INTO produktbatch(pb_id, status, recept_id) VALUES "
						+"(" + pb.getPbId() + ", '" + pb.getStatus() + ", '" + pb.getReceptId() + ")"
				);			
	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO pb) throws DALException {
		connector.doUpdate(
				"UPDATE produktbatch SET pb_id = '" + pb.getPbId() + "', status = '" + pb.getStatus() + 
				"', recept_id = '" + pb.getReceptId() + " WHERE recept_id = " + pb.getPbId()			
				);			
	}

	@Override
	public ProduktBatchKomponentDTO getProduktBatchKomponent(int pbId, int rbId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id = " + pbId + " AND rb_id = " + rbId);
		try {
			if (!rs.first()) throw new DALException("Produktbatchkomponent " + pbId + " findes ikke");
			ProduktBatchKomponentDTO pbkDTO = new ProduktBatchKomponentDTO();
			pbkDTO.setPbId(rs.getInt("pb_id"));
			pbkDTO.setRbId(rs.getInt("rb_id"));
			pbkDTO.setTara(rs.getDouble("tara"));
			pbkDTO.setNetto(rs.getDouble("netto"));
			pbkDTO.setOprId(rs.getInt("opr_id"));
			return pbkDTO;
		} catch (SQLException e) {
			throw new DALException(e); 
		}
	}

	@Override
	public List<ProduktBatchKomponentDTO> getProduktBatchKomponentList(int pbId) throws DALException{
		List<ProduktBatchKomponentDTO> list = new ArrayList<ProduktBatchKomponentDTO>();
		ResultSet rs = connector.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id = " + pbId);
		try{
			while (rs.next()){
				ProduktBatchKomponentDTO pbkDTO = new ProduktBatchKomponentDTO();
				pbkDTO.setPbId(rs.getInt("pb_id"));
				pbkDTO.setRbId(rs.getInt("rb_id"));
				pbkDTO.setTara(rs.getDouble("tara"));
				pbkDTO.setNetto(rs.getDouble("netto"));
				pbkDTO.setOprId(rs.getInt("opr_id"));
				list.add(pbkDTO);
			}
			return list;
		}
		catch (SQLException e) { throw new DALException(e); }
	}

	@Override
	public List<ProduktBatchKomponentDTO> getProduktBatchKomponentList() throws DALException {
		List<ProduktBatchKomponentDTO> list = new ArrayList<ProduktBatchKomponentDTO>();
		ResultSet rs = connector.doQuery("SELECT * FROM produktbatchkomponent");
		try{
			while (rs.next()) {
				ProduktBatchKomponentDTO pbkDTO = new ProduktBatchKomponentDTO();
				pbkDTO.setPbId(rs.getInt("pb_id"));
				pbkDTO.setRbId(rs.getInt("rb_id"));
				pbkDTO.setTara(rs.getDouble("tara"));
				pbkDTO.setNetto(rs.getDouble("netto"));
				pbkDTO.setOprId(rs.getInt("opr_id"));
				list.add(pbkDTO);
			}
			return list;
		}
		catch (SQLException e) { throw new DALException(e); }
	}

	@Override
	public void createProduktBatchKomponent(ProduktBatchKomponentDTO produktbatchkomponent) throws DALException {
		connector.doUpdate(
				"INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, opr_id) VALUES " +
						"(" + produktbatchkomponent.getPbId() + ", " + produktbatchkomponent.getRbId() + ", " + produktbatchkomponent.getNetto() + ", " + 
						produktbatchkomponent.getTara() + ", " + produktbatchkomponent.getOprId() + ")"
				);
	}

	@Override
	public void updateProduktBatchKomponent(ProduktBatchKomponentDTO produktbatchkomponent) throws DALException {
		connector.doUpdate(
				"UPDATE produktbatchkomponent SET  netto = " + produktbatchkomponent.getNetto() + ", tara = " + produktbatchkomponent.getTara() + 
				" WHERE pb_id = " + produktbatchkomponent.getPbId()
				);
	}
}