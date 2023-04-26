package nl.pokemon.mapGenerator.view;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.bootstrap.TilesetImageContainer;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
import nl.pokemon.mapGenerator.service.MG_ViewService;
import nl.pokemon.mapGenerator.util.JsonSerialize;
import nl.pokemon.mapGenerator.util.MapConverter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OptionPanel extends JPanel {

    @Inject
    MG_ViewService viewService;

    @Inject(name = "fullMap")
    MG_ViewMap fullMap;


    private int currentZ = 1;
    private AreaType curentAreaType = AreaType.PLAYER_TOP;
    private int lastSelectedSqmId = 0;
    private boolean isAutoFill = false;
    private boolean isFillAll = false;

    public void init() {

        String elevationLevelText = "Elevation: ";
        JLabel elevationLevel = new JLabel(elevationLevelText + currentZ);
        elevationLevel.setBounds(50,0,250,25);
        this.add(elevationLevel);
        JButton incrElevation = new JButton();
        incrElevation.setText("Elevation Up");
        incrElevation.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int newZ = currentZ+1;
                        if (newZ <= MG_ViewMap.MAX_Z) {
                            viewService.changeElevation(newZ);
                            currentZ = newZ;
                            elevationLevel.setText(elevationLevelText + currentZ);
                        }
                    }
                }
        );
        incrElevation.setVisible(true);
        incrElevation.setFocusable(false);
        incrElevation.setBounds(0,25,250,25);
        this.add(incrElevation);

        JButton decrElevation = new JButton();
        decrElevation.setText("Elevation Down");
        decrElevation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newZ = currentZ-1;
                if (newZ >= MG_ViewMap.START_Z) {
                    viewService.changeElevation(newZ);
                    currentZ = newZ;
                    elevationLevel.setText(elevationLevelText + currentZ);
                }
            }
        });
        decrElevation.setVisible(true);
        decrElevation.setFocusable(false);
        decrElevation.setBounds(0,50,250,25);
        this.add(decrElevation);


        String AreaTypeLevelText = "AreaType: ";
        JLabel AreaTypeLevel = new JLabel(AreaTypeLevelText + curentAreaType);
        AreaTypeLevel.setBounds(50,100,250,25);
        this.add(AreaTypeLevel);

        AtomicInteger areaViewY = new AtomicInteger(125);
        for (AreaType areaType : AreaType.values()) {
            JButton areaTypeButton = new JButton(areaType.toString());
            areaTypeButton.setBounds(0, areaViewY.getAndAdd(25), 250, 25);
            areaTypeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    curentAreaType = areaType;
                    viewService.changeAreaType(areaType);
                    AreaTypeLevel.setText(AreaTypeLevelText + curentAreaType);
                    lastSelectedSqmId = 0;
                    repaintButtons();
                }
            });
            areaTypeButton.setFocusable(false);
            areaTypeButton.setVisible(true);
            this.add(areaTypeButton);
        }

        JButton saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.setBounds(0,260,250,25);
        saveButton.setVisible(true);
        saveButton.setFocusable(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JsonSerialize.serializeFullMapToJson(MapConverter.convertBaseSQMMapToIntMap(fullMap.getViewMap()));
            }
        });
        this.add(saveButton);

        JButton autoFill = new JButton();
        autoFill.setText("fill 4 x 4: " + isAutoFill);
        autoFill.setBounds(0,290,250,25);
        autoFill.setVisible(true);
        autoFill.setFocusable(false);
        autoFill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAutoFill = !isAutoFill;
                autoFill.setText("fill 4 x 4: " + isAutoFill);
            }
        });
        this.add(autoFill);

        JButton fillAll = new JButton();
        fillAll.setText("fill all: " + isFillAll);
        fillAll.setBounds(0,315,250,25);
        fillAll.setVisible(true);
        fillAll.setFocusable(false);
        fillAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFillAll = !isFillAll;
                fillAll.setText("fill all: " + isFillAll);
            }
        });
        this.add(fillAll);

        JLabel selectedItem = new JLabel();
        selectedItem.setBounds(50,330,100,100);
        selectedItem.setVisible(true);
        selectedItem.setHorizontalAlignment(SwingConstants.LEFT);
        selectedItem.setVerticalAlignment(SwingConstants.CENTER);
        this.add(selectedItem);

        addSelectableObjectsByArea(selectedItem);
    }

    private Map<AreaType, List<JButton>> selectableAreaContainer = new HashMap<>();

    private void addSelectableObjectsByArea(JLabel selectedItem) {
        AtomicInteger selectableViewY = new AtomicInteger(500);

        TilesetImageContainer.bootstrap();
        for (Map.Entry<AreaType, Map<Integer, BaseTile>> selectableArea : TilesetImageContainer.getSQMByArea().entrySet()) {
            List<JButton> selectableButtons = new ArrayList<>();

            AtomicInteger selectableViewX = new AtomicInteger(0);
            for (Map.Entry<Integer, BaseTile> selectableSQMId : selectableArea.getValue().entrySet()) {
                JButton selectableButton = new JButton();
                selectableButton.setIcon(selectableSQMId.getValue().getImageIcon());
                selectableButton.setBounds(selectableViewX.get(), selectableViewY.get(), 50, 50);

                if (curentAreaType == selectableArea.getKey()) {
                    selectableButton.setVisible(true);
                } else {
                    selectableButton.setVisible(false);
                }

                selectableButton.setFocusable(false);
                this.add(selectableButton);
                selectableViewX.getAndAdd(50);
                if (selectableViewX.get() == 250) {
                    selectableViewY.getAndAdd(50);
                    selectableViewX.set(0);
                }


                selectableButton.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        lastSelectedSqmId = selectableSQMId.getKey();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        selectedItem.setIcon(selectableSQMId.getValue().getImageIcon());
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                            selectedItem.setIcon(TilesetImageContainer.getSQMByIntAndArea(selectableArea.getKey(), lastSelectedSqmId).getImageIcon());
                    }
                });
                selectableButtons.add(selectableButton);
            }
            selectableViewY.set(500);
            selectableViewX.set(0);
            selectableAreaContainer.put(selectableArea.getKey(), selectableButtons);
        }
    }

    private void repaintButtons() {
        for (Map.Entry<AreaType, List<JButton>> selectableArea : selectableAreaContainer.entrySet()) {

            selectableArea.getValue().forEach(button -> {
                if (curentAreaType == selectableArea.getKey()) {
                    button.setVisible(true);
                } else {
                    button.setVisible(false);
                }
            });
        }
    }

    public OptionPanel() {
        this.setAutoscrolls(true);
        this.setLayout(null);
        this.setFocusable(false);
    }

    public int getCurrentZ() {
        return currentZ;
    }

    public AreaType getCurentAreaType() {
        return curentAreaType;
    }

    public int getLastSelectedSqmId() {
        return lastSelectedSqmId;
    }

    public void setLastSelectedSqmId(int lastSelectedSqmId) {
        this.lastSelectedSqmId = lastSelectedSqmId;
    }

    public boolean isAutoFill() {
        return isAutoFill;
    }

    public boolean isFillAll() {
        return isFillAll;
    }


}
