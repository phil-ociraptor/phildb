package queryexecutors;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class NLJoin implements Node {

  private Resettable left;
  private Resettable right;
  private Optional<List<ResultElement>> currentLeft;
  private Optional<List<ResultElement>> currentRight;
  private BiPredicate predicate;

  public NLJoin(BiPredicate predicate, Resettable left, Resettable right) {
    this.left = left;
    this.right = right;
    this.currentLeft = left.next();
    this.currentRight = right.next();
    this.predicate = predicate;
  }

  @Override
  public Optional<List<ResultElement>> next() {
    if (!currentRight.isPresent()) {
      right.reset();
      currentRight = right.next();
      currentLeft = left.next();
    }

    if (currentLeft.isPresent() && currentRight.isPresent()) {
      Optional<List<ResultElement>> result =
          currentLeft.flatMap(
              leftResult ->
                  currentRight.map(
                      rightResult -> {
                        if (predicate.test(leftResult, rightResult)) {
                          leftResult.addAll(rightResult);
                          return leftResult;
                        } else {
                          return null;
                        }
                      }));
      currentRight = right.next();
      return result;
    }

    return Optional.empty();
  }
}
