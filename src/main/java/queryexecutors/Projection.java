package queryexecutors;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Projection implements Node {

  private Node child;
  private HashSet<String> fieldsSet;

  public Projection(List<String> fields, Node child) {
    this.fieldsSet = Sets.newHashSet(fields);
    this.child = child;
  }

  @Override
  public Optional<Map> next() {
    Optional<Map> current = child.next();
    return current.map(tuple -> Maps.filterKeys(tuple, fieldsSet::contains));
  }
}
