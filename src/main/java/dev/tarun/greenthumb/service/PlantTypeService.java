package dev.tarun.greenthumb.service;

import java.util.List;

import dev.tarun.greenthumb.dto.PlantTypeDTO;
import dev.tarun.greenthumb.dto.PlantTypeRequestDTO;

public interface PlantTypeService {
    List<PlantTypeDTO> getAllPlantTypes();
    PlantTypeDTO getPlantTypeById(Long id);
    PlantTypeDTO createPlantType(PlantTypeRequestDTO request);
    PlantTypeDTO updatePlantType(Long id, PlantTypeRequestDTO request);
    void deletePlantType(Long id);
}