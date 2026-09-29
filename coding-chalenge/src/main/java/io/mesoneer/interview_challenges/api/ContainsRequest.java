package io.mesoneer.interview_challenges.api;

import io.swagger.v3.oas.annotations.media.Schema;

public record ContainsRequest(
    @Schema(description = "Type of the range bounds and of the value", example = "INTEGER")
    ValueType type,
    @Schema(description = "Range notation as produced by Range#toString()", example = "[1, 5)")
    String range,
    @Schema(description = "Value to check against the range", example = "3")
    String value) {
}
