package nl.pokemon.mapGenerator.config;

import nl.pokemon.mapGenerator.model.BigFiller;
import nl.pokemon.mapGenerator.model.Filler;
import nl.pokemon.mapGenerator.model.Selection;
import org.dpmFramework.annotation.Configurations;
import org.dpmFramework.annotation.Enable;

@Configurations
public class MapGeneratorConfig {

    @Enable(name = "selectedItem")
    Selection selection;

    @Enable(name = "incrementedFill")
    Filler filler;

    @Enable(name = "incrementedFill")
    BigFiller bigFiller;
}
