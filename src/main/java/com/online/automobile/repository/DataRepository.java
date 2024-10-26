package com.online.automobile.repository;

import com.online.automobile.model.Company;
import com.online.automobile.model.Location;
import com.online.automobile.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class DataRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findAllByChart(String fdate, String tdate, User user){
        List<Map<String, Object>> rows = null;
        try {
            String sql="SELECT DATE(timestamp_created) AS days,SUM(paid_amount) AS summ FROM receipt_header" +
                    " WHERE DATE(timestamp_created) >= '"+fdate+"' AND DATE(timestamp_created) <= '"+tdate+"' AND created_by='"+user.getId()+"' AND paid_status=1" +
                    " GROUP BY DATE(timestamp_created)";

            rows = jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return rows;
    }

    public List<Map<String, Object>> findAllByCompanyLocationAndServiceTypesAndRating(Location location) {

        List<Map<String, Object>> rows = null;

        try {
            String sql = "SELECT COALESCE((SELECT AVG(rating) FROM company_rating AS r WHERE r.company=c.id GROUP BY rating_company_user),0) AS rating,COALESCE((SELECT COUNT(company) AS c FROM company_service_type AS st WHERE st.company=c.id GROUP BY company),0) AS servicetype,c.id AS id,c.address AS address,c.company_name AS name,l.location AS location,c.start_time AS start,c.end_time AS endt,t.type AS type FROM comapny AS c JOIN location AS l ON c.company_location=l.id JOIN company_type AS t ON c.company_type=t.id WHERE c.company_location= "+location.getId()+" ORDER BY rating DESC";

            rows = jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows;
    }
}
