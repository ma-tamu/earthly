package jp.co.project.planets.earthly.webapp.view.dialect;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class CustomDialect extends AbstractDialect implements IExpressionObjectDialect {

    private static final String DIALECT_NAME = "";

    public CustomDialect() {
        super(DIALECT_NAME);
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return null;
    }
}
