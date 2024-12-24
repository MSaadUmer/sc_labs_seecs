package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

public class ExpressionTest {

    @Test
    public void testDifferentiateSimpleExpressions() {
        Expression expr = Expression.parse("x");
        assertEquals(Expression.parse("1"), expr.differentiate("x"));

        Expression constantExpr = Expression.parse("5");
        assertEquals(Expression.parse("0"), constantExpr.differentiate("x"));
    }

    @Test
    public void testDifferentiateComplexExpressions() {
        Expression expr = Expression.parse("x * x");
        assertEquals(Expression.parse("(1 * x) + (x * 1)"), expr.differentiate("x"));

        Expression sumExpr = Expression.parse("x + x * x");
        assertEquals(Expression.parse("1 + (1 * x) + (x * 1)"), sumExpr.differentiate("x"));
    }

    @Test
    public void testDifferentiateInvalidVariable() {
        Expression expr = Expression.parse("x * x");
        assertEquals(Expression.parse("0"), expr.differentiate("y"));
    }
}
