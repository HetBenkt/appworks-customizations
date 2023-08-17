package nl.bos;

import com.eibus.applicationconnector.sql.DBConnectionPool;
import com.eibus.applicationconnector.sql.WCPDBConnection;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.eibus.xml.nom.XMLException;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

class TestAPI {

    private final static String DSO_XML = """
            <configuration>
            	<update-connections>10</update-connections>
            	<read-connections>10</read-connections>
            	<dso jdbcDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver" driver="JDBC" connectionString="jdbc:sqlserver://HOST:PORT;sendStringParametersAsUnicode=false" defaultDB="DB_NAME" userId="USERNAME" xmlencoding="false" password="BASE64_PASSWORD" update="true">
            		<query-cache>
            			<size>50</size>
            			<refresh-interval>3600</refresh-interval>
            		</query-cache>
            		<cursor-cache>
            			<size>50</size>
            			<refresh-interval>3600</refresh-interval>
            		</cursor-cache>
            	</dso>
            	<connection-pool>
            		<min-update-connections>1</min-update-connections>
            		<min-read-connections>1</min-read-connections>
            		<refresh-interval>3600</refresh-interval>
            	</connection-pool>
            </configuration>
            """;

    private final static String CONSTRUCTOR_XML = """
            <dataset>
                <constructor language="DBSQL">
                    <query>select * from Employees where EmployeeID = :EmployeeID</query>
                    <parameters>
                        <EmployeeID dd="Employees.EmployeeID">1</EmployeeID>
                    </parameters>
                </constructor>
            </dataset>
            """;

    @Test
    void testConnection() throws XMLException, UnsupportedEncodingException {
        int dso = new Document().parseString(DSO_XML);
        DBConnectionPool connectionPool = DBConnectionPool._createInstance(dso, null, null, null);

        WCPDBConnection readConnection = connectionPool.getReadConnection();
        int constructor = new Document().parseString(CONSTRUCTOR_XML);
        int result = readConnection.query(constructor, null);

        if (result < 0) {
            // Error happened
            readConnection.abortCurrentTransaction(); // for write connections
        } else {
            // Processing is success
            System.out.println(Node.writeToString(result, true));
            readConnection.commitCurrentTransaction();
        }

        connectionPool.putReadConnection(readConnection);
    }
}
