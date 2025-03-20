package co.ohelit.iaCore.domain.ports.in;

import co.ohelit.iaCore.domain.models.Recipe;

import java.util.List;

public interface GetRecipes {
    List<Recipe> getRecipes(String filterName, String filterValue, int pageSize);
}
