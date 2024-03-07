package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lecture.SingletonConnection;
import lecture.Livre;
public class LivreDaoImpl implements ILivreDao {
@Override
public Livre save(Livre l) {
Connection conn=SingletonConnection.getConnection();
 try {
PreparedStatement ps= conn.prepareStatement("INSERT INTO LIVRES (TITRE_LIVRE,AUTEUR,PRIX,GENRE,NB_PAGES) VALUES(?,?,?,?,?)");
ps.setString(1, l.getTitreLivre());
ps.setString(2, l.getAuteur());
ps.setDouble(3, l.getPrix());
ps.setString(4, l.getGenre());
ps.setInt(5, l.getNbpages());
ps.executeUpdate();
PreparedStatement ps2= conn.prepareStatement
("SELECT MAX(ID_LIVRE) as MAX_ID FROM LIVRES");
ResultSet rs =ps2.executeQuery();
if (rs.next()) {
l.setIdLivre(rs.getLong("MAX_ID"));
}
ps.close();
ps2.close();
} catch (SQLException e) {
e.printStackTrace();
}
return l;
}
@Override
public List<Livre> livresParMC(String mc) {
 List<Livre> livs= new ArrayList<Livre>();
 Connection conn=SingletonConnection.getConnection();
 try {
PreparedStatement ps= conn.prepareStatement("select * from LIVRES where TITRE_LIVRE LIKE ?");
ps.setString(1, "%"+mc+"%");
ResultSet rs = ps.executeQuery();
while (rs.next()) {
Livre l = new Livre();
l.setIdLivre(rs.getLong("ID_LIVRE"));
l.setTitreLivre(rs.getString("TITRE_LIVRE"));
l.setAuteur(rs.getString("AUTEUR"));
l.setPrix(rs.getDouble("PRIX"));
l.setGenre(rs.getString("GENRE"));
l.setNbpages(rs.getInt("NbPages"));
livs.add(l);
}
} catch (SQLException e) {
e.printStackTrace();
}
return livs;
}
@Override
public Livre getLivre(Long id) {
	Connection conn=SingletonConnection.getConnection();
	 Livre l = new Livre();
	 try {
	PreparedStatement ps= conn.prepareStatement("select * from LIVRES where ID_LIVRE = ?");
	ps.setLong(1, id);
	ResultSet rs = ps.executeQuery();
	if (rs.next()) {
	l.setIdLivre(rs.getLong("ID_LIVRE"));
	l.setTitreLivre(rs.getString("TITRE_LIVRE"));
	l.setAuteur(rs.getString("AUTEUR"));
	l.setPrix(rs.getDouble("PRIX"));
	l.setGenre(rs.getString("GENRE"));
	l.setNbpages(rs.getInt("NB_PAGES"));
	
	}
	} catch (SQLException e) {
	e.printStackTrace();
	}
	return l;
	}


@Override
public Livre updateLivre(Livre l) {
	Connection conn=SingletonConnection.getConnection();
	 try {
	PreparedStatement ps= conn.prepareStatement("UPDATE LIVRES SET TITRE_LIVRE=?,AUTEUR=?,PRIX=?,GENRE=?,NB_PAGES=? WHERE ID_LIVRE=?");
	ps.setString(1, l.getTitreLivre());
	ps.setString(2, l.getAuteur());
	ps.setDouble(3, l.getPrix());
	ps.setString(4, l.getGenre());
	ps.setInt(5, l.getNbpages());
	ps.setLong(6, l.getIdLivre());
	ps.executeUpdate();
	ps.close();
	} catch (SQLException e) {
	e.printStackTrace();
	}
	return l;
	}


@Override
public void deleteLivre(Long id) {
	Connection conn=SingletonConnection.getConnection();
	 try {
	PreparedStatement ps= conn.prepareStatement("DELETE FROM LIVRES WHERE ID_LIVRE = ?");
	ps.setLong(1, id);
	ps.executeUpdate();
	ps.close();
	} catch (SQLException e) {
	e.printStackTrace();
	}
	}

}