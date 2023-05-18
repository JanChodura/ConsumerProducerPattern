package org.chodura.dao;

import org.chodura.core.DatabaseConnector;
import org.chodura.util.AppConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractDao {
    protected final DatabaseConnector connector;

    protected final ModelClassEnum modelClassEnum;
    protected String modelClassPrefix;
    protected final Map<String, String> sqlQueryTemplates;

    protected AbstractDao(ModelClassEnum modelClassEnum, String sqlQueriesTemplateFile) {
        this.modelClassEnum = modelClassEnum;

        connector = DatabaseConnector.getInstance();
        sqlQueryTemplates = new HashMap<>();
        Properties sqlQueryTemplatesAsProperties = AppConfiguration.read(sqlQueriesTemplateFile);
        loadSqlQueryTemplatesForEntity(sqlQueryTemplatesAsProperties);
    }

    private void loadSqlQueryTemplatesForEntity(Properties properties) {
        properties.forEach((k, v) -> {
            String key = (String) k;
            String queryTemplate = (String) v;
            int queryNameStart = key.indexOf(modelClassEnum.name().toLowerCase() + ".");
            if (queryNameStart > -1) {
                modelClassPrefix = key.substring(0, modelClassEnum.name().length() + 1);
                sqlQueryTemplates.put(key.substring(queryNameStart), queryTemplate);
            }
        });
    }

}
