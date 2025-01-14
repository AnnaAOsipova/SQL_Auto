package org.max.home;

import org.hibernate.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.max.home.AbstractTest;
import org.max.home.CourierInfoEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.max.home.AbstractTest.getConnection;
import static org.max.home.AbstractTest.getSession;

public class CourierTest extends AbstractTest {
    @Test
    void getCountTest() throws SQLException {
        //given
        String sql = "Select * from courier_info";
        Statement stmt = getConnection().createStatement();
        int count = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            count ++;
        }
        //then
        Assertions.assertEquals(4, count);
        }

    @Test
    void getCountTestORM(){
        //given
        //when
        final Query query = getSession().createSQLQuery("Select * from courier_info").addEntity(CourierInfoEntity.class);
        //then
        Assertions.assertEquals(4, query.getResultList().size());
    }

    @ParameterizedTest
    @CsvSource({"1, John", "2, Kate", "3, Bob", "4, Michael"})
    void TestName(int id, String name) throws SQLException{
        //given
        String sql = "Select * from courier_info where courier_id" + id;
        Statement statement = getConnection().createStatement();
        String nameResult = "";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()) {
            nameResult = rs.getString(4);
        }
        //then
        Assertions.assertEquals(name, nameResult);
    }
}


