package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @Autowired
    final DataSource dataSource;

    public AdminPageController(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @GetMapping
    public String getAdminPage() throws SQLException {
        DatabaseMetaData md = dataSource.getConnection().getMetaData();
        ResultSet rs = md.getTables("srv84190_gm4brain", null, "%", null);
        while (rs.next()) {
            System.out.println(rs.getString("TABLE_NAME"));
        }
        return "/admin/adminIndex";
    }
}
