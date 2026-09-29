package io.mesoneer.interview_challenges;

import java.util.Objects;
import java.util.function.Function;

public class Range<T extends Comparable<? super T>> {

  private static final String INFINITY = "Infinity";
  private static final String SEPARATOR = ", ";

  private enum BoundType {
    OPEN, CLOSED, UNBOUNDED
  }

  private final T lowerbound;
  private final T upperbound;
  private final BoundType lowerType;
  private final BoundType upperType;

  private Range(T lowerbound, BoundType lowerType, T upperbound, BoundType upperType) {
    if (lowerType != BoundType.UNBOUNDED) {
      Objects.requireNonNull(lowerbound, "Lower bound must not be null.");
    }
    if (upperType != BoundType.UNBOUNDED) {
      Objects.requireNonNull(upperbound, "Upper bound must not be null.");
    }
    if (lowerType != BoundType.UNBOUNDED && upperType != BoundType.UNBOUNDED
        && lowerbound.compareTo(upperbound) > 0) {
      throw new IllegalArgumentException("Lower bound must be less than or equal to upper bound.");
    }
    this.lowerbound = lowerbound;
    this.upperbound = upperbound;
    this.lowerType = lowerType;
    this.upperType = upperType;
  }

  public static <T extends Comparable<? super T>> Range<T> open(T lowerbound, T upperbound) {
    return new Range<T>(lowerbound, BoundType.OPEN, upperbound, BoundType.OPEN);
  }

  public static <T extends Comparable<? super T>> Range<T> closed(T lowerbound, T upperbound) {
    return new Range<T>(lowerbound, BoundType.CLOSED, upperbound, BoundType.CLOSED);
  }

  public static <T extends Comparable<? super T>> Range<T> openClosed(T lowerbound, T upperbound) {
    return new Range<T>(lowerbound, BoundType.OPEN, upperbound, BoundType.CLOSED);
  }

  public static <T extends Comparable<? super T>> Range<T> closedOpen(T lowerbound, T upperbound) {
    return new Range<T>(lowerbound, BoundType.CLOSED, upperbound, BoundType.OPEN);
  }

  public static <T extends Comparable<? super T>> Range<T> lessThan(T upperbound) {
    return new Range<T>(null, BoundType.UNBOUNDED, upperbound, BoundType.OPEN);
  }

  public static <T extends Comparable<? super T>> Range<T> atMost(T upperbound) {
    return new Range<T>(null, BoundType.UNBOUNDED, upperbound, BoundType.CLOSED);
  }

  public static <T extends Comparable<? super T>> Range<T> greaterThan(T lowerbound) {
    return new Range<T>(lowerbound, BoundType.OPEN, null, BoundType.UNBOUNDED);
  }

  public static <T extends Comparable<? super T>> Range<T> atLeast(T lowerbound) {
    return new Range<T>(lowerbound, BoundType.CLOSED, null, BoundType.UNBOUNDED);
  }

  public static <T extends Comparable<? super T>> Range<T> all() {
    return new Range<T>(null, BoundType.UNBOUNDED, null, BoundType.UNBOUNDED);
  }

  public static <T extends Comparable<? super T>> Range<T> parse(String rangeString,
      Function<String, T> parser) {
    if (rangeString == null || rangeString.length() < 2) {
      throw invalidFormat(rangeString);
    }
    Objects.requireNonNull(parser, "Parser must not be null.");

    char openingBracket = rangeString.charAt(0);
    char closingBracket = rangeString.charAt(rangeString.length() - 1);
    if ((openingBracket != '[' && openingBracket != '(')
        || (closingBracket != ']' && closingBracket != ')')) {
      throw invalidFormat(rangeString);
    }

    String[] bounds = rangeString.substring(1, rangeString.length() - 1).split(SEPARATOR, -1);
    if (bounds.length != 2) {
      throw invalidFormat(rangeString);
    }

    BoundType lowerType = INFINITY.equals(bounds[0]) ? BoundType.UNBOUNDED
        : openingBracket == '(' ? BoundType.OPEN : BoundType.CLOSED;
    BoundType upperType = INFINITY.equals(bounds[1]) ? BoundType.UNBOUNDED
        : closingBracket == ')' ? BoundType.OPEN : BoundType.CLOSED;

    T lowerbound = lowerType == BoundType.UNBOUNDED ? null : parseBound(bounds[0], parser);
    T upperbound = upperType == BoundType.UNBOUNDED ? null : parseBound(bounds[1], parser);

    return new Range<>(lowerbound, lowerType, upperbound, upperType);
  }

  private static IllegalArgumentException invalidFormat(String rangeString) {
    return new IllegalArgumentException("Invalid range format: " + rangeString);
  }

  private static <T> T parseBound(String text, Function<String, T> parser) {
    try {
      return parser.apply(text);
    } catch (RuntimeException e) {
      throw new IllegalArgumentException("Invalid bound: " + text, e);
    }
  }

  public boolean contains(T value) {
    return lowerBoundCheck(value) && upperBoundCheck(value);
  }

  private boolean lowerBoundCheck(T value) {
    switch (lowerType) {
      case OPEN:
        return value.compareTo(lowerbound) > 0;
      case CLOSED:
        return value.compareTo(lowerbound) >= 0;
      case UNBOUNDED:
        return true;
      default:
        throw new IllegalStateException("Unsupported bound type: " + lowerType);
    }
  }

  private boolean upperBoundCheck(T value) {
    switch (upperType) {
      case OPEN:
        return value.compareTo(upperbound) < 0;
      case CLOSED:
        return value.compareTo(upperbound) <= 0;
      case UNBOUNDED:
        return true;
      default:
        throw new IllegalStateException("Unsupported bound type: " + upperType);
    }
  }

  public T lowerbound() {
    return lowerbound;
  }

  public T upperbound() {
    return upperbound;
  }

  @Override
  public String toString() {
    return lowerBracket() + boundText(lowerbound, lowerType) + SEPARATOR
        + boundText(upperbound, upperType) + upperBracket();
  }

  private String boundText(T bound, BoundType type) {
    return type == BoundType.UNBOUNDED ? INFINITY : bound.toString();
  }

  private String lowerBracket() {
    return lowerType == BoundType.OPEN ? "(" : "[";
  }

  private String upperBracket() {
    return upperType == BoundType.OPEN ? ")" : "]";
  }

}
