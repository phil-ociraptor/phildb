package queryexecutors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

public class NLJoin implements Node {

  private Node left;
  private Resettable right;
  private Optional<Map> currentLeft;
  private Optional<Map> currentRight;
  private BiPredicate predicate;

  public NLJoin(BiPredicate predicate, Node left, Resettable right) {
    this.left = left;
    this.right = right;
    this.currentLeft = left.next();
    this.currentRight = right.next();
    this.predicate = predicate;
  }

  @Override
  public Optional<Map> next() {
    Optional<Map> result = Optional.empty();
    while (!result.isPresent() && currentLeft.isPresent()) {
      result =
          currentLeft.flatMap(
              leftTuple ->
                  currentRight.map(
                      rightTuple ->
                          predicate.test(leftTuple, rightTuple)
                              ? combine(leftTuple, rightTuple)
                              : null));
      advance();
    }
    return result;
  }

  private void advance() {
    currentRight = right.next();
    if (!currentRight.isPresent()) {
      right.reset();
      currentRight = right.next();
      currentLeft = left.next();
    }
  }

  private Map<String, String> combine(Map<String, String> map1, Map<String, String> map2) {
    Map<String, String> combined = new HashMap<>();
    combined.putAll(map1);
    combined.putAll(map2);
    return combined;
  }
}
