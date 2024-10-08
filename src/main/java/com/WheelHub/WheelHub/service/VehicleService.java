package com.WheelHub.WheelHub.service;

import com.WheelHub.WheelHub.dto.vehicleDtos.VehicleDto;
import com.WheelHub.WheelHub.dto.vehicleDtos.VehicleResponseDto;
import com.WheelHub.WheelHub.entity.Vehicle;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VehicleService {

    VehicleResponseDto createVehicle(VehicleDto vehicleDTO);

    VehicleResponseDto getVehicleById(Long id);

    List<VehicleResponseDto> getAllVehicles();

    List<VehicleResponseDto> getAllDeletedVehicles();

    List<VehicleResponseDto> getAllNotDeletedVehicles();

    VehicleResponseDto updateVehicle(Long id, VehicleDto vehicleDTO);

    void deleteVehicle(Long id);

    Vehicle findById(Long id);

    List<String> saveImages(MultipartFile[] imageFiles, Long vehicleId);

    void deleteAllVehiclesByIds(List<Long> vehiclesIds);

    void softDeleteAllVehiclesByIds(List<Long> vehiclesIds);

    void recoverAllVehiclesByIds(List<Long> vehicleIds);
}
