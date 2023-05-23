package nl.pokemon.mapGenerator.view;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.bootstrap.FullMap;
import nl.pokemon.game.bootstrap.TilesetImageContainer;
import nl.pokemon.mapGenerator.model.SQMs.DevTool_BaseTile;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ViewPanel extends JPanel {

    @Inject(name = "fullMap")
    private MG_ViewMap fullMap;

    @Inject(name = "clientViewMap")
    private MG_ViewMap clientViewMap;


    public ViewPanel() {
        this.setLayout(null);
    }

    public void init() {
//        FullMap.bootstrapFullMap();
//        TilesetImageContainer.bootstrap();

        List<Integer> elevations = new ArrayList<>(FullMap.getViewMap().keySet());
        Collections.reverse(elevations);

        for (int elevation : elevations) {
            Map<AreaType, int[][]> layerMap = FullMap.getViewMap().get(elevation);
            List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
            Collections.sort(areaTypes);

            for (AreaType areaType : areaTypes) {
                int[][] areaMap = layerMap.get(areaType);
                for (int y = areaMap[0].length-1; y >= 0; y--) {
                    for (int x = areaMap.length-1; x >= 0; x--) {
                        DevTool_BaseTile sqm = fullMap.getViewMap().get(elevation).get(areaType).getGridMap()[y][x];
                        sqm.setIndexX(x);
                        sqm.setIndexY(y);
                        sqm.setCoordinateX(x);
                        sqm.setCoordinateY(y);
                        sqm.setAreaType(areaType);
                        sqm.setSqmId(areaMap[y][x]);
                        sqm.setImageIcon(TilesetImageContainer.getSQMByArea(areaType).get(areaMap[y][x]).getImageIcon());
                        sqm.setSqmSizeX(TilesetImageContainer.getSQMByArea(areaType).get(areaMap[y][x]).getSqmSizeX());
                        sqm.setSqmSizeY(TilesetImageContainer.getSQMByArea(areaType).get(areaMap[y][x]).getSqmSizeY());
                        sqm.updateSQM();
                        if (y <= 13 && x <= 18) {
                            DevTool_BaseTile clientViewSQM = clientViewMap.getViewMap().get(elevation).get(areaType).getGridMap()[y][x];
                            clientViewSQM.setIndexX(x);
                            clientViewSQM.setIndexY(y);
                            clientViewSQM.setCoordinateX(x);
                            clientViewSQM.setCoordinateY(y);
                            clientViewSQM.setAreaType(areaType);
                            clientViewSQM.setSqmId(areaMap[y][x]);
                            clientViewSQM.setBorderPainted(false);
                            clientViewSQM.setImageIcon(TilesetImageContainer.getSQMByArea(areaType).get(areaMap[y][x]).getImageIcon());
                            clientViewSQM.setSqmSizeX(TilesetImageContainer.getSQMByArea(areaType).get(areaMap[y][x]).getSqmSizeX());
                            clientViewSQM.setSqmSizeY(TilesetImageContainer.getSQMByArea(areaType).get(areaMap[y][x]).getSqmSizeY());
                            clientViewSQM.setVisible(true);
                            clientViewSQM.updateSQM();
                            this.add(clientViewSQM);
                        }
                    }
                }
            }
        }
        this.setVisible(true);
    }




}
