package com.happy3friends.eatcleanmenubackend.service.serviceImpl;

import com.happy3friends.eatcleanmenubackend.dto.DishDTO;
import com.happy3friends.eatcleanmenubackend.dto.IngredientDTO;
import com.happy3friends.eatcleanmenubackend.entity.DishEntity;
import com.happy3friends.eatcleanmenubackend.entity.IngredientEntity;
import com.happy3friends.eatcleanmenubackend.exception.NotFoundException;
import com.happy3friends.eatcleanmenubackend.mapper.DishMapper;
import com.happy3friends.eatcleanmenubackend.mapper.IngredientMapper;
import com.happy3friends.eatcleanmenubackend.repository.DishRepository;
import com.happy3friends.eatcleanmenubackend.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Override
    public List<DishDTO> findAll() {
        List<DishEntity> dishEntities = dishRepository.findAll();

        if (dishEntities.isEmpty())
            throw new NotFoundException("List of dishes is not found!");

        return dishEntities.stream()
                .map(e -> dishMapper.convertEntityToDTO(e))
                .collect(Collectors.toList());
    }

    @Override
    public DishDTO findById(int dishId) {
        Optional<DishEntity> dishEntity = dishRepository.findById(dishId);

        return dishEntity.map(entity -> dishMapper.convertEntityToDTO(entity)).orElseThrow(() -> new NotFoundException("Dish with Id " + dishId + " Not Found!"));
    }

    @Override
    public Map<String, List<IngredientDTO>> findIngredientsByDishId(int dishId) {
        Optional<DishEntity> dishEntity = dishRepository.findById(dishId);

        Map<String, List<IngredientDTO>> result;

        if (dishEntity.isPresent()) {
            List<IngredientEntity> ingredientEntityList = dishEntity.get().getIngredientsById().stream().collect(Collectors.toList());
            List<IngredientDTO> ingredientDTOList = ingredientEntityList.stream().map(e -> ingredientMapper.convertEntityToDTO(e)).collect(Collectors.toList());
            result = ingredientDTOList.stream().collect(Collectors.groupingBy(IngredientDTO::getType));
        } else throw new NotFoundException("Can not find dish with dishId!");

        return result;
    }
}