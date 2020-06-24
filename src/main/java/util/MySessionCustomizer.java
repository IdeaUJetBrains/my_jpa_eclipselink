package util;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.tools.schemaframework.IndexDefinition;

import java.sql.SQLException;

public class MySessionCustomizer implements SessionCustomizer {
    private static final String SUFFIX = "STRTEST";
    @Override
    public void customize(Session session) throws SQLException {
        for (ClassDescriptor descriptor : session.getDescriptors().values()) {
            //Only change the table name for non-embedable entities with no @Table already
            if (!descriptor.getTables().isEmpty() && descriptor.getAlias().equalsIgnoreCase(descriptor.getTableName())) {
                String tableName = descriptor.getTableName().concat(SUFFIX).toUpperCase();
                descriptor.setTableName(tableName);
                for (IndexDefinition index : descriptor.getTables().get(0).getIndexes()) {
                    index.setTargetTable(tableName);
                }
            }
        }
    }
}