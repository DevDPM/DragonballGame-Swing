package nl.pokemon.mapGenerator.config;

import nl.pokemon.mapGenerator.model.SQMs.DevTool_VoidTile;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
import org.dpmFramework.annotation.Configurations;
import org.dpmFramework.annotation.Enable;

@Configurations
public class MapGeneratorConfig {

    @Enable(name = "fullMap")
    MG_ViewMap fullMap;

    @Enable(name = "clientViewMap")
    MG_ViewMap clientViewMap;

    @Enable
    DevTool_VoidTile voidSQM;

}
