package nl.pokemon.mapGenerator.view;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.util.JsonDeserialize;
import nl.pokemon.game.util.TilesetImageContainer;
import nl.pokemon.mapGenerator.model.Selected_SQM;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
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
    ViewPanel viewPanel;

    @Inject
    MG_ViewMap viewMap;

    private int currentZ = 1;
    private AreaType curentAreaType = AreaType.PLAYER_TOP;
    private int lastSelectedSqmId = 0;

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
                        if (newZ <= 1) {
                            viewPanel.changeElevation(newZ);
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
                if (newZ >= -1) {
                    viewPanel.changeElevation(newZ);
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
                    viewPanel.changeAreaType(areaType);
                    AreaTypeLevel.setText(AreaTypeLevelText + curentAreaType);
                    lastSelectedSqmId = 0;
                    repaintButtons();
                }
            });
            areaTypeButton.setFocusable(false);
            areaTypeButton.setVisible(true);
            this.add(areaTypeButton);
        }

        JLabel selectedItem = new JLabel();
        selectedItem.setBounds(0,600,100,100);
        selectedItem.setVisible(true);
        selectedItem.setHorizontalAlignment(SwingConstants.LEFT);
        selectedItem.setVerticalAlignment(SwingConstants.CENTER);
        this.add(selectedItem);

        addSelectableObjectsByArea(selectedItem);

        JButton saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.setBounds(0,400,250,25);
        saveButton.setVisible(true);
        saveButton.setFocusable(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JsonSerialize.serializeFullMapToJson(MapConverter.convertBaseSQMMapToIntMap(viewMap.getViewMap()));
            }
        });
        this.add(saveButton);
    }

    private Map<AreaType, List<JButton>> selectableAreaContainer = new HashMap<>();

    private void addSelectableObjectsByArea(JLabel selectedItem) {
        AtomicInteger selectableViewY = new AtomicInteger(250);
        for (Map.Entry<AreaType, Map<Integer, BaseSQM>> selectableArea : TilesetImageContainer.getSQMByArea().entrySet()) {

            List<JButton> selectableButtons = new ArrayList<>();

            AtomicInteger selectableViewX = new AtomicInteger(0);
            for (Map.Entry<Integer, BaseSQM> selectableSQMId : selectableArea.getValue().entrySet()) {
                JButton selectableButton = new Selected_SQM(selectableSQMId.getKey(), selectableSQMId.getValue());
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
            selectableViewY.set(250);
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
}