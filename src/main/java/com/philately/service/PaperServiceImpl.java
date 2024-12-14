package com.philately.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.philately.model.dto.PaperSeedDTO;
import com.philately.model.entity.Paper;
import com.philately.model.entity.Stamp;
import com.philately.model.entity.enums.PaperEnum;
import com.philately.repository.PaperRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.philately.model.entity.enums.PaperEnum.WOVE_PAPER;

@Service
public class PaperServiceImpl {

    private final PaperRepository paperRepository;
//    private final Gson gson;

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    private static final String PAPERS_FILE_PATH = "src/main/resources/static/files/papers.json";

    public PaperServiceImpl(PaperRepository paperRepository, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.paperRepository = paperRepository;
        this.objectMapper = objectMapper;
//        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    public String readPapersFileContent() throws IOException {
        return Files.readString(Path.of(PAPERS_FILE_PATH));
    }

    public void initPapers() throws IOException {
        if (this.paperRepository.count() != 0) {
            return;
        }
        //Using Gson
//        Arrays.stream(gson.fromJson(readPapersFileContent(), PaperSeedDTO[].class))
//                .map(paperSeedDTO -> modelMapper.map(paperSeedDTO, Paper.class))
//                .forEach(paperRepository::save);

        //Using Jackson ObjectMapper to deserialize the JSON file into PaperSeedDTO array
        PaperSeedDTO[] paperSeedDTOs = objectMapper.readValue(readPapersFileContent(), PaperSeedDTO[].class);

        // Map each PaperSeedDTO to Paper entity and save it to the repository
//        Arrays.stream(paperSeedDTOs)
//                .map(paperSeedDTO -> modelMapper.map(paperSeedDTO, Paper.class))
//                .forEach(paperRepository::save);

        Arrays.stream(paperSeedDTOs)
                .map(this::mapToPaper) // Manually map from PaperSeedDTO to Paper
                .forEach(paperRepository::save);
    }

    public void toJson() throws IOException {
        PaperSeedDTO[] paperSeedDTOs = objectMapper.readValue(readPapersFileContent(), PaperSeedDTO[].class);

        objectMapper.writeValue(new File("src/main/resources/static/files/jsonExportTest.json"), paperSeedDTOs);
    }

    public void toJsonString() throws IOException {
        PaperSeedDTO[] paperSeedDTOs = objectMapper.readValue(readPapersFileContent(), PaperSeedDTO[].class);

        String jsonString = objectMapper.writeValueAsString(paperSeedDTOs);
        System.out.println(jsonString);
    }

    public Paper findPaper(PaperEnum paper) {
        return this.paperRepository.findByPaperEnum(paper);
    }

    private Paper mapToPaper(PaperSeedDTO paperSeedDTO) {
        Paper paper = new Paper();
        paper.setPaperEnum(PaperEnum.valueOf(paperSeedDTO.getPaperEnum()));
        paper.setDescription(paperSeedDTO.getDescription());
        Set<Stamp> stamps = paperSeedDTO.getStamps().stream()
                .map(stampSeedDTO -> mapToStamp(stampSeedDTO, paper)) // Pass 'paper' to set it later
                .collect(Collectors.toSet());

        paper.setStamps(stamps);

        return paper;
    }

    private Stamp mapToStamp(Stamp stampSeedDTO, Paper paper) {
        Stamp stamp = new Stamp();

        stamp.setName(stampSeedDTO.getName());
        stamp.setDescription(stampSeedDTO.getDescription());
        stamp.setPrice(stampSeedDTO.getPrice());
        stamp.setImageUrl(stampSeedDTO.getImageUrl());
        stamp.setWished(stampSeedDTO.isWished());

        // Set the associated paper (already created or passed from the parent method)
        stamp.setPaper(paper);

        // Optionally, set the addedBy if you have information about the user (not provided in DTO)
        // stamp.setAddedBy(...);

        return stamp;
    }
}
