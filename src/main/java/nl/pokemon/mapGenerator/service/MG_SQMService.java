package nl.pokemon.mapGenerator.service;

import nl.pokemon.mapGenerator.model.MG_BaseSQM;
import org.dpmFramework.annotation.Service;

@Service
public class MG_SQMService {

    public void updateSQM(MG_BaseSQM sqm) {
        sqm.updateBounds();
    }
}
