package example.db;

import example.entity.ResultEntity;
import jakarta.annotation.PreDestroy;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ResultDAOImpl implements ResultDAO {

    @Override
    public void addNewResult(ResultEntity result) {
        String sql = "INSERT INTO results (hit, x, y, r, ses) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, result.getHit());
            pstmt.setDouble(2, result.getX());
            pstmt.setDouble(3, result.getY());
            pstmt.setDouble(4, result.getR());

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            pstmt.setString(5, session.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateResult(Long result_id, ResultEntity result) {
        String sql = "UPDATE results SET hit = ?, x = ?, y = ?, r = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, result.getHit());
            pstmt.setDouble(2, result.getX());
            pstmt.setDouble(3, result.getY());
            pstmt.setDouble(4, result.getR());
            pstmt.setLong(5, result_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultEntity getResultById(Long result_id) {
        String sql = "SELECT * FROM results WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, result_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new ResultEntity(rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("r"), rs.getInt("hit") == 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LinkedList<ResultEntity> getAllResults() {
        String sql = "SELECT * FROM results WHERE ses = ?";
        LinkedList<ResultEntity> results = new LinkedList<>();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, session.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                results.add(new ResultEntity(rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("r"), rs.getInt("hit") == 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public void deleteResult(String ses) {
        String sql = "DELETE FROM results WHERE ses = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ses);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearResults() {
        String sql = "DELETE FROM results";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}