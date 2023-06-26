package jp.co.project.planets.earthly.schema.db.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.seasar.doma.jdbc.NoCacheSqlFileRepository;
import org.seasar.doma.jdbc.SqlFile;
import org.seasar.doma.jdbc.SqlFileRepository;
import org.seasar.doma.jdbc.dialect.Dialect;

/**
 * 
 */
public class RoleDaoTest {

    /** */
    protected SqlFileRepository repository;

    /** */
    protected Dialect dialect;

    /** */
    protected Driver driver;

    /** */
    protected String url;

    /** */
    protected String user;

    /** */
    protected String password;

    @BeforeEach
    protected void setUp() throws Exception {
        repository = new NoCacheSqlFileRepository();
        dialect = new org.seasar.doma.jdbc.dialect.MysqlDialect();
        url = "jdbc:mysql://localhost:3306/earthly?useSSL=false&allowPublicKeyRetrieval=true";
        user = "earthly";
        password = "earthly";
    }

    /**
     * @param sqlFile
     * @throws Exception
     */
    protected void execute(final SqlFile sqlFile) throws Exception {
        final Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            final Statement statement = connection.createStatement();
            try {
                statement.execute(sqlFile.getSql());
            } finally {
                statement.close();
            }
        } finally {
            try {
                connection.rollback();
            } finally {
                connection.close();
            }
        }
    }

    /**
     * @return
     * @throws Exception
     */
    protected Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSelectAssignedRoleByUserIdAndLikeName(final TestInfo testInfo) throws Exception {
        final SqlFile sqlFile = repository.getSqlFile(testInfo.getTestMethod().get(),
                "META-INF/jp/co/project/planets/earthly/schema/db/dao/RoleDao/selectAssignedRoleByUserIdAndLikeName.sql",
                dialect);
        execute(sqlFile);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSelectByAssignedRoleByUserId(final TestInfo testInfo) throws Exception {
        final SqlFile sqlFile = repository.getSqlFile(testInfo.getTestMethod().get(),
                "META-INF/jp/co/project/planets/earthly/schema/db/dao/RoleDao/selectByAssignedRoleByUserId.sql",
                dialect);
        execute(sqlFile);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSelectGrantedRoleByUserId(final TestInfo testInfo) throws Exception {
        final SqlFile sqlFile = repository.getSqlFile(testInfo.getTestMethod().get(),
                "META-INF/jp/co/project/planets/earthly/schema/db/dao/RoleDao/selectGrantedRoleByUserId.sql", dialect);
        execute(sqlFile);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSelectUnassignedRoleByUserIdAndLikeName(final TestInfo testInfo) throws Exception {
        final SqlFile sqlFile = repository.getSqlFile(testInfo.getTestMethod().get(),
                "META-INF/jp/co/project/planets/earthly/schema/db/dao/RoleDao/selectUnassignedRoleByUserIdAndLikeName.sql",
                dialect);
        execute(sqlFile);
    }

}
