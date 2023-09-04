package com.wanted.nution;

import com.wanted.nution.common.DBManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcPageRepository implements PageRepository{

    private final DBManager dbManager;

    @Override
    public int save(Pages page) throws SQLException {
        String query = "insert into pages (id, title, content, parent_id) values (null, ?, ?, ?)";
        int res = 0;

        try (Connection con = dbManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
        ) {
            pstmt.setString(1, page.getTitle());
            pstmt.setString(2, page.getContent());
            pstmt.setLong(3, page.getParentId());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Optional<Pages> findById(Long id) {
        List<PageSummary> subs = findSubPagesById(id);

        String query = "SELECT *" +
                " FROM pages" +
                " WHERE id = ?";

        try(Connection con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) return Optional.of(new Pages(rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getLong("parent_id")
                    ));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<PageSummary> findSubPagesById(Long id) {
        List<PageSummary> res = new ArrayList<>();

        String query = "SELECT *" +
                " FROM pages" +
                " WHERE parent_id = ?";

        try(Connection con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
//                res.add(new Pages(rs.getLong(1),
//                        rs.getString(2),
//                        rs.getString(3),
//                        rs.getLong(4)));
                res.add(new PageSummary(rs.getLong(1), rs.getString(2), rs.getLong(4)));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

//    @Override
//    public List<SubPages> findParentPagesById(Long id) {
//        List<SubPages> parents = new ArrayList<>();
//
//        while (id != null) {
//            SubPages found = findParentById(id);
//            parents.add(found);
//            id = found.getParent_id();
//        }
//
//        return parents;
//    }

    @Override
    public PageSummary findParentById(Long id) {
        String query = "SELECT id, title, parent_id" +
                " FROM pages" +
                " WHERE id = ?";
        try (Connection con = dbManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) return new PageSummary(rs.getLong("id"), rs.getString("title"), rs.getLong("parent_id"));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
