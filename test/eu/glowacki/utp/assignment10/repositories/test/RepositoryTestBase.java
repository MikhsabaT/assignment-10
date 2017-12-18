package eu.glowacki.utp.assignment10.repositories.test;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OraclePooledConnection;
import org.junit.After;
import org.junit.Before;

import eu.glowacki.utp.assignment10.dtos.DTOBase;
import eu.glowacki.utp.assignment10.repositories.IRepository;
import org.junit.BeforeClass;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RepositoryTestBase<TDTO extends DTOBase, TRepository extends IRepository<TDTO>> {

	protected TRepository _repository;
	protected static PooledConnection pooledConnection;

	@BeforeClass
    public static void createPooledConnection() {
        try {
            OracleConnectionPoolDataSource poolDataSource = new OracleConnectionPoolDataSource();
            poolDataSource.setURL("jdbc:oracle:thin:@10.01.01.34:1521:baza");
            poolDataSource.setUser("s15711");
            poolDataSource.setPassword("oracle12");
            pooledConnection = poolDataSource.getPooledConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

	@Before
	public void before() {
		_repository = Create();
		if (_repository != null) {
			_repository.beginTransaction();
		}
	}

	@After
	public void after() {
		if (_repository != null) {
			_repository.rollbackTransaction();
		}
	}

	protected abstract TRepository Create();
}