package me.ppgome.nerdminigames.nerdminigames.db;

import me.ppgome.nerdminigames.nerdminigames.data.Minigame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MinigameDAOImpl implements MinigameDAO {

    Connection con;

    @Override
    public Minigame get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Minigame minigame = null;

        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Minigame WHERE gameid = ?");
        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            int gameid = rs.getInt("gameid");
            int arenaid = rs.getInt("arenaid");
            String gamestatus = rs.getString("gamestatus");

            minigame = new Minigame(gameid, arenaid, gamestatus);
        }


        return minigame;
    }

    @Override
    public List<Minigame> getAll() throws SQLException {
        return null;
    }

    @Override
    public int insert(Minigame minigame) throws SQLException {
        return 0;
    }

    @Override
    public int update(Minigame minigame) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Minigame minigame) {
        return 0;
    }


}
