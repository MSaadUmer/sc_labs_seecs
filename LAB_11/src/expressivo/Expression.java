package expressivo;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionParser;

/**
 * An immutable data type representing a polynomial expression.
 */
public class Expression {

    private final String representation;

    private Expression(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Expression)) return false;
        return this.representation.equals(((Expression) obj).representation);
    }

    @Override
    public int hashCode() {
        return representation.hashCode();
    }

    /**
     * Parse an expression string into an Expression object.
     *
     * @param input expression to parse.
     * @return an Expression object representing the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        try {
            ANTLRInputStream inputStream = new ANTLRInputStream(input);
            ExpressionLexer lexer = new ExpressionLexer(inputStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ExpressionParser parser = new ExpressionParser(tokens);
            ParseTree tree = parser.root();
            return new Expression(tree.getText());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse expression.", e);
        }
    }

    /**
     * Compute the derivative of this expression with respect to the given variable.
     *
     * @param variable the variable to differentiate with respect to.
     * @return a new Expression representing the derivative.
     */
    public Expression differentiate(String variable) {
        // Recursive differentiation logic
        return differentiateRecursive(this.representation, variable);
    }

    private Expression differentiateRecursive(String expr, String variable) {
        // Base cases
        if (expr.equals(variable)) {
            return new Expression("1");
        }
        if (expr.matches("\\d+")) { // Constant
            return new Expression("0");
        }

        // Handle operations: "+", "*"
        if (expr.contains("+")) {
            String[] terms = expr.split("\\+");
            String differentiatedTerms = "";
            for (String term : terms) {
                Expression differentiatedTerm = differentiateRecursive(term.trim(), variable);
                differentiatedTerms += (differentiatedTerms.isEmpty() ? "" : " + ") + differentiatedTerm;
            }
            return new Expression(differentiatedTerms);
        } else if (expr.contains("*")) {
            String[] factors = expr.split("\\*");
            if (factors.length == 2) {
                String u = factors[0].trim();
                String v = factors[1].trim();
                Expression du = differentiateRecursive(u, variable);
                Expression dv = differentiateRecursive(v, variable);
                return new Expression(
                        String.format("(%s * %s) + (%s * %s)", du, v, u, dv)
                );
            }
        }

        // If none of the above cases match, return 0 as a fallback
        return new Expression("0");
    }
    public Expression simplify(Map<String, Double> environment) {
        return simplifyRecursive(this.representation, environment);
    }

    private Expression simplifyRecursive(String expr, Map<String, Double> environment) {
        // Base case: a variable
        if (expr.matches("[a-zA-Z]+")) {
            if (environment.containsKey(expr)) {
                return new Expression(Double.toString(environment.get(expr)));
            } else {
                return new Expression(expr);
            }
        }

        // Base case: a constant number
        if (expr.matches("\\d+(\\.\\d+)?")) {
            return new Expression(expr);
        }

        // Handle operations: "+", "*"
        if (expr.contains("+")) {
            String[] terms = expr.split("\\+");
            double sum = 0.0;
            String simplifiedTerms = "";
            boolean isConstant = true;
            for (String term : terms) {
                Expression simplifiedTerm = simplifyRecursive(term.trim(), environment);
                if (simplifiedTerm.representation.matches("\\d+(\\.\\d+)?")) {
                    sum += Double.parseDouble(simplifiedTerm.representation);
                } else {
                    isConstant = false;
                    simplifiedTerms += (simplifiedTerms.isEmpty() ? "" : " + ") + simplifiedTerm;
                }
            }
            if (isConstant) {
                return new Expression(Double.toString(sum));
            } else {
                return new Expression((sum > 0 ? sum + " + " : "") + simplifiedTerms);
            }
        } else if (expr.contains("*")) {
            String[] factors = expr.split("\\*");
            double product = 1.0;
            String simplifiedFactors = "";
            boolean isConstant = true;
            for (String factor : factors) {
                Expression simplifiedFactor = simplifyRecursive(factor.trim(), environment);
                if (simplifiedFactor.representation.matches("\\d+(\\.\\d+)?")) {
                    product *= Double.parseDouble(simplifiedFactor.representation);
                } else {
                    isConstant = false;
                    simplifiedFactors += (simplifiedFactors.isEmpty() ? "" : " * ") + simplifiedFactor;
                }
            }
            if (isConstant) {
                return new Expression(Double.toString(product));
            } else {
                return new Expression((product != 1.0 ? product + " * " : "") + simplifiedFactors);
            }
        }

        return new Expression(expr);
    }
}


