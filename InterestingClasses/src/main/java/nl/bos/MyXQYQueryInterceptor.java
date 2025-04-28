package nl.bos;

import com.cordys.xqy.interceptor.IXQYQueryInterceptor;

public class MyXQYQueryInterceptor implements IXQYQueryInterceptor {
    @Override
    public void setDatabaseServerType(String databaseType) {

    }

    @Override
    public String changeBeforePrepare(String queryText, int numRows, int parametersNode) {
        return "";
    }

    @Override
    public int changeBeforePrepare(int requestNode) {
        return 0;
    }

    @Override
    public int changeAfterPrepare(int requestNode) {
        return 0;
    }

    @Override
    public int changeBeforeExecute(int requestNode) {
        return 0;
    }

    @Override
    public int changeAfterExecute(int requestNode) {
        return 0;
    }
}
