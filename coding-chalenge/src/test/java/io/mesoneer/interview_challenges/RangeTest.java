package io.mesoneer.interview_challenges;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.function.Function;
import static org.assertj.core.api.Assertions.*;

public class RangeTest {

  @Test
  public void should_create_range() {
    Range<Integer> range = Range.closed(5, 50);
    assertThat(range.lowerbound()).isEqualTo(5);
    assertThat(range.upperbound()).isEqualTo(50);
  }

  @Test
  public void should_throw_error__when_create_with_lowerbound_bigger_than_upperbound() {
    assertThatThrownBy(() -> Range.closed(500, 1)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Range.open(500, 1)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Range.openClosed(500, 1)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Range.closedOpen(500, 1)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void closed_range_should_contain_both_bounds_and_all_elements_in_between() {
    Range<Integer> closedRange = Range.closed(5, 50);

    assertThat(closedRange.contains(Integer.MIN_VALUE)).isEqualTo(false);
    assertThat(closedRange.contains(4)).isEqualTo(false);

    assertThat(closedRange.contains(5)).isEqualTo(true);

    assertThat(closedRange.contains(42)).isEqualTo(true);

    assertThat(closedRange.contains(50)).isEqualTo(true);

    assertThat(closedRange.contains(10000)).isEqualTo(false);
    assertThat(closedRange.contains(Integer.MAX_VALUE)).isEqualTo(false);
  }

  @Test
  public void range_should_be_state_independent() {
    Range<Integer> range1 = Range.closed(5, 10);
    Range<Integer> range2 = Range.closed(11, 20);

    assertThat(range1.contains(10)).isEqualTo(true);
    assertThat(range2.contains(10)).isEqualTo(false);
  }

  @Test
  public void should_contain_values_from_13_to_100_inclusive() {
    Range<Integer> range = Range.closed(13, 100);
    assertThat(range.contains(5)).isFalse();
    assertThat(range.contains(13)).isTrue();
    assertThat(range.contains(22)).isTrue();
    assertThat(range.contains(100)).isTrue();
    assertThat(range.contains(101)).isFalse();
  }

  @Test
  public void should_allow_lowerbound_equal_to_upperbound() {
    Range<Integer> range = Range.closed(5, 5);

    assertThat(range.lowerbound()).isEqualTo(5);
    assertThat(range.upperbound()).isEqualTo(5);
    assertThat(range.contains(4)).isFalse();
    assertThat(range.contains(5)).isTrue();
    assertThat(range.contains(6)).isFalse();
  }

  @Test
  public void should_throw_error__when_lowerbound_is_just_one_bigger_than_upperbound() {
    assertThatThrownBy(() -> Range.closed(5, 4))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void closed_range_should_not_contain_values_just_outside_bounds() {
    Range<Integer> range = Range.closed(5, 50);

    assertThat(range.contains(4)).isFalse();
    assertThat(range.contains(51)).isFalse();
  }

  @Test
  public void should_support_negative_bounds() {
    Range<Integer> range = Range.closed(-10, -1);

    assertThat(range.contains(-11)).isFalse();
    assertThat(range.contains(-10)).isTrue();
    assertThat(range.contains(-5)).isTrue();
    assertThat(range.contains(-1)).isTrue();
    assertThat(range.contains(0)).isFalse();
  }

  @Test
  public void should_support_range_spanning_zero() {
    Range<Integer> range = Range.closed(-5, 5);

    assertThat(range.contains(-6)).isFalse();
    assertThat(range.contains(-5)).isTrue();
    assertThat(range.contains(0)).isTrue();
    assertThat(range.contains(5)).isTrue();
    assertThat(range.contains(6)).isFalse();
  }

  @Test
  public void should_support_full_int_range() {
    Range<Integer> range = Range.closed(Integer.MIN_VALUE, Integer.MAX_VALUE);

    assertThat(range.contains(Integer.MIN_VALUE)).isTrue();
    assertThat(range.contains(0)).isTrue();
    assertThat(range.contains(Integer.MAX_VALUE)).isTrue();
  }

  @Test
  public void open_range_should_exclude_both_bounds() {
    Range<Integer> range = Range.open(5, 7);

    assertThat(range.contains(4)).isFalse();
    assertThat(range.contains(5)).isFalse();
    assertThat(range.contains(6)).isTrue();
    assertThat(range.contains(7)).isFalse();
    assertThat(range.contains(8)).isFalse();
  }

  @Test
  public void open_closed_range_should_exclude_lowerbound_and_include_upperbound() {
    Range<Integer> range = Range.openClosed(5, 7);

    assertThat(range.contains(5)).isFalse();
    assertThat(range.contains(6)).isTrue();
    assertThat(range.contains(7)).isTrue();
    assertThat(range.contains(8)).isFalse();
  }

  @Test
  public void closed_open_range_should_include_lowerbound_and_exclude_upperbound() {
    Range<Integer> range = Range.closedOpen(5, 7);

    assertThat(range.contains(4)).isFalse();
    assertThat(range.contains(5)).isTrue();
    assertThat(range.contains(6)).isTrue();
    assertThat(range.contains(7)).isFalse();
  }

  @Test
  public void every_type_of_range_should_expose_its_lowerbound_and_upperbound() {
    List<Range<Integer>> ranges = List.of(
        Range.closed(5, 7),
        Range.open(5, 7),
        Range.openClosed(5, 7),
        Range.closedOpen(5, 7)
    );

    for (Range<Integer> range : ranges) {
      assertThat(range.lowerbound()).isEqualTo(5);
      assertThat(range.upperbound()).isEqualTo(7);
    }
  }

  @Test
  public void should_support_any_comparable_type() {
    Range<String> text = Range.open("abc", "xyz");
    assertThat(text.contains("abc")).isFalse();
    assertThat(text.contains("mno")).isTrue();
    assertThat(text.contains("xyz")).isFalse();

    Range<BigDecimal> decimals = Range.closed(new BigDecimal("1.5"), new BigDecimal("2.5"));
    assertThat(decimals.contains(new BigDecimal("1.50"))).isTrue();
    assertThat(decimals.contains(new BigDecimal("2.51"))).isFalse();

    Range<LocalDate> dates = Range.closedOpen(LocalDate.of(2016, Month.SEPTEMBER, 11), LocalDate.of(2017, Month.JUNE, 30));
    assertThat(dates.contains(LocalDate.of(2016, Month.SEPTEMBER, 11))).isTrue();
    assertThat(dates.contains(LocalDate.of(2017, Month.JANUARY, 1))).isTrue();
    assertThat(dates.contains(LocalDate.of(2017, Month.JUNE, 30))).isFalse();
  }

  @Test
  public void should_expose_bounds_of_non_numeric_types() {
    LocalDate start = LocalDate.of(2016, Month.SEPTEMBER, 11);
    LocalDate end = LocalDate.of(2017, Month.JUNE, 30);
    Range<LocalDate> dates = Range.closed(start, end);
    Range<String> text = Range.open("abc", "xyz");

    assertThat(dates.lowerbound()).isEqualTo(start);
    assertThat(dates.upperbound()).isEqualTo(end);
    assertThat(text.lowerbound()).isEqualTo("abc");
    assertThat(text.upperbound()).isEqualTo("xyz");
  }

  @Test
  public void should_throw_error__when_lowerbound_is_bigger_than_upperbound_for_non_numeric_types() {
    assertThatThrownBy(() -> Range.closed("z", "a")).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Range.open(LocalDate.of(2017, Month.JUNE, 30), LocalDate.of(2016, Month.SEPTEMBER, 11)))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Range.openClosed(new BigDecimal("2.5"), new BigDecimal("1.5")))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void should_throw_error__when_a_bound_is_null() {
    assertThatThrownBy(() -> Range.closed(null, 5))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("Lower bound must not be null.");
    assertThatThrownBy(() -> Range.open(5, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("Upper bound must not be null.");
  }

  @Test
  public void less_than_range_should_exclude_upperbound_and_have_no_lowerbound() {
    Range<Integer> lessThanFive = Range.lessThan(5);

    assertThat(lessThanFive.contains(5)).isFalse();
    assertThat(lessThanFive.contains(6)).isFalse();
    assertThat(lessThanFive.contains(4)).isTrue();
    assertThat(lessThanFive.contains(-9000)).isTrue();
    assertThat(lessThanFive.contains(Integer.MIN_VALUE)).isTrue();
  }

  @Test
  public void at_least_range_should_include_lowerbound_and_have_no_upperbound() {
    Range<Integer> atLeastFive = Range.atLeast(5);

    assertThat(atLeastFive.contains(4)).isFalse();
    assertThat(atLeastFive.contains(5)).isTrue();
    assertThat(atLeastFive.contains(6)).isTrue();
    assertThat(atLeastFive.contains(Integer.MAX_VALUE)).isTrue();
  }

  @Test
  public void at_most_range_should_include_upperbound_and_have_no_lowerbound() {
    Range<Integer> atMostFive = Range.atMost(5);

    assertThat(atMostFive.contains(5)).isTrue();
    assertThat(atMostFive.contains(-234234)).isTrue();
    assertThat(atMostFive.contains(Integer.MIN_VALUE)).isTrue();
    assertThat(atMostFive.contains(6)).isFalse();
  }

  @Test
  public void greater_than_range_should_exclude_lowerbound_and_have_no_upperbound() {
    LocalDate epoch = LocalDate.of(1900, Month.JANUARY, 1);
    Range<LocalDate> afterEpoch = Range.greaterThan(epoch);

    assertThat(afterEpoch.contains(LocalDate.of(2016, Month.JULY, 28))).isTrue();
    assertThat(afterEpoch.contains(epoch.plusDays(1))).isTrue();
    assertThat(afterEpoch.contains(epoch)).isFalse();
    assertThat(afterEpoch.contains(LocalDate.of(1750, Month.JANUARY, 1))).isFalse();
  }

  @Test
  public void all_range_should_contain_everything_including_null() {
    Range<String> all = Range.all();

    assertThat(all.contains("anything")).isTrue();
    assertThat(all.contains("")).isTrue();
    assertThat(all.contains(null)).isTrue();
  }

  @Test
  public void should_throw_error__when_the_bound_of_an_open_ended_range_is_null() {
    assertThatThrownBy(() -> Range.lessThan(null)).isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Range.atMost(null)).isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Range.atLeast(null)).isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Range.greaterThan(null)).isInstanceOf(NullPointerException.class);
  }

  @Test
  public void open_ended_range_should_have_null_only_on_its_unbounded_side() {
    assertThat(Range.lessThan(5).lowerbound()).isNull();
    assertThat(Range.lessThan(5).upperbound()).isEqualTo(5);

    assertThat(Range.atMost(5).lowerbound()).isNull();
    assertThat(Range.atMost(5).upperbound()).isEqualTo(5);

    assertThat(Range.atLeast(5).lowerbound()).isEqualTo(5);
    assertThat(Range.atLeast(5).upperbound()).isNull();

    assertThat(Range.greaterThan(5).lowerbound()).isEqualTo(5);
    assertThat(Range.greaterThan(5).upperbound()).isNull();
  }

  @Test
  public void all_range_should_have_null_on_both_sides() {
    Range<Integer> all = Range.all();

    assertThat(all.lowerbound()).isNull();
    assertThat(all.upperbound()).isNull();
  }

  @Test
  public void bounded_range_should_never_have_null_bounds() {
    List<Range<Integer>> ranges = List.of(
        Range.closed(5, 7),
        Range.open(5, 7),
        Range.openClosed(5, 7),
        Range.closedOpen(5, 7)
    );

    for (Range<Integer> range : ranges) {
      assertThat(range.lowerbound()).isNotNull();
      assertThat(range.upperbound()).isNotNull();
    }
  }

  @Test
  public void to_string_should_match_readme_examples() {
    Range<Integer> lessThan100 = Range.lessThan(100);
    Range<LocalDate> within2020 = Range.closed(
        LocalDate.of(2020, Month.JANUARY, 1),
        LocalDate.of(2020, Month.DECEMBER, 31));

    assertThat(lessThan100.toString()).isEqualTo("[Infinity, 100)");
    assertThat(within2020.toString()).isEqualTo("[2020-01-01, 2020-12-31]");
  }

  @Test
  public void to_string_should_represent_bounded_range_types() {
    assertThat(Range.closed(5, 7).toString()).isEqualTo("[5, 7]");
    assertThat(Range.open(5, 7).toString()).isEqualTo("(5, 7)");
    assertThat(Range.openClosed(5, 7).toString()).isEqualTo("(5, 7]");
    assertThat(Range.closedOpen(5, 7).toString()).isEqualTo("[5, 7)");
  }

  @Test
  public void to_string_should_represent_unbounded_side_as_infinity_with_square_bracket() {
    assertThat(Range.lessThan(5).toString()).isEqualTo("[Infinity, 5)");
    assertThat(Range.atMost(5).toString()).isEqualTo("[Infinity, 5]");
    assertThat(Range.greaterThan(5).toString()).isEqualTo("(5, Infinity]");
    assertThat(Range.atLeast(5).toString()).isEqualTo("[5, Infinity]");
    assertThat(Range.all().toString()).isEqualTo("[Infinity, Infinity]");
  }

  @Test
  public void to_string_should_use_to_string_of_the_bound_type() {
    assertThat(Range.open("abc", "xyz").toString()).isEqualTo("(abc, xyz)");
    assertThat(Range.closed(new BigDecimal("1.50"), new BigDecimal("2.5")).toString()).isEqualTo("[1.50, 2.5]");
    assertThat(Range.closed(-10, -1).toString()).isEqualTo("[-10, -1]");
    assertThat(Range.closed(5, 5).toString()).isEqualTo("[5, 5]");
    assertThat(Range.greaterThan(LocalDate.of(1900, Month.JANUARY, 1)).toString())
        .isEqualTo("(1900-01-01, Infinity]");
  }

  @Test
  public void parse_should_recreate_range_from_readme_example() {
    String rangeString = Range.lessThan(100).toString();

    Range<Integer> lessThan100 = Range.parse(rangeString, Integer::valueOf);

    assertThat(lessThan100.toString()).isEqualTo("[Infinity, 100)");
    assertThat(lessThan100.contains(99)).isTrue();
    assertThat(lessThan100.contains(100)).isFalse();
  }

  @Test
  public void parse_should_round_trip_every_type_of_range() {
    List<Range<Integer>> ranges = List.of(
        Range.closed(5, 7),
        Range.open(5, 7),
        Range.openClosed(5, 7),
        Range.closedOpen(5, 7),
        Range.lessThan(7),
        Range.atMost(7),
        Range.greaterThan(5),
        Range.atLeast(5),
        Range.all()
    );

    for (Range<Integer> original : ranges) {
      Range<Integer> parsed = Range.parse(original.toString(), Integer::valueOf);

      assertThat(parsed.toString()).isEqualTo(original.toString());
      for (int value = 3; value <= 9; value++) {
        assertThat(parsed.contains(value)).as("%s contains %s", original, value)
            .isEqualTo(original.contains(value));
      }
    }
  }

  @Test
  public void parse_should_keep_the_bounds_of_the_original_range() {
    Range<Integer> parsed = Range.parse("(5, 7]", Integer::valueOf);

    assertThat(parsed.lowerbound()).isEqualTo(5);
    assertThat(parsed.upperbound()).isEqualTo(7);
  }

  @Test
  public void parse_should_leave_unbounded_side_without_a_bound() {
    Range<Integer> atLeastFive = Range.parse("[5, Infinity]", Integer::valueOf);
    Range<Integer> all = Range.parse("[Infinity, Infinity]", Integer::valueOf);

    assertThat(atLeastFive.lowerbound()).isEqualTo(5);
    assertThat(atLeastFive.upperbound()).isNull();
    assertThat(all.lowerbound()).isNull();
    assertThat(all.upperbound()).isNull();
  }

  @Test
  public void parse_should_support_other_comparable_types() {
    Range<String> text = Range.parse("(abc, xyz)", Function.identity());
    Range<LocalDate> dates = Range.parse("[2020-01-01, 2020-12-31]", LocalDate::parse);
    Range<BigDecimal> decimals = Range.parse("[1.50, Infinity]", BigDecimal::new);

    assertThat(text.contains("mno")).isTrue();
    assertThat(text.contains("abc")).isFalse();
    assertThat(dates.toString()).isEqualTo("[2020-01-01, 2020-12-31]");
    assertThat(dates.contains(LocalDate.of(2020, Month.JUNE, 15))).isTrue();
    assertThat(decimals.toString()).isEqualTo("[1.50, Infinity]");
    assertThat(decimals.contains(new BigDecimal("1.5"))).isTrue();
  }

  @Test
  public void parse_should_throw_error__when_range_string_is_null() {
    assertThatThrownBy(() -> Range.parse(null, Integer::valueOf))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void parse_should_throw_error__when_range_string_has_invalid_format() {
    List<String> invalidInputs = List.of("", "[", "5, 7", "[5 7]", "[5, 7", "{5, 7}", "[5, 7, 9]",
        "[5, 7, ]", "[5, 7, , ]", "[, 5, 7]", "[5, ]", "[, 7]");

    for (String input : invalidInputs) {
      assertThatThrownBy(() -> Range.parse(input, Integer::valueOf))
          .as("input: '%s'", input)
          .isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Test
  public void parse_should_throw_error__when_lowerbound_is_bigger_than_upperbound() {
    assertThatThrownBy(() -> Range.parse("[7, 5]", Integer::valueOf))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void parse_should_throw_error__when_a_bound_cannot_be_parsed() {
    assertThatThrownBy(() -> Range.parse("[a, 5]", Integer::valueOf))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Range.parse("[2020-13-01, 2020-12-31]", LocalDate::parse))
        .isInstanceOf(IllegalArgumentException.class);
  }

}
