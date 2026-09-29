package io.mesoneer.interview_challenges.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/range")
public class RangeController {

  private final RangeService rangeService;

  public RangeController(RangeService rangeService) {
    this.rangeService = rangeService;
  }

  @Operation(summary = "Check whether a range contains a value")
  @PostMapping("/contains")
  public ContainsResponse contains(@RequestBody ContainsRequest request) {
    return new ContainsResponse(rangeService.contains(request.type(), request.range(), request.value()));
  }

}
