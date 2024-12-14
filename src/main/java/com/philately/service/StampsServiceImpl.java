package com.philately.service;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.philately.model.dto.AddStampDTO;
import com.philately.model.dto.StampSeedDTO;
import com.philately.model.entity.Paper;
import com.philately.model.entity.Stamp;
import com.philately.model.entity.User;
import com.philately.repository.PaperRepository;
import com.philately.repository.StampRepository;
import com.philately.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class StampsServiceImpl {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    private final PaperRepository paperRepository;
    private final PaperServiceImpl paperService;

    private final StampRepository stampRepository;
//    private final Gson gson;

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    private static final String STAMPS_FILE_PATH = "src/main/resources/static/files/stamps.json";

    public StampsServiceImpl(UserServiceImpl userService, UserRepository userRepository, PaperRepository paperRepository, ModelMapper modelMapper, PaperServiceImpl paperService, StampRepository stampRepository, ObjectMapper objectMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
//        this.gson = gson;
        this.modelMapper = modelMapper;
        this.paperService = paperService;
        this.stampRepository = stampRepository;
        this.objectMapper = objectMapper;
    }

    public String readStampsFileContent() throws IOException {
        return Files.readString(Path.of(STAMPS_FILE_PATH));
    }

    public void initStamps() throws IOException {
        if (this.stampRepository.count() != 0) {
            return;
        }

        StampSeedDTO[] stampSeedDTOS = objectMapper.readValue(readStampsFileContent(), StampSeedDTO[].class);

        Arrays.stream(stampSeedDTOS)
                .forEach(stampSeedDTO -> {
                    Stamp stamp = modelMapper.map(stampSeedDTO, Stamp.class);
                    Paper paper = paperService.findPaper(stampSeedDTO.getPaper());
                    stamp.setPaper(paper);

                    User user = userService.findUserById((stampSeedDTO.getAddedBy())).orElse(null);

                    if (user != null) {
                        user.getAddedStamps().add(stamp);
                        stamp.setAddedBy(user);
                        stampRepository.save(stamp);
                        userRepository.save(user);
                    }
                });

//        Arrays.stream(gson.fromJson(readStampsFileContent(), StampSeedDTO[].class))
//                .forEach(stampSeedDTO -> {
//                    Stamp stamp = modelMapper.map(stampSeedDTO, Stamp.class);
//                    Paper paper = paperService.findPaper(stampSeedDTO.getPaper());
//                    stamp.setPaper(paper);
//
//                    User user = userService.findUserById((stampSeedDTO.getAddedBy())).orElse(null);
//
//                    if (user != null) {
//                        user.getAddedStamps().add(stamp);
//                        stamp.setAddedBy(user);
//                        stampRepository.save(stamp);
//                        userRepository.save(user);
//                    }
//                });
    }

    public void addStamp(AddStampDTO addStampDTO, Long id) {
        Stamp stamp = new Stamp();
        Paper paper = this.paperService.findPaper(addStampDTO.getPaper());
        User userById = userService.findUserById(id).orElse(null);

        stamp.setName(addStampDTO.getName());
        stamp.setDescription(addStampDTO.getDescription());
        stamp.setImageUrl(addStampDTO.getImageUrl());
        stamp.setPrice(addStampDTO.getPrice());
        stamp.setPaper(paper);
        stamp.setAddedBy(userById);

        assert userById != null;
        userById.getAddedStamps().add(stamp);

        this.stampRepository.save(stamp);
        this.userRepository.save(userById);
    }

    public List<Stamp> getAllStampsByUser(Long userId) {
        return stampRepository.findByAddedById(userId);
    }

    public List<Stamp> getAllStamps(User userId) {
        return stampRepository.findStampsByAddedByNot(userId);
    }

    public Set<Stamp> getWishedStampsByUser(Long userId) {
        return userRepository.getWishedStamps(userId);
    }

    public Set<Stamp> getBoughtStampsByUser(Long userId) {
        return userRepository.getBoughtStamps(userId);
    }

    public void addToWishList(Long stampId, Long userId) {
        User user = userService.findUserById(userId).orElse(null);
        Stamp stamp = stampRepository.findById(stampId).orElse(null);

        if (user != null && stamp != null && !user.getWishedStamps().contains(stamp) && !stamp.isWished()) {
            stamp.setWished(true);
            user.getWishedStamps().add(stamp);
            stampRepository.save(stamp);
            userRepository.save(user);
        }
    }

    public void removeFromWishList(Long userId) {
        User user = userService.findUserById(userId).orElse(null);

        assert user != null;
        Set<Stamp> wishedStamps = user.getWishedStamps();

        for (Stamp wishedStamp : wishedStamps) {
            wishedStamp.setWished(false);
            stampRepository.save(wishedStamp);
        }

        user.getWishedStamps().removeAll(wishedStamps);
        userRepository.save(user);
    }

    public void removeFromWishListById(Long stampId, Long userId) {

        User user = userService.findUserById(userId).orElse(null);
        Stamp stamp = user.getWishedStamps().stream().filter(s -> s.getId().equals(stampId)).findFirst().orElse(null);


        assert stamp != null;
        stamp.setWished(false);
        user.getWishedStamps().remove(stamp);

        stampRepository.save(stamp);
        userRepository.save(user);
    }

    @Transactional
    public void buyStamps(Long userId) {
        User buyer = userRepository.findById(userId).orElse(null);
        Set<Stamp> wishedStamps = userRepository.getWishedStamps(userId);

        for (Stamp wishedStamp : wishedStamps) {
            Long wishedStampOwnerId = wishedStamp.getAddedBy().getId();
            User owner = userRepository.findById(wishedStampOwnerId).orElse(null);

            assert owner != null;
            assert buyer != null;
            wishedStamp.setAddedBy(null);
            owner.getAddedStamps().remove(wishedStamp);
            buyer.getWishedStamps().remove(wishedStamp);
            buyer.getPurchasedStamps().add(wishedStamp);
            wishedStamp.setWished(false);

            userRepository.save(owner);
            userRepository.save(buyer);
            stampRepository.save(wishedStamp);
        }
    }
}
