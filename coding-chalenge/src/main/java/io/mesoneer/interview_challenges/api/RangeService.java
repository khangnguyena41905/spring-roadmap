package io.mesoneer.interview_challenges.api;

import io.mesoneer.interview_challenges.Range;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class RangeService {

  public boolean contains(ValueType type, String range, String value) {
    if (type == null || range == null || value == null) {
      throw new IllegalArgumentException("Fields 'type', 'range' and 'value' are required.");
    }
    switch (type) {
      case INTEGER:
        return contains(range, value, Integer::valueOf);
      case DECIMAL:
        return contains(range, value, BigDecimal::new);
      case DATE:
        return contains(range, value, LocalDate::parse);
      case STRING:
        return contains(range, value, Function.identity());
      default:
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
  }

  private <T extends Comparable<? super T>> boolean contains(String range, String value,
      Function<String, T> parser) {
    Range<T> parsedRange = Range.parse(range, parser);
    return parsedRange.contains(parseValue(value, parser));
  }

  private <T> T parseValue(String value, Function<String, T> parser) {
    try {
      return parser.apply(value);
    } catch (RuntimeException e) {
      throw new IllegalArgumentException("Invalid value: " + value, e);
    }
  }

}
