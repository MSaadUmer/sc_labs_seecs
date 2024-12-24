package expressivo;

public class Commands {
    
    /**
     * Differentiate an expression with respect to a variable.
     * @param expression the expression to differentiate
     * @param variable the variable to differentiate by, a case-sensitive nonempty string of letters.
     * @return expression's derivative with respect to variable. Must be a valid expression equal
     *         to the derivative, but doesn't need to be in simplest or canonical form.
     * @throws IllegalArgumentException if the expression or variable is invalid
     */
    public static String differentiate(String expression, String variable) {
        try {
            // Parse the input expression
            Expression expr = Expression.parse(expression);
            
            // Differentiate the parsed expression
            Expression derivative = expr.differentiate(variable);
            
            // Convert the derivative back to a string
            return derivative.toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid expression or variable: " + e.getMessage(), e);
        }
    }
    public static String simplify(String expression, Map<String, Double> environment) {
        try {
            Expression expr = Expression.parse(expression);
            Expression simplifiedExpr = expr.simplify(environment);
            return simplifiedExpr.toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid expression: " + e.getMessage(), e);
        }
    }
}
}

