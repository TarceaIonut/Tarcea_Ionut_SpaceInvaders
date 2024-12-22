package service;

import java.sql.*;
import java.util.*;

public class DbService {
    protected Connection con;

    public DbService() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/sp";
        Properties props = new Properties();
        props.setProperty("user", "sp");
        props.setProperty("password", "sp");
//        props.setProperty("ssl", "true");
        con = DriverManager.getConnection(url, props);
    }

    public List<String> listUsers() throws SQLException {
        List<String> result = new ArrayList<String>();
        Statement st = con.createStatement();
        ResultSet sr = st.executeQuery("select userName, age from users");
        while (sr.next()) {
            result.add(sr.getString(1));
        }
        return result;
    }

    public boolean userExists(String userName, String password) throws SQLException {
        userName = userName.toUpperCase();
        password = password.toUpperCase();
        PreparedStatement ps = con.prepareStatement("select userName from users where userName = ? and password = ?");
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet sr = ps.executeQuery();
        if (sr.next())
            return true;
        return false;
    }

    public void newUser(String userName, String password) throws SQLException {
        userName = userName.toUpperCase();
        password = password.toUpperCase();
        PreparedStatement ps = con.prepareStatement("insert into users (userName, password) values (?, ?)");
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.executeUpdate();
    }

    public List<Integer> topScoresFromUser(String userName) throws SQLException {
        userName = userName.toUpperCase();
        List<Integer> r = new LinkedList<Integer>();
        PreparedStatement ps = con.prepareStatement("select score from users u inner join games g on g.user_id = u.id where u.username = ? ORDER BY g.score desc");
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            r.add(rs.getInt(1));
            //System.out.println(rs.getInt(1));
        }
        return r;
    }

    public List<Integer> topScoresFromUser(String userName, int nr) throws SQLException {
        userName = userName.toUpperCase();
        List<Integer> r = new LinkedList<Integer>();
        PreparedStatement ps = con.prepareStatement("select top ? score from users inner join games on games.user = users.userName where userName = ? order by score desc");
        ps.setString(2, userName);
        ps.setInt(1, nr);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            r.add(rs.getInt(1));
        }
        return r;
    }

    public List<Integer> topScores(String userName) throws SQLException {
        userName = userName.toUpperCase();
        List<Integer> r = new LinkedList<Integer>();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select score from games order by score desc");
        while (rs.next()) {
            r.add(rs.getInt(1));
        }
        return r;
    }

    public List<Integer> topScores(String userName, int nr) throws SQLException {
        userName = userName.toUpperCase();
        List<Integer> r = new LinkedList<Integer>();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select top" + nr + "score from games order by score desc");
        while (rs.next()) {
            r.add(rs.getInt(1));
        }
        return r;
    }

    public void insertNewGame(String userName, int score) throws SQLException {
        userName = userName.toUpperCase();
        try {
            PreparedStatement ps = con.prepareStatement("insert into games (user_id, score) values ((select id from users where username = ?), ?);");
            ps.setString(1, userName);
            ps.setInt(2, score);
            ps.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(userName + " " + score);

            System.out.println("Username already picked");
        }

    }
}
