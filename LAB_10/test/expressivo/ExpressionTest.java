package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

public class ExpressionTest {

    @Test
    public void testNumber() {
        Expression num = new Number(5.0);
        assertEquals("5.0", num.toString());
        assertEquals(new Number(5.0), num);
        assertEquals(new Number(5), num);
    }

    @Test
    public void testVariable() {
        Expression var = new Variable("x");
        assertEquals("x", var.toString());
        assertEquals(new Variable("x"), var);
    }

    @Test
    public void testSum() {
        Expression sum = new Sum(new Number(1), new Variable("x"));
        assertEquals("(1.0 + x)", sum.toString());
        assertEquals(new Sum(new Number(1), new Variable("x")), sum);
    }

    @Test
    public void testProduct() {
        Expression product = new Product(new Number(3), new Variable("y"));
        assertEquals("(3.0 * y)", product.toString());
        assertEquals(new Product(new Number(3), new Variable("y")), product);
    }

    @Test
    public void testComplexExpression() {
        Expression expr = new Sum(
            new Product(new Number(2), new Variable("x")),
            new Number(3)
        );
        assertEquals("((2.0 * x) + 3.0)", expr.toString());
    }
}
