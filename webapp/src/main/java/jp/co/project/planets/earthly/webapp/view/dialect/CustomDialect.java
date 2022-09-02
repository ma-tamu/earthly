package jp.co.project.planets.earthly.webapp.view.dialect;

import jp.co.project.planets.earthly.webapp.view.factory.CustomExpressionObjectFactory;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * custom dialect
 */
public class CustomDialect implements IExpressionObjectDialect {

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new CustomExpressionObjectFactory();
    }

    @Override
    public String getName() {
        return "earthly";
    }
}
