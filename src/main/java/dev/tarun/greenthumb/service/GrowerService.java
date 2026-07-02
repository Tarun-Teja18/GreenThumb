package dev.tarun.greenthumb.service;

import java.util.List;

import dev.tarun.greenthumb.dto.GrowerDTO;
import dev.tarun.greenthumb.dto.GrowerRequestDTO;

public interface GrowerService {
    List<GrowerDTO> getAllGrowers();
    GrowerDTO getGrowerById(Long id);
    GrowerDTO createGrower(GrowerRequestDTO request);
    GrowerDTO updateGrower(Long id, GrowerRequestDTO request);
    void deleteGrower(Long id);
}