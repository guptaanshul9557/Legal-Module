package org.egov.lm.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.egov.lm.models.Advocate;
import org.egov.lm.models.AuditDetails;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class AdvocateRowMapper
        implements ResultSetExtractor<List<Advocate>> {

    @Override
    public List<Advocate> extractData(ResultSet rs) throws SQLException {

        List<Advocate> result = new ArrayList<>();

        while (rs.next()) {

            AuditDetails audit = AuditDetails.builder()
                    .createdBy(rs.getString("createdby"))
                    .createdTime(rs.getLong("createdtime"))
                    .lastModifiedBy(rs.getString("lastmodifiedby"))
                    .lastModifiedTime(rs.getObject("lastmodifiedtime", Long.class))
                    .build();

            Advocate advocate = Advocate.builder()
                    .advocateId(rs.getString("id"))
                    .advocateId(rs.getString("advocateid"))
                    .role(rs.getString("role"))
                    .activeCaseCount(rs.getInt("activecasecount"))
                    .auditDetails(audit)
                    .build();

            result.add(advocate);
        }

        return result;
    }
}

