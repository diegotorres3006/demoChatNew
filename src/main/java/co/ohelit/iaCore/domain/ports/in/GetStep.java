package co.ohelit.iaCore.domain.ports.in;

import java.util.List;
import java.util.Map;

public interface GetStep {
    Map<String, Object> findStepByNumber(List<Map<String, Object>> yamlList, int targetStepNumber);
}
