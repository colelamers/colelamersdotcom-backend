package blogapi.service;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;

@Component
public class PostgreSQLQueryWrapper {

    private final JdbcTemplate jdbcTemplate;
    public PostgreSQLQueryWrapper(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Execute a native SQL query and return a list of results mapped to the specified class.
     *
     * @param sql        The SQL query to execute
     * @param resultClass The class to map the results to
     * @param params     The parameters to set in the query
     * @param <T>        The type of the result
     * @return A list of mapped results
     */
    @Transactional
    public <T> List<T> executeQueryAsClassList(String sql, Class<T> resultClass, Object[] params)
    {
        var x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(resultClass), params);
        return x;
    }

    /**
     * Execute a native SQL query and return a list of results as Object arrays (generic version).
     * This method is used when the result set should not be mapped to a specific entity class.
     *
     * @param sql    The SQL query to execute
     * @param params The parameters to set in the query
     * @return A list of Object arrays (representing rows)
     */
    @Transactional
    public List<Object[]> executeQueryAs(String sql, Object[] params)
    {
        // Directly use queryForList and map the result to Object[] arrays
        return jdbcTemplate.query(sql, params, (ResultSet rs, int rowNum) -> {
            int columnCount = rs.getMetaData().getColumnCount();
            Object[] row = new Object[columnCount];

            // Efficiently populate the row with column values
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);  // Convert each column into an object
            }

            return row;  // Return the row as an Object array
        });
    }
}
