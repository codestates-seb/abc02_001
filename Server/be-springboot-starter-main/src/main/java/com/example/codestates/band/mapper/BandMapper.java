package com.example.codestates.band.mapper;


import com.example.codestates.band.dto.BandPatchDto;
import com.example.codestates.band.dto.BandPostDto;
import com.example.codestates.band.dto.BandResponseDto;
import com.example.codestates.band.entity.Band;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BandMapper {


    Band bandPostDtoToBand(BandPostDto bandPostDto);

    Band bandPatchDtoToBand(BandPatchDto bandPatchDto);

    BandResponseDto bandToBandResponseDto(Band band);

    List<BandResponseDto> bandToBandResponseDtos(List<Band> bands);

}
