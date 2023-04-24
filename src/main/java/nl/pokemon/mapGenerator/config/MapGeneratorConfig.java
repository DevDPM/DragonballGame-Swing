package nl.pokemon.mapGenerator.config;

import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
import org.dpmFramework.annotation.Configurations;
import org.dpmFramework.annotation.Enable;

@Configurations
public class MapGeneratorConfig {

    @Enable
    MG_ViewMap viewMap;

}
