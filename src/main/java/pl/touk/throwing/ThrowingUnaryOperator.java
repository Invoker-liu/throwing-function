package pl.touk.throwing;

import java.util.Objects;
import java.util.function.UnaryOperator;

import pl.touk.throwing.exception.WrappedException;

/**
 * Represents an operation on a single operand that produces a result of the
 * same type as its operand.  This is a specialization of {@code Function} for
 * the case where the operand and result are of the same type.
 * Function may throw a checked exception.
 *
 * @param <T> the type of the operand and result of the operator
 * @param <E> the type of the thrown checked exception
 *
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingUnaryOperator<T, E extends Throwable> extends ThrowingFunction<T, T, E> {

    static <T, E extends Exception> UnaryOperator<T> unchecked(ThrowingUnaryOperator<T, E> operator) {
        Objects.requireNonNull(operator);

        return operator.unchecked();
    }

    /**
     * Returns a new UnaryOperator instance which wraps thrown checked exception instance into a RuntimeException
     */
    default UnaryOperator<T> unchecked() {
        return t -> {
            try {
                return apply(t);
            } catch (final Throwable e) {
                throw new WrappedException(e, e.getClass());
            }
        };
    }
}
