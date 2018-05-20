package queryexecutors;

import java.util.Map;
import java.util.Optional;

public interface Node {
    Optional<Map> next();
}
