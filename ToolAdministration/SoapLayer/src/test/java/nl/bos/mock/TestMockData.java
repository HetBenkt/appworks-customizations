package nl.bos.mock;

public enum TestMockData {
    ;


    static final String soapResponseGetUserDetails = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP:Header>
                    <header xmlns="http://schemas.cordys.com/General/1.0/">
                        <msg-id>080027c2-550f-a1ed-a27b-486cff242981</msg-id>
                        <messageoptions noreply="true"/>
                    </header>
                </SOAP:Header>
                <SOAP:Body>
                    <GetUserDetailsResponse xmlns="http://schemas.cordys.com/notification/workflow/1.0">
                        <User>
                            <UserDN>cn=sysadmin,cn=organizational users,o=appworks_tips,cn=cordys,cn=defaultInst,o=22.3.com</UserDN>
                            <UserDisplayName>sysadmin</UserDisplayName>
                            <UserId>080027c2-550f-a1ed-a27b-486d23c32981</UserId>
                            <CanQueryOrgUnits>true</CanQueryOrgUnits>
                            <ManagerFor>
                                <Target>
                                    <Id>cn=Entity Workflow Template Developer,cn=OpenText Entity Workflow Template Components,cn=cordys,cn=defaultInst,o=22.3.com</Id>
                                    <Name>Entity Workflow Template Developer</Name>
                                    <Type>role</Type>
                                </Target>
                            </ManagerFor>
                        </User>
                    </GetUserDetailsResponse>
                </SOAP:Body>
            </SOAP:Envelope>
            """;

    static final String soapResponseGetSoapProcessors = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP:Header>
                    <header xmlns="http://schemas.cordys.com/General/1.0/">
                        <msg-id>080027c2-550f-a1ed-a27b-da83ad2e9aa7</msg-id>
                        <messageoptions noreply="true"/>
                    </header>
                </SOAP:Header>
                <SOAP:Body>
                    <GetSoapProcessorsResponse xmlns="http://schemas.cordys.com/1.0/ldap">
                        <tuple>
                            <old>
                                <entry dn="cn=Auditing,cn=Auditing,cn=soap nodes,o=system,cn=cordys,cn=defaultInst,o=22.3.com" entryUUID="96cb1bb0-a28e-103c-90ae-45c9da7f20fd">
                                    <bussoapprocessorconfiguration>
                                        <string>&lt;configurations&gt;&lt;spyPublish&gt;false&lt;/spyPublish&gt;&lt;gracefulCompleteTime&gt;15&lt;/gracefulCompleteTime&gt;&lt;abortTime&gt;5&lt;/abortTime&gt;&lt;cancelReplyInterval&gt;30000&lt;/cancelReplyInterval&gt;&lt;routing ui_algorithm="failover" ui_type="loadbalancing"&gt;&lt;preference&gt;2&lt;/preference&gt;&lt;/routing&gt;&lt;configuration implementation="com.cordys.audit.connector.AuditConnector" htmFile="/cordys/com/cordys/audit/auditConnector.caf"&gt;&lt;classpath&gt;
            			&lt;/classpath&gt;&lt;startupDependency&gt;&lt;namespace&gt;http://schemas.cordys.com/1.0/xmlstore&lt;/namespace&gt;&lt;/startupDependency&gt;&lt;component name="Connection Pool"&gt;&lt;datasource&gt;&lt;type&gt;Relational&lt;/type&gt;&lt;name&gt;Cordys System#system&lt;/name&gt;&lt;/datasource&gt;&lt;xmlEncoding&gt;false&lt;/xmlEncoding&gt;&lt;precedence/&gt;&lt;cursorCacheSize&gt;50&lt;/cursorCacheSize&gt;&lt;cursorCacheRefreshInterval&gt;30&lt;/cursorCacheRefreshInterval&gt;&lt;maximumWriteConnections&gt;5&lt;/maximumWriteConnections&gt;&lt;maximumReadConnections&gt;5&lt;/maximumReadConnections&gt;&lt;minimumReadConnections&gt;1&lt;/minimumReadConnections&gt;&lt;minimumWriteConnections&gt;1&lt;/minimumWriteConnections&gt;&lt;multithreaded&gt;true&lt;/multithreaded&gt;&lt;xsiNilForNullData&gt;true&lt;/xsiNilForNullData&gt;&lt;/component&gt;&lt;/configuration&gt;&lt;/configurations&gt;</string>
                                    </bussoapprocessorconfiguration>
                                    <computer>
                                        <string>appworks</string>
                                    </computer>
                                    <description>
                                        <string>This SOAP Node is configured by the installer.</string>
                                    </description>
                                    <busosprocesshost>
                                        <string>cn=Application Server,cn=monitor@appworks,cn=monitorsoapnode@appworks,cn=soap nodes,o=system,cn=cordys,cn=defaultInst,o=22.3.com</string>
                                    </busosprocesshost>
                                    <automaticstart>
                                        <string>false</string>
                                    </automaticstart>
                                    <cn>
                                        <string>Auditing</string>
                                    </cn>
                                    <objectclass>
                                        <string>top</string>
                                        <string>bussoapprocessor</string>
                                    </objectclass>
                                </entry>
                            </old>
                        </tuple>
                        <tuple>
                            <old>
                                <entry dn="cn=Business Process Management,cn=Business Process Management,cn=soap nodes,o=system,cn=cordys,cn=defaultInst,o=22.3.com" entryUUID="bd4e195c-a290-103c-96ff-45c9da7f20fd">
                                    <bussoapprocessorconfiguration>
                                        <string>&lt;configurations&gt;&lt;cancelReplyInterval&gt;30000&lt;/cancelReplyInterval&gt;&lt;gracefulCompleteTime&gt;15&lt;/gracefulCompleteTime&gt;&lt;abortTime&gt;5&lt;/abortTime&gt;&lt;routing ui_type="loadbalancing" ui_algorithm="failover"&gt;&lt;preference&gt;1&lt;/preference&gt;&lt;/routing&gt;&lt;configuration implementation="com.cordys.bpm.service.BPMApplicationConnector" htmFile="com/cordys/bpmengine/applicationconnector/bpmengineconfiguration.caf"&gt;&lt;classpath&gt;&lt;location&gt;components/bpmengine/bpmengine.jar&lt;/location&gt;&lt;location&gt;components/wsappserver/wsappserver.jar&lt;/location&gt;&lt;location&gt;components/scheduler/scheduler.jar&lt;/location&gt;&lt;location&gt;components/ruleengine/ruleengine.jar&lt;/location&gt;&lt;location&gt;components/expressioneval/expressioneval.jar&lt;/location&gt;&lt;location&gt;components/statemachine/statemachine.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/dom4j.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/poi-ooxml.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/poi-ooxml-schemas.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/poi.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/commons-collections4.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/commons-compress.jar&lt;/location&gt;&lt;/classpath&gt;&lt;startupDependency&gt;&lt;namespace&gt;http://schemas.cordys.com/1.0/xmlstore&lt;/namespace&gt;&lt;namespace&gt;http://schemas.cordys.com/notification/workflow/1.0&lt;/namespace&gt;&lt;namespace&gt;http://schemas.cordys.com/notification/workflow/task/1.0&lt;/namespace&gt;&lt;namespace&gt;http://schemas.cordys.com/notification/workflow/task/escalation/1.0&lt;/namespace&gt;&lt;namespace&gt;http://schemas.cordys.com/buscalendar/runtime/BusinessCalendar/1.0&lt;/namespace&gt;&lt;namespace&gt;http://schemas.cordys.com/documentstore/default/1.0&lt;/namespace&gt;&lt;/startupDependency&gt;&lt;component name="Process Engine Database Connection Pool"&gt;&lt;cursorCacheSize&gt;50&lt;/cursorCacheSize&gt;&lt;cursorCacheRefreshInterval&gt;30&lt;/cursorCacheRefreshInterval&gt;&lt;maximumReadConnections&gt;10&lt;/maximumReadConnections&gt;&lt;maximumWriteConnections&gt;10&lt;/maximumWriteConnections&gt;&lt;minimumReadConnections&gt;1&lt;/minimumReadConnections&gt;&lt;minimumWriteConnections&gt;1&lt;/minimumWriteConnections&gt;&lt;auditInfo/&gt;&lt;xmlEncoding&gt;false&lt;/xmlEncoding&gt;&lt;multithreaded&gt;true&lt;/multithreaded&gt;&lt;precedence&gt;data&lt;/precedence&gt;&lt;reconnectionInterval&gt;5000&lt;/reconnectionInterval&gt;&lt;reconnectionAttempts&gt;1&lt;/reconnectionAttempts&gt;&lt;requestThreshold&gt;1000&lt;/requestThreshold&gt;&lt;datasource&gt;&lt;type&gt;Relational&lt;/type&gt;&lt;name&gt;Cordys System#system&lt;/name&gt;&lt;/datasource&gt;&lt;conversionConfig&gt;&lt;conversion implementation="com.cordys.conversion.compression.GZipCompression"&gt;&lt;database id="*"&gt;&lt;table id="PROCESS_INSTANCE_DATA"&gt;&lt;field id="INSTANCE_DATA"/&gt;&lt;/table&gt;&lt;table id="PROCESS_INSTANCE"&gt;&lt;field id="MESSAGE"/&gt;&lt;field id="MESSAGE_MAP"/&gt;&lt;field id="OUTPUT_MESSAGE"/&gt;&lt;field id="ERROR_TEXT"/&gt;&lt;/table&gt;&lt;table id="PROCESS_ACTIVITY"&gt;&lt;field id="PARTICIPANT"/&gt;&lt;field id="REQUEST"/&gt;&lt;field id="RESPONSE"/&gt;&lt;/table&gt;&lt;table id="CASE_INSTANCE_DATA"&gt;&lt;field id="INSTANCE_DATA"/&gt;&lt;/table&gt;&lt;table id="CASE_INSTANCE_JOURNAL"&gt;&lt;field id="REQUEST"/&gt;&lt;/table&gt;&lt;table id="ACTIVITY_INSTANCE"&gt;&lt;field id="ACTIVITY_DATA"/&gt;&lt;/table&gt;&lt;table id="CASE_ERROR_INFO"&gt;&lt;field id="ERROR_TEXT"/&gt;&lt;/table&gt;&lt;table id="CASE_ACTIVITY"&gt;&lt;field id="DEFINITION"/&gt;&lt;/table&gt;&lt;table id="BPM_MODEL_REVISION"&gt;&lt;field id="RUNTIME_MODEL"/&gt;&lt;/table&gt;&lt;table id="CASE_MODEL_IDENTIFIER"&gt;&lt;field id="LOOKUP"/&gt;&lt;/table&gt;&lt;table id="ARCHIVE_POLICY"&gt;&lt;field id="POLICY_QUERY"/&gt;&lt;field id="SCHEDULE_XML"/&gt;&lt;/table&gt;&lt;table id="ARCHIVE_LOG"&gt;&lt;field id="ERROR_LOG"/&gt;&lt;/table&gt;&lt;table id="ARCHIVE_REGISTRY"&gt;&lt;field id="LOADED_TRANSACTIONS"/&gt;&lt;field id="ERROR_TEXT"/&gt;&lt;field id="ARCHIVE_INFO"/&gt;&lt;field id="LOADED_FILES"/&gt;&lt;/table&gt;&lt;/database&gt;&lt;/conversion&gt;&lt;/conversionConfig&gt;&lt;/component&gt;&lt;component name="Business Process Engine"&gt;&lt;threadPoolSize&gt;5&lt;/threadPoolSize&gt;&lt;processModelCacheSize&gt;200&lt;/processModelCacheSize&gt;&lt;crashRecovery&gt;true&lt;/crashRecovery&gt;&lt;activateDebugPoints&gt;true&lt;/activateDebugPoints&gt;&lt;adminEnabled&gt;true&lt;/adminEnabled&gt;&lt;inProcProcessExecution&gt;true&lt;/inProcProcessExecution&gt;&lt;QThresholdLimit&gt;5&lt;/QThresholdLimit&gt;&lt;priorityEnabled&gt;true&lt;/priorityEnabled&gt;&lt;priorityAlgorithm&gt;com.cordys.bpm.utils.priorityqueue.algorithm.AgingAlgorithm&lt;/priorityAlgorithm&gt;&lt;enableAging&gt;true&lt;/enableAging&gt;&lt;agingTime&gt;3600&lt;/agingTime&gt;&lt;priorityLevels&gt;5&lt;/priorityLevels&gt;&lt;defaultPriority&gt;3&lt;/defaultPriority&gt;&lt;/component&gt;&lt;component xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/" name="Scheduler DBConnectionPool"&gt;&lt;xmlEncoding&gt;false&lt;/xmlEncoding&gt;&lt;precedence&gt;data&lt;/precedence&gt;&lt;cursorCacheSize&gt;50&lt;/cursorCacheSize&gt;&lt;cursorCacheRefreshInterval&gt;30&lt;/cursorCacheRefreshInterval&gt;&lt;maximumWriteConnections&gt;5&lt;/maximumWriteConnections&gt;&lt;maximumReadConnections&gt;5&lt;/maximumReadConnections&gt;&lt;minimumReadConnections&gt;1&lt;/minimumReadConnections&gt;&lt;minimumWriteConnections&gt;1&lt;/minimumWriteConnections&gt;&lt;multithreaded&gt;true&lt;/multithreaded&gt;&lt;datasource&gt;&lt;type&gt;Relational&lt;/type&gt;&lt;name&gt;Cordys System#system&lt;/name&gt;&lt;/datasource&gt;&lt;/component&gt;&lt;/configuration&gt;&lt;configuration implementation="com.cordys.bpm.pim.service.PIMQueryConnector" htmFile="com/cordys/bpmengine/applicationconnector/pimconfiguration.caf"&gt;&lt;classpath&gt;&lt;location&gt;components/bpmengine/bpmengine.jar&lt;/location&gt;&lt;location&gt;components/dbconnectors/dbconnectors.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/dom4j.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/poi-ooxml.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/poi-ooxml-schemas.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/poi.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/commons-collections4.jar&lt;/location&gt;&lt;location&gt;components/bpmengine/commons-compress.jar&lt;/location&gt;&lt;/classpath&gt;&lt;startupDependency&gt;&lt;namespace&gt;http://schemas.cordys.com/1.0/xmlstore&lt;/namespace&gt;&lt;/startupDependency&gt;&lt;component name="PIM Query Connector Connection Pool"&gt;&lt;cursorCacheSize&gt;50&lt;/cursorCacheSize&gt;&lt;cursorCacheRefreshInterval&gt;30&lt;/cursorCacheRefreshInterval&gt;&lt;maximumReadConnections&gt;10&lt;/maximumReadConnections&gt;&lt;maximumWriteConnections&gt;10&lt;/maximumWriteConnections&gt;&lt;minimumReadConnections&gt;1&lt;/minimumReadConnections&gt;&lt;minimumWriteConnections&gt;1&lt;/minimumWriteConnections&gt;&lt;auditInfo/&gt;&lt;xmlEncoding&gt;false&lt;/xmlEncoding&gt;&lt;multithreaded&gt;true&lt;/multithreaded&gt;&lt;precedence&gt;data&lt;/precedence&gt;&lt;reconnectionInterval&gt;5000&lt;/reconnectionInterval&gt;&lt;reconnectionAttempts&gt;1&lt;/reconnectionAttempts&gt;&lt;requestThreshold&gt;1000&lt;/requestThreshold&gt;&lt;datasource&gt;&lt;type&gt;Relational&lt;/type&gt;&lt;name&gt;Cordys System#system&lt;/name&gt;&lt;/datasource&gt;&lt;conversionConfig&gt;&lt;conversion implementation="com.cordys.conversion.compression.GZipCompression"&gt;&lt;database id="*"&gt;&lt;table id="PROCESS_INSTANCE_DATA"&gt;&lt;field id="INSTANCE_DATA"/&gt;&lt;/table&gt;&lt;table id="PROCESS_INSTANCE"&gt;&lt;field id="MESSAGE"/&gt;&lt;field id="MESSAGE_MAP"/&gt;&lt;field id="OUTPUT_MESSAGE"/&gt;&lt;field id="ERROR_TEXT"/&gt;&lt;/table&gt;&lt;table id="PROCESS_ACTIVITY"&gt;&lt;field id="PARTICIPANT"/&gt;&lt;field id="REQUEST"/&gt;&lt;field id="RESPONSE"/&gt;&lt;/table&gt;&lt;/database&gt;&lt;/conversion&gt;&lt;/conversionConfig&gt;&lt;/component&gt;&lt;/configuration&gt;&lt;/configurations&gt;</string>
                                    </bussoapprocessorconfiguration>
                                    <computer>
                                        <string>appworks</string>
                                    </computer>
                                    <description>
                                        <string>This SOAP Node is configured by the installer.</string>
                                    </description>
                                    <busosprocesshost>
                                        <string>cn=Application Server,cn=monitor@appworks,cn=monitorsoapnode@appworks,cn=soap nodes,o=system,cn=cordys,cn=defaultInst,o=22.3.com</string>
                                    </busosprocesshost>
                                    <automaticstart>
                                        <string>true</string>
                                    </automaticstart>
                                    <cn>
                                        <string>Business Process Management</string>
                                    </cn>
                                    <objectclass>
                                        <string>top</string>
                                        <string>bussoapprocessor</string>
                                    </objectclass>
                                </entry>
                            </old>
                        </tuple>
                    </GetSoapProcessorsResponse>
                </SOAP:Body>
            </SOAP:Envelope>
            """;

    static final String soapResponseSearchLDAP = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP:Header>
                    <header xmlns="http://schemas.cordys.com/General/1.0/">
                        <msg-id>080027c2-550f-a1ed-a27c-5329a2db15aa</msg-id>
                        <messageoptions noreply="true"/>
                    </header>
                </SOAP:Header>
                <SOAP:Body>
                    <SearchLDAPResponse
                        xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/"
                        xmlns="http://schemas.cordys.com/1.0/ldap">
                        <tuple>
                            <old>
                                <entry dn="cn=Auditing,cn=Auditing,cn=soap nodes,o=system,cn=cordys,cn=defaultInst,o=23.1.com" entryUUID="bc0aee26-3408-103d-9da4-d5da8ac8f262">
                                    <bussoapprocessorconfiguration>
                                        <string>
                                            <configurations>
                                                <spyPublish>false</spyPublish>
                                                <gracefulCompleteTime>15</gracefulCompleteTime>
                                                <abortTime>5</abortTime>
                                                <cancelReplyInterval>30000</cancelReplyInterval>
                                                <routing ui_algorithm="failover" ui_type="loadbalancing">
                                                    <preference>2</preference>
                                                </routing>
                                                <configuration implementation="com.cordys.audit.connector.AuditConnector" htmFile="/cordys/com/cordys/audit/auditConnector.caf">
                                                    <classpath></classpath>
                                                    <startupDependency>
                                                        <namespace>http://schemas.cordys.com/1.0/xmlstore</namespace>
                                                    </startupDependency>
                                                    <component name="Connection Pool">
                                                        <datasource>
                                                            <type>Relational</type>
                                                            <name>Cordys System#system</name>
                                                        </datasource>
                                                        <xmlEncoding>false</xmlEncoding>
                                                        <precedence/>
                                                        <cursorCacheSize>50</cursorCacheSize>
                                                        <cursorCacheRefreshInterval>30</cursorCacheRefreshInterval>
                                                        <maximumWriteConnections>5</maximumWriteConnections>
                                                        <maximumReadConnections>5</maximumReadConnections>
                                                        <minimumReadConnections>1</minimumReadConnections>
                                                        <minimumWriteConnections>1</minimumWriteConnections>
                                                        <multithreaded>true</multithreaded>
                                                        <xsiNilForNullData>true</xsiNilForNullData>
                                                    </component>
                                                </configuration>
                                            </configurations>
                                        </string>
                                    </bussoapprocessorconfiguration>
                                    <computer>
                                        <string>appworks</string>
                                    </computer>
                                    <description>
                                        <string>This SOAP Node is configured by the installer.</string>
                                    </description>
                                    <busosprocesshost>
                                        <string>cn=Application Server,cn=monitor@appworks,cn=monitorsoapnode@appworks,cn=soap nodes,o=system,cn=cordys,cn=defaultInst,o=23.1.com</string>
                                    </busosprocesshost>
                                    <automaticstart>
                                        <string>false</string>
                                    </automaticstart>
                                    <cn>
                                        <string>Auditing</string>
                                    </cn>
                                    <objectclass>
                                        <string>top</string>
                                        <string>bussoapprocessor</string>
                                    </objectclass>
                                </entry>
                            </old>
                        </tuple>
                        <tuple>
                            <old>
                                <entry dn="cn=Collaborative Workspace,cn=Collaborative Workspace,cn=soap nodes,o=system,cn=cordys,cn=defaultInst,o=23.1.com" entryUUID="4c466f74-3409-103d-9e36-d5da8ac8f262">
                                    <bussoapprocessorconfiguration>
                                        <string>
                                            <configurations autoStartCount="3">
                                                <cancelReplyInterval>30000</cancelReplyInterval>
                                                <gracefulCompleteTime>15</gracefulCompleteTime>
                                                <abortTime>5</abortTime>
                                                <mustSuspendContainerManagedTransaction>false</mustSuspendContainerManagedTransaction>
                                                <jreconfig>
                                                    <param value="-cp /opt/opentext/AppWorksPlatform/defaultInst/crosscontext/*.jar"/>
                                                    <param value="-Xmx2500M"/>
                                                    <param value="-Dbus.xml.vm.maxsize=2000"/>
                                                </jreconfig>
                                                <routing ui_type="loadbalancing" ui_algorithm="failover">
                                                    <preference>1</preference>
                                                </routing>
                                                <configuration implementation="com.cordys.cws.internal.soap.CWSSOAPConnector" htmfile="/cordys/com/cordys/cws/applicationconnector/cwsservicecontainer.caf">
                                                    <classpath>
                                                        <location>components/cws/cws.jar</location>
                                                    </classpath>
                                                    <component name="XDSRepository">
                                                        <component name="DocumentCache">
                                                            <cacheSize>500</cacheSize>
                                                        </component>
                                                        <component name="XDS_SyncUp">
                                                            <ipAddress>appworks</ipAddress>
                                                            <portNo>24134</portNo>
                                                            <manual>false</manual>
                                                        </component>
                                                        <component name="CWSDBConnectionPool">
                                                            <xmlEncoding>false</xmlEncoding>
                                                            <precedence/>
                                                            <cursorCacheSize>50</cursorCacheSize>
                                                            <cursorCacheRefreshInterval>30</cursorCacheRefreshInterval>
                                                            <maximumWriteConnections>5</maximumWriteConnections>
                                                            <maximumReadConnections>3</maximumReadConnections>
                                                            <minimumReadConnections>1</minimumReadConnections>
                                                            <minimumWriteConnections>1</minimumWriteConnections>
                                                            <multithreaded>true</multithreaded>
                                                            <xsiNilForNullData>true</xsiNilForNullData>
                                                            <readEmptyStringAsNull>false</readEmptyStringAsNull>
                                                            <writeEmptyStringAsNull>false</writeEmptyStringAsNull>
                                                            <datasource>
                                                                <type>Relational</type>
                                                                <name>Cordys System#system</name>
                                                            </datasource>
                                                        </component>
                                                    </component>
                                                </configuration>
                                            </configurations>
                                        </string>
                                    </bussoapprocessorconfiguration>
                                    <computer>
                                        <string>appworks</string>
                                    </computer>
                                    <description>
                                        <string>This SOAP Node is configured by the installer.</string>
                                    </description>
                                    <busosprocesshost>
                                        <string>cn=Application Server,cn=monitor@appworks,cn=monitorsoapnode@appworks,cn=soap nodes,o=system,cn=cordys,cn=defaultInst,o=23.1.com</string>
                                    </busosprocesshost>
                                    <automaticstart>
                                        <string>true</string>
                                    </automaticstart>
                                    <cn>
                                        <string>Collaborative Workspace</string>
                                    </cn>
                                    <objectclass>
                                        <string>top</string>
                                        <string>bussoapprocessor</string>
                                    </objectclass>
                                </entry>
                            </old>
                        </tuple>
                    </SearchLDAPResponse>
                </SOAP:Body>
            </SOAP:Envelope>
            """;

    /*
    <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
      <SOAP:Body>
        <GetMethodSets xmlns="http://schemas.cordys.com/1.0/ldap">
          <labeleduri>PARAMETER</labeleduri>
          <sort>PARAMETER</sort>
          <dn>PARAMETER</dn>
        </GetMethodSets>
      </SOAP:Body>
    </SOAP:Envelope>
    */
    //TODO Extract the above XML from XSD data (it's mixed data)! The big Jackson trick is to convert "<testConvert>Doe</testConvert>" to "&lt;testConvert&gt;Doe&lt;/testConvert&gt;"
    public static final String soapResponseGetLDAPObject = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP:Header>
                    <header xmlns="http://schemas.cordys.com/General/1.0/">
                        <msg-id>080027c2-550f-a1ed-a27c-5329a2db15aa</msg-id>
                        <messageoptions noreply="true"/>
                    </header>
                </SOAP:Header>
                <SOAP:Body>
                    <GetLDAPObjectResponse xmlns="http://schemas.cordys.com/1.0/ldap" xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
                       <tuple>
                          <old>
                             <entry dn="cn=GetMethodSets,cn=Method Set LDAP 1.0,cn=Cordys LDAP Connector,cn=cordys,cn=defaultInst,o=23.1.com" entryUUID="ef683716-3407-103d-9cbd-d5da8ac8f262">
                                <busmethodsignature>
                                   <string>
                                      <wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:cordys="http://schemas.cordys.com/General/1.0/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://schemas.cordys.com/1.0/ldap" name="GetMethodSets" targetNamespace="http://schemas.cordys.com/1.0/ldap">
                                         <wsdl:types>
                                            <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" targetNamespace="http://schemas.cordys.com/1.0/ldap" attributeFormDefault="unqualified" elementFormDefault="qualified">
                                               <xsd:import namespace="http://schemas.cordys.com/General/1.0/" schemaLocation="http://schemas.cordys.com/CordysSchemas//CordysFaultDetails.xsd" />
                                               <xsd:element name="GetMethodSets">
                                                  <xsd:complexType>
                                                     <xsd:all>
                                                        <xsd:element name="labeleduri" type="xsd:string" />
                                                        <xsd:element name="sort" type="xsd:string" minOccurs="0" />
                                                        <xsd:element name="dn" type="xsd:string" />
                                                     </xsd:all>
                                                  </xsd:complexType>
                                               </xsd:element>
                                               <xsd:element name="GetMethodSetsResponse">
                                                  <xsd:complexType>
                                                     <xsd:sequence>
                                                        <xsd:element name="tuple" minOccurs="0" maxOccurs="unbounded">
                                                           <xsd:complexType>
                                                              <xsd:sequence>
                                                                 <xsd:element name="old">
                                                                    <xsd:complexType>
                                                                       <xsd:sequence>
                                                                          <xsd:element name="entry">
                                                                             <xsd:complexType>
                                                                                <xsd:all>
                                                                                   <xsd:element name="cn" minOccurs="0">
                                                                                      <xsd:complexType>
                                                                                         <xsd:sequence>
                                                                                            <xsd:element name="string" type="xsd:string" />
                                                                                         </xsd:sequence>
                                                                                      </xsd:complexType>
                                                                                   </xsd:element>
                                                                                   <xsd:element name="description" minOccurs="0">
                                                                                      <xsd:complexType>
                                                                                         <xsd:sequence>
                                                                                            <xsd:element name="string" type="xsd:string" />
                                                                                         </xsd:sequence>
                                                                                      </xsd:complexType>
                                                                                   </xsd:element>
                                                                                   <xsd:element name="aci" minOccurs="0">
                                                                                      <xsd:complexType>
                                                                                         <xsd:sequence>
                                                                                            <xsd:element name="string" type="xsd:string" />
                                                                                         </xsd:sequence>
                                                                                      </xsd:complexType>
                                                                                   </xsd:element>
                                                                                   <xsd:element name="objectclass">
                                                                                      <xsd:complexType>
                                                                                         <xsd:sequence>
                                                                                            <xsd:element name="string" maxOccurs="unbounded" type="xsd:string" />
                                                                                         </xsd:sequence>
                                                                                      </xsd:complexType>
                                                                                   </xsd:element>
                                                                                   <xsd:element name="labeleduri" minOccurs="0">
                                                                                      <xsd:complexType>
                                                                                         <xsd:sequence>
                                                                                            <xsd:element name="string" type="xsd:string" />
                                                                                         </xsd:sequence>
                                                                                      </xsd:complexType>
                                                                                   </xsd:element>
                                                                                   <xsd:element name="implementationclass" minOccurs="0">
                                                                                      <xsd:complexType>
                                                                                         <xsd:sequence>
                                                                                            <xsd:element name="string" type="xsd:string" />
                                                                                         </xsd:sequence>
                                                                                      </xsd:complexType>
                                                                                   </xsd:element>
                                                                                </xsd:all>
                                                                                <xsd:attribute name="dn" type="xsd:string" />
                                                                                <xsd:attribute name="entryUUID" type="xsd:string" />
                                                                             </xsd:complexType>
                                                                          </xsd:element>
                                                                       </xsd:sequence>
                                                                    </xsd:complexType>
                                                                 </xsd:element>
                                                              </xsd:sequence>
                                                           </xsd:complexType>
                                                        </xsd:element>
                                                     </xsd:sequence>
                                                  </xsd:complexType>
                                               </xsd:element>
                                            </xsd:schema>
                                         </wsdl:types>
                                         <wsdl:message name="CordysFaultDetail">
                                            <wsdl:part name="FaultDetail" element="cordys:FaultDetails" />
                                         </wsdl:message>
                                         <wsdl:message name="GetMethodSets">
                                            <wsdl:part element="tns:GetMethodSets" name="body" />
                                         </wsdl:message>
                                         <wsdl:message name="GetMethodSetsResponse">
                                            <wsdl:part element="tns:GetMethodSetsResponse" name="body" />
                                         </wsdl:message>
                                         <wsdl:portType name="GetMethodSetsPortType">
                                            <wsdl:operation name="GetMethodSetsoperation">
                                               <wsdl:input message="tns:GetMethodSets" />
                                               <wsdl:output message="tns:GetMethodSetsResponse" />
                                               <wsdl:fault name="FaultDetail" message="tns:CordysFaultDetail" />
                                            </wsdl:operation>
                                         </wsdl:portType>
                                         <wsdl:binding name="GetMethodSetsBinding" type="tns:GetMethodSetsPortType">
                                            <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http" />
                                            <wsdl:operation name="GetMethodSetsoperation">
                                               <soap:operation style="document" soapAction="" />
                                               <wsdl:input>
                                                  <soap:body use="literal" />
                                               </wsdl:input>
                                               <wsdl:output>
                                                  <soap:body use="literal" />
                                               </wsdl:output>
                                               <wsdl:fault name="FaultDetail">
                                                  <soap:fault name="FaultDetail" use="literal" />
                                               </wsdl:fault>
                                            </wsdl:operation>
                                         </wsdl:binding>
                                         <wsdl:service name="GetMethodSetsUsers">
                                            <wsdl:port binding="tns:GetMethodSetsBinding" name="GetMethodSetsPort">
                                               <soap:address location="com.eibus.web.soap.Gateway.wcp" />
                                            </wsdl:port>
                                         </wsdl:service>
                                      </wsdl:definitions>
                                   </string>
                                </busmethodsignature>
                                <cn>
                                   <string>GetMethodSets</string>
                                </cn>
                                <objectclass>
                                   <string>top</string>
                                   <string>busmethod</string>
                                </objectclass>
                                <busmethodimplementation>
                                   <string>
                                      <implementation type="LDAP">
                                         <SearchLDAP>
                                            <dn>:dn</dn>
                                            <scope>2</scope>
                                            <returnValues>true</returnValues>
                                            <filter>&amp;(objectclass=busmethodset)(labeleduri=:labeleduri)</filter>
                                            <sort>:sort</sort>
                                         </SearchLDAP>
                                      </implementation>
                                   </string>
                                </busmethodimplementation>
                             </entry>
                          </old>
                       </tuple>
                    </GetLDAPObjectResponse>
                </SOAP:Body>
            </SOAP:Envelope>
            """;

    static final String soapResponseSamlToken = """
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:wsse="urn:api.ecm.opentext.com">
            	<SOAP-ENV:Header xmlns:wsse="urn:api.ecm.opentext.com" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            		<header xmlns:wsse="urn:api.ecm.opentext.com" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns="http://schemas.cordys.com/General/1.0/">
            			<msg-id>080027c2-550f-a1ed-8700-cc06a8f8261d</msg-id>
            			<messageoptions noreply="true"/>
            		</header>
            	</SOAP-ENV:Header>
            	<SOAP-ENV:Body>
            		<samlp:Response xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" IssueInstant="2022-08-14T18:59:13.590Z" MajorVersion="1" MinorVersion="1" ResponseID="A080027c2-550f-a1ed-8700-cc06be6a261d">
            			<Signature xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns="http://www.w3.org/2000/09/xmldsig#">
            				<SignedInfo>
            					<CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
            					<SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
            					<Reference URI="#A080027c2-550f-a1ed-8700-cc06c236a61d">
            						<Transforms>
            							<Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
            							<Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
            						</Transforms>
            						<DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
            						<DigestValue>JvZcy2WwXwmWUZG6ug5XwAGBhTaZsppEOI88ngiOMmk=</DigestValue>
            					</Reference>
            				</SignedInfo>
            				<SignatureValue>XdJ+Op8BId3DyVgnyQqEZuiotqv+AJH3lLQuJNnt8C7B2Hp70j04JqZpi06i9yBJlABORWFiF5mz
            					/MW0ebx7lQlqr81O0NzyqhH92HHU1kkKUVQY92DQhtd5Y5nHQkM8OkCIo+qfEMH42H90bIZku+FZ
            					N69XFI+oAUAsYSY1iL6Rp4bdaE08/eY+ZEFLeFox/i41i08c6Y4Ia9QHYuLBIr1ANI/4Ii68/FZu
            					fbXtGds5GeqDVhnRu4XpPw5zTl7ENlSnygeaboUnsOcSWTb/XaknWKxuOkosbuVRqmDtQ9/a3rcn
            					EKqm89pD9gITF4CvGXo2w/gghE92D0WH7aZjEQ==</SignatureValue>
            			</Signature>
            			<samlp:Status xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol">
            				<samlp:StatusCode Value="samlp:Success"/>
            			</samlp:Status>
            			<saml:Assertion xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" AssertionID="A080027c2-550f-a1ed-8700-cc06c236a61d" IssueInstant="2022-08-14T18:59:13.591Z" Issuer="https://www.cordys.com/SSO" MajorVersion="1" MinorVersion="1">
            				<saml:Conditions NotBefore="2022-08-14T18:54:13.590Z" NotOnOrAfter="2022-08-15T02:59:13.590Z"/>
            				<saml:AuthenticationStatement AuthenticationInstant="2022-08-14T18:59:13.590Z" AuthenticationMethod="urn:oasis:names:tc:SAML:1.0:am:password">
            					<saml:Subject>
            						<saml:NameIdentifier>anonymous</saml:NameIdentifier>
            					</saml:Subject>
            				</saml:AuthenticationStatement>
            			</saml:Assertion>
            			<samlp:AssertionArtifact xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol">e0pBVkEtQUVTL0dDTS9Ob1BhZGRpbmd9WWGZ9li84Pl0ZLM4LqD57o8Q1lUNUlfFxq8t+1kg7pbbtnEe4zaDioNaAELZioW3Flz7G0e4hHeR/Mv2/ibul6rCzX5FoCs0BciHSbOc5M7dfcY2fC/Np8dRefcVWQZKW58u</samlp:AssertionArtifact>
            		</samlp:Response>
            	</SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            """;
    static final String jsonResponseOtdsAuthentication = """
            {
               "token":"6F7464735F73657373696F6E5F6B6579",
               "userId":"awdev@awp",
               "ticket":"*VER2*ABRhBKQxWCK_ETWlxqgGal7AiACg6wAQU0oTRMPfhFOm3iEFGne0qwKQ0nVQ-U08c7Tld42YQ6lpWcAxgvkqetKbiPmWoXupu_QUMh88AVfOFsaaOzpqsn2sQx9N6b9IZ68oNKXOQDVmI1ZnrdzuH-K6C1A_oEHq89kN9TBJ4EZK3uFK2zlVOpV3HfLAIqN5Te_entdIPdU4Mh5pSLsg4Bi3ioTXBWNbQDdCcJ-DWOSTddShgrJNTUrgxJggHeQKEaQIIRji3Wx8lZEWM4s7te7rhJrgdTm8UDjgoyFg4nwijlvZfpoyC33542Ti7ejwnkt6Ij5f8Qzy_f2DoliJSZoMl63ewZwd362Lka0--qfa8YsHXMvShU6NoRHtnS7U56OXZDHAdU5Y3dnxij891lOzJBuSaVyHihIqXCo2Ri-LTwcbJNJJ_c6OkO7rs29tD51iP0kDu50C8REedS3vOpylj_9atf7LsfDYDa6IsovAGgFYZmbkYoEY_Hml_Lh1Jxw4lK3mPbb1Avmc99Pls3yk-5veNVzPhWRifESLYZueeF2KLZyOymJeNEAgr8FabCQKISi7Z-g53WLV3atxQ7x0b6chIZPWymEL_gAkQ4H_7Mne8vx37gdaIIvBqQ4Az1l6lwreUtkLiX2mwPr4AEfyYMh_2bwnbFYelKTaA04yHEOxgz9e57_PaFnTz3ph1Q7vUmBTw2KiR9HKfRvFuavB1m3D7CPrf8070oIPhUhcFnhkDOTAKpgQ7gdnKYOvLCzYi6-E2v6q_ivk3HZx_-RMldxLtGchQCHSy2T6gfo8DjLrJ5kSRyhJqjZre9zDFMIyV2aeLfJ81eELzRQqFqZsyQSkA-Xpgl0-phOHq_Rcsv0um22kRLjJ4IfatjKi3X9axmEAM56257qOfps0fLJQ-L9-4DJBNpY*",
               "resourceID":"7a3eb4ce-b1ec-4acb-bb5c-45e79df2830e",
               "failureReason":null,
               "passwordExpirationTime":0,
               "continuation":false,
               "continuationContext":null,
               "continuationData":null
            }
            """;

    static final String jsonResponseHealth = "{\"checks\":[{\"name\":\"Initialization\",\"state\":\"UP\"},{\"name\":\"service.system.monitor\",\"state\":\"UP\"},{\"name\":\"service.system.LDAP\",\"state\":\"UP\"},{\"name\":\"service.system.Repository\",\"state\":\"UP\"},{\"name\":\"service.system.CAP\",\"state\":\"UP\"},{\"name\":\"service.system.Single Sign-On\",\"state\":\"UP\"},{\"name\":\"service.system.Event Handling\",\"state\":\"UP\"},{\"name\":\"service.system.Platform\",\"state\":\"UP\"},{\"name\":\"service.system.Business Process Management\",\"state\":\"DOWN\"},{\"name\":\"service.system.XForms\",\"state\":\"UP\"},{\"name\":\"service.system.Collaborative Workspace\",\"state\":\"UP\"},{\"name\":\"service.system.Notification\",\"state\":\"UP\"},{\"name\":\"service.system.Security Administration\",\"state\":\"UP\"},{\"name\":\"service.system.Logging\",\"state\":\"UP\"},{\"name\":\"service.system.Data Transformation\",\"state\":\"UP\"}],\"status\":\"DOWN\"}";
    static final String soapRequestSamlToken = """
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                <SOAP-ENV:Header>
                    <wsse:Security>
                        <wsse:UsernameToken>
                            <wsse:Username>sysadmin</wsse:Username>
                            <wsse:Password>admin</wsse:Password>
                        </wsse:UsernameToken>
                    </wsse:Security>
                </SOAP-ENV:Header>
                <SOAP-ENV:Body>
                    <samlp:Request MajorVersion="1" MinorVersion="1">
                        <samlp:AuthenticationQuery>
                            <saml:Subject>
                                <saml:NameIdentifier Format="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified">sysadmin</saml:NameIdentifier>
                            </saml:Subject>
                        </samlp:AuthenticationQuery>
                    </samlp:Request>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            """;
}
